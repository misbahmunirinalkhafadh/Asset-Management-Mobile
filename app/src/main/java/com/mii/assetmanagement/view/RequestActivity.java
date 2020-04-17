package com.mii.assetmanagement.view;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mii.assetmanagement.R;
import com.mii.assetmanagement.SharedPrefManager;
import com.mii.assetmanagement.adapter.RequestAssetAdapter;
import com.mii.assetmanagement.db.AssetDbHelper;
import com.mii.assetmanagement.model.AssetRequest;
import com.mii.assetmanagement.viewmodel.RequestViewModel;

import java.util.Objects;

import es.dmoral.toasty.Toasty;

import static android.provider.BaseColumns._ID;
import static com.mii.assetmanagement.db.AssetContract.AssetEntry.COLUMN_ITEM;
import static com.mii.assetmanagement.db.AssetContract.AssetEntry.COLUMN_QTY;
import static com.mii.assetmanagement.db.AssetContract.AssetEntry.COLUMN_TIMESTAMP;
import static com.mii.assetmanagement.db.AssetContract.TABLE_NAME;

public class RequestActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etSalesOrder, etBranch, etNik, etItem, inputText, etReason;
    private TextView tvCompanyName, tvEmpName, tvQty;
    private Button btnSubmit;
    private RecyclerView recyclerView;
    private boolean userHide;
    private LinearLayout layoutUser;
    private ProgressDialog progressDialog;
    private SharedPrefManager sharedPrefManager;
    private RequestViewModel requestViewModel;
    private SQLiteDatabase database;
    private RequestAssetAdapter adapter;
    private int amount = 1;
    private final static String RESOURCE = "Resource";
    private final static String STRIP = "-";
    private final static String INVALID_NUMBER = "Invalid number";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        actionBar();

        sharedPrefManager = new SharedPrefManager(this);
        requestViewModel = ViewModelProviders.of(this).get(RequestViewModel.class);

        initComponent();
        eventInputSo();
        eventInputNik();
        callDataSO();
        callDataEmpl();
        itemAssetRequest();

        inputText.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
    }

    private void actionBar() {
        ActionBar actionBar = getSupportActionBar();
        AppCompatTextView mTitleTextView = new AppCompatTextView(getApplicationContext());
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        if (actionBar != null) {
            actionBar.setCustomView(mTitleTextView, layoutParams);
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_HOME_AS_UP);
        }
        mTitleTextView.setText(getString(R.string.appbar_his_req_new));
        mTitleTextView.setTextAppearance(getApplicationContext(), android.R.style.TextAppearance_DeviceDefault_Large);
        mTitleTextView.setTextColor(Color.WHITE);
    }

    private void initComponent() {
        etSalesOrder = findViewById(R.id.et_sales_order);
        etBranch = findViewById(R.id.et_branch);
        etNik = findViewById(R.id.et_nik);
        tvCompanyName = findViewById(R.id.tv_company);
        tvEmpName = findViewById(R.id.tv_name);
        layoutUser = findViewById(R.id.ll_user);
        inputText = findViewById(R.id.input_text);
        etReason = findViewById(R.id.et_reason);
        recyclerView = findViewById(R.id.rv_asset);
        btnSubmit = findViewById(R.id.btn_submit);

        layoutUser.setVisibility(View.GONE);
        inputText.setFocusable(false);
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(Objects.requireNonNull(activity.getCurrentFocus()).getWindowToken(), 0);
        }
    }

    /**
     * Sales Order Area
     */
    private void eventInputSo() {
        etSalesOrder.setInputType(InputType.TYPE_CLASS_NUMBER);
        etSalesOrder.setOnEditorActionListener((v, actionId, event) -> {
            String val = v.getText().toString().trim();
            if (val.isEmpty()) {
                etSalesOrder.setError("Required");
                tvCompanyName.setText(STRIP);
                tvCompanyName.setTextColor(Color.GRAY);
                layoutUser.setVisibility(View.GONE);
                etNik.getText().clear();
            } else {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || event.getAction() == KeyEvent.ACTION_DOWN
                        && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    showLoading();
                    tvCompanyName.setText("");
                    requestViewModel.setDataSO(val);
                    hideSoftKeyboard(this);
                    return true;
                }
            }
            return false;
        });
    }

    private void callDataSO() {
        requestViewModel.getDataSO().observe(this, salesOrder -> {
            if (salesOrder.getError()) {
                tvCompanyName.setText(INVALID_NUMBER);
                tvCompanyName.setTextColor(Color.RED);
                layoutUser.setVisibility(View.GONE);
                etNik.getText().clear();
            } else {
                tvCompanyName.append(salesOrder.getCustomerName());
                tvCompanyName.setTextColor(Color.BLACK);
                //Set Visibility Employee Layout
                if (salesOrder.getAssetType().equals(RESOURCE)) {
                    userHide = false;
                    layoutUser.setVisibility(View.VISIBLE);
                } else {
                    layoutUser.setVisibility(View.GONE);
                    etNik.getText().clear();
                }
            }
            dismissLoading();
        });
    }

    /**
     * User Asset Area
     */
    private void eventInputNik() {
        etNik.setInputType(InputType.TYPE_CLASS_NUMBER);
        etNik.setOnEditorActionListener((v, actionId, event) -> {
            String value = v.getText().toString().trim();
            if (value.isEmpty()) {
                etNik.setError("Required");
                tvEmpName.setText(STRIP);
                tvEmpName.setTextColor(Color.GRAY);
                etNik.getText().clear();
            } else {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || event.getAction() == KeyEvent.ACTION_DOWN
                        && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    showLoading();
                    tvEmpName.setText("");
                    requestViewModel.setDataEmpl(Integer.parseInt(value));
                    hideSoftKeyboard(this);
                    return true;
                }
            }
            return false;
        });
    }

    private void callDataEmpl() {
        requestViewModel.getDataEmployee().observe(this, employeeResult -> {
            if (employeeResult.isError()) {
                tvEmpName.setText(INVALID_NUMBER);
                tvEmpName.setTextColor(Color.RED);
                etNik.getText().clear();
            } else {
                tvEmpName.append(employeeResult.getEmplName());
                tvEmpName.setTextColor(Color.BLACK);
            }
            dismissLoading();
        });
    }

    private void showLoading() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Please wait...");
        }
        progressDialog.show();
    }

    private void dismissLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    /**
     * Insert Request Asset Area
     */
    private void itemAssetRequest() {
        AssetDbHelper dbHelper = new AssetDbHelper(this);
        database = dbHelper.getWritableDatabase();
        database.execSQL("delete FROM " + TABLE_NAME);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RequestAssetAdapter(this, getAllItems(), database);
        recyclerView.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                removeItem((long) viewHolder.itemView.getTag());
            }
        }).attachToRecyclerView(recyclerView);
    }

    private void insertBrand() {
        LayoutInflater inflater = getLayoutInflater();
        View customView = inflater.inflate(R.layout.layout_insert_text, null);
        initCustomView(customView);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add new item ?")
                .setView(customView)
                .setPositiveButton("Save", (dialog, which) -> {
                    String name = etItem.getText().toString();
                    if (name.trim().length() == 0) {
                        etItem.setError("Required");
                        Toast.makeText(RequestActivity.this, "Asset failed to add", Toast.LENGTH_SHORT).show();
                    } else {
                        ContentValues values = new ContentValues();
                        values.put(COLUMN_ITEM, name);
                        values.put(COLUMN_QTY, amount);

                        database.insert(TABLE_NAME, null, values);
                        adapter.swapCursor(getAllItems());
                        amount = 1;

                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.cancel())
                .show();
        hideSoftKeyboard(this);
    }

    private void removeItem(long id) {
        database.delete(TABLE_NAME, _ID + "=" + id, null);
        adapter.swapCursor(getAllItems());
    }

    private Cursor getAllItems() {
        return database.query(TABLE_NAME, null, null, null, null, null, COLUMN_TIMESTAMP + " ASC");
    }

    private void initCustomView(View customView) {
        etItem = customView.findViewById(R.id.et_item);
        tvQty = customView.findViewById(R.id.tv_qty);
        Button btnPlus = customView.findViewById(R.id.btn_plus);
        Button btnMinus = customView.findViewById(R.id.btn_minus);

        tvQty.setText(String.valueOf(amount));
        btnPlus.setOnClickListener(this);
        btnMinus.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.input_text:
                insertBrand();
                break;
            case R.id.btn_minus:
                if (amount > 1) {
                    amount--;
                    tvQty.setText(String.valueOf(amount));
                }
                break;
            case R.id.btn_plus:
                amount++;
                tvQty.setText(String.valueOf(amount));
                break;
            case R.id.btn_submit:
                InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(etSalesOrder.getWindowToken(), 0);
                    imm.hideSoftInputFromWindow(etNik.getWindowToken(), 0);
                    imm.hideSoftInputFromWindow(etReason.getWindowToken(), 0);
                }
                String soId = etSalesOrder.getText().toString().trim();
                String nik = etNik.getText().toString().trim();
                String company = tvCompanyName.getText().toString().trim();
                String employee = tvEmpName.getText().toString().trim();
                String reason = etReason.getText().toString().trim();
                boolean isEmptyFields = false;
                if (TextUtils.isEmpty(soId) && company.equals(STRIP) || company.equals(INVALID_NUMBER)) {
                    isEmptyFields = true;
                    etSalesOrder.setError("Required");
                }

                if (!userHide && TextUtils.isEmpty(nik) && employee.equals(STRIP) || employee.equals(INVALID_NUMBER)) {
                    isEmptyFields = true;
                    etNik.setError("Required");
                }

                if (!isEmptyFields) {
                    if (getDataItem().length == 0 && getDataQty().length == 0) {
                        Toasty.error(this, "No Asset can be request", Toast.LENGTH_SHORT, true).show();
                    } else if (TextUtils.isEmpty(reason)) {
                        etReason.setFocusable(true);
                        Toasty.error(this, "Required!, result can't empty", Toast.LENGTH_SHORT, true).show();
                    } else {
                        saveState();
                    }
                }
                break;
        }
    }

    private String[] getDataItem() {
        Cursor cursor = getAllItems();
        String[] array = new String[cursor.getCount()];
        int i = 0;
        while (cursor.moveToNext()) {
            String uname = cursor.getString(cursor.getColumnIndex(COLUMN_ITEM));
            array[i] = uname;
            i++;
        }
        return array;
    }

    private int[] getDataQty() {
        Cursor cursor = getAllItems();
        int[] array = new int[cursor.getCount()];
        int i = 0;
        while (cursor.moveToNext()) {
            int uname = cursor.getInt(cursor.getColumnIndex(COLUMN_QTY));
            array[i] = uname;
            i++;
        }
        return array;
    }

    private void saveState() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Confirmation")
                .setMessage("Are you sure, want you submit this request?")
                .setCancelable(false)
                .setPositiveButton("Save", (dialog, which) -> {
                    String requester = sharedPrefManager.getSpNik().trim();
                    String soId = etSalesOrder.getText().toString().trim();
                    String reason = etReason.getText().toString().trim();
                    String assignee = etNik.getText().toString().trim();
                    if (TextUtils.isEmpty(assignee)) {
                        assignee = STRIP;
                    }

                    AssetRequest assetRequest = new AssetRequest();
                    assetRequest.setRequester(requester);
                    assetRequest.setSalesOrder(soId);
                    assetRequest.setAssignee(assignee);
                    assetRequest.setBrands(getDataItem());
                    assetRequest.setQuantity(getDataQty());
                    assetRequest.setReason(reason);

                    requestViewModel.saveDataRequestAsset(assetRequest);
                    dialog.dismiss();
                    showDialogSuccess();
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.cancel()).show();
    }

    private void showDialogSuccess() {
        runOnUiThread(() -> {
            if (!isFinishing()) {
                final Dialog dialog = new Dialog(RequestActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.layout_dialog_success);
                dialog.show();
                Button dialogButton = dialog.findViewById(R.id.btn_continue);
                dialogButton.setOnClickListener(v -> {
                    dialog.dismiss();
                    Intent goToMain = new Intent(RequestActivity.this, MainActivity.class);
                    startActivity(goToMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                });
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
