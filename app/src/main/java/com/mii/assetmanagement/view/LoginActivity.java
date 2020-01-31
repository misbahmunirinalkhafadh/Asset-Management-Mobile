package com.mii.assetmanagement.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.mii.assetmanagement.R;
import com.mii.assetmanagement.SharedPrefManager;
import com.mii.assetmanagement.apihelper.ApiService;
import com.mii.assetmanagement.apihelper.UtilsApi;
import com.mii.assetmanagement.model.LoginRequest;
import com.mii.assetmanagement.model.LoginResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.mii.assetmanagement.apihelper.UtilsApi.BASE_URL_API;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    ScrollView svScroll;
    EditText etEmail, etPassword;
    Button btnLogin;
    Animation animLogin;

    Context mContext;
    ApiService mApiService;
    SharedPrefManager sharedPrefManager;
    private ProgressBar loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mContext = this;
        mApiService = UtilsApi.getApiService();
        sharedPrefManager = new SharedPrefManager(this);

        initComponents();

        animLogin = AnimationUtils.loadAnimation(this, R.anim.button_touch);

        //event click component
        btnLogin.setOnClickListener(this);

        //mengecek apakah user sudah login atau belum
        if (sharedPrefManager.getSPSudahLogin()) {

            Log.v("check Login", "login success");
            startActivity(new Intent(LoginActivity.this, MainActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }else{
            Log.v("check Login", "login failed");
//            etEmail.setText(sharedPrefManager.getSPEmail());
        }
    }

    /**
     * initialization element
     */
    private void initComponents() {
        svScroll = findViewById(R.id.sv_login);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        loading = findViewById(R.id.loading);
    }

    @Override
    public void onClick(View v) {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        if (email.isEmpty()) {
            etEmail.setError("Email Empty");
            return;
        }

        if (password.isEmpty()) {
            etPassword.setError("Password Empty");
            return;
        }

        loginRequest(email, password);
    }

    /**
     * fungsi untuk melakukan request login
     */
    public void loginRequest(final String email, String password) {
        loading.setVisibility(View.VISIBLE);
        etEmail.setEnabled(false);
        etPassword.setEnabled(false);
        btnLogin.setEnabled(false);

        btnLogin.startAnimation(animLogin);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mApiService = retrofit.create(ApiService.class);
        Call<LoginResult> call = mApiService.login(new LoginRequest(email, password));
        call.enqueue(new Callback<LoginResult>() {
            @Override
            public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                if (response.body().getError()) {
                    Log.e("", "ERROR RESPONSE : " + new Gson().toJson(response));
                    Toast.makeText(getBaseContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    loading.setVisibility(View.GONE);
                    etEmail.setEnabled(true);
                    etPassword.setEnabled(true);
                    btnLogin.setEnabled(true);

                } else {
                    Log.i("", "SUCCESS RESPONSE : " + new Gson().toJson(response));

                    String name = response.body().getUserName();
                    String email = response.body().getEmail();
                    String nik = Integer.toString(response.body().getNik());

                    sharedPrefManager.saveSPString(SharedPrefManager.SP_NAMA, name);
                    sharedPrefManager.saveSPString(SharedPrefManager.SP_EMAIL, email);
                    sharedPrefManager.saveSPString(SharedPrefManager.SP_NIK, nik);

                    // Shared Pref ini berfungsi untuk menjadi trigger session login
                    sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, true);

                    startActivity(new Intent(mContext, MainActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                    finish();

                }
            }

            @Override
            public void onFailure(Call<LoginResult> call, Throwable t) {
                Log.e("debug", "onFailure: ERROR > " + t.toString());

                loading.setVisibility(View.VISIBLE);

                etEmail.setEnabled(true);
                etPassword.setEnabled(true);
                btnLogin.setEnabled(true);
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}