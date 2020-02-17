package com.mii.assetmanagement.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.mii.assetmanagement.R;
import com.mii.assetmanagement.SharedPrefManager;
import com.mii.assetmanagement.model.LoginResult;
import com.mii.assetmanagement.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private Animation animLogin;

    private LoginViewModel loginViewModel;
    private SharedPrefManager sharedPrefManager;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPrefManager = new SharedPrefManager(this);
        loginViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(LoginViewModel.class);

        //mengecek apakah user sudah login atau belum
        if (sharedPrefManager.getSPSudahLogin()) {
            Log.v("Check Login", "Sudah login");
            startActivity(new Intent(LoginActivity.this, MainActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }

        initComponents();

        btnLogin.setOnClickListener(this);
    }

    private void initComponents() {
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        animLogin = AnimationUtils.loadAnimation(this, R.anim.button_touch);
    }

    @Override
    public void onClick(View v) {
        btnLogin.startAnimation(animLogin);
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        if (validateLogin(email, password)) {
            showLoading();
            loginViewModel.setLiveDataUser(email, password);
        }
    }

    private boolean validateLogin(String email, String password) {
        if (email == null || email.trim().length() == 0) {
            etEmail.setError("Email is required");
            return false;
        }
        if (password == null || password.trim().length() == 0) {
            etPassword.setError("Password is required");
            return false;
        }
        return true;
    }

    private void showLoading() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Please wait...");
        }
        progressDialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loginViewModel.getLiveData().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(LoginResult loginResult) {
                String name = loginResult.getUserName();
                String email = loginResult.getEmail();
                String nik = Integer.toString(loginResult.getNik());
                if (loginResult.isError()) {
                    Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_SHORT).show();
                } else {
                    sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, true);
                    sharedPrefManager.saveSPString(SharedPrefManager.SP_NAMA, name);
                    sharedPrefManager.saveSPString(SharedPrefManager.SP_EMAIL, email);
                    sharedPrefManager.saveSPString(SharedPrefManager.SP_NIK, nik);
                    startActivity(new Intent(LoginActivity.this, MainActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                    finish();
                }
                dismissLoading();
            }
        });
    }

    private void dismissLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
