package com.mii.assetmanagement;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mii.assetmanagement.apihelper.ApiService;
import com.mii.assetmanagement.apihelper.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    ScrollView svScroll;
    EditText etEmail, etPassword;
    Button btnLogin;
    Animation animLogin;
    ProgressDialog loading;

    Context mContext;
    ApiService mApiService;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //hide scroll bar in scrollView
        svScroll.setVerticalScrollBarEnabled(false);
        svScroll.setHorizontalScrollBarEnabled(false);

        mContext = this;
        mApiService = UtilsApi.getAPIService();
        sharedPrefManager = new SharedPrefManager(this);

        initComponents();

        animLogin = AnimationUtils.loadAnimation(this, R.anim.button_touch);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLogin.startAnimation(animLogin);

                final String eml = etEmail.getText().toString();
                final String pass = etPassword.getText().toString();

                if (!eml.isEmpty() && !pass.isEmpty()) {
                    loading = ProgressDialog.show(mContext, null, "Please wait", true, false);
                    requestLogin();
                } else {
                    if (eml.isEmpty() || pass.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Username or password is required", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //mengecek apakah user sudah login atau belum
        if (sharedPrefManager.getSPSudahLogin()) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }
    }

    private void initComponents() {
        //initialisation element
        svScroll = (ScrollView) findViewById(R.id.sv_login);
        etEmail = (EditText) findViewById(R.id.et_email);
        etPassword = (EditText) findViewById(R.id.et_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
    }

    private void requestLogin() {
        mApiService.loginRequest(etEmail.getText().toString(), etPassword.getText().toString()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    loading.dismiss();
                    try {
                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                        if (jsonRESULTS.getString("error").equals("false")) {
                            Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();
                            String nama = jsonRESULTS.getJSONObject("user").getString("firstName");

                            sharedPrefManager.saveSPString(SharedPrefManager.SP_NAMA, nama);
                            // Shared Pref ini berfungsi untuk menjadi trigger session login
                            sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, true);

                            startActivity(new Intent(mContext, MainActivity.class)
                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                            finish();
                        } else {
                            String error_message = jsonRESULTS.getString("errorMessage");
                            Toast.makeText(mContext, error_message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.i("debug", "onResponse: GA BERHASIL");
                    loading.dismiss();
                }
            }

            /**
             *
             * @param call
             * @param t
             */
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("debug", "onFailure: ERROR > " + t.toString());
                loading.dismiss();
                Toast.makeText(mContext, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
