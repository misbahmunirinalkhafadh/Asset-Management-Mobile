package com.mii.assetmanagement.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;
import com.mii.assetmanagement.R;
import com.mii.assetmanagement.SharedPrefManager;
import com.mii.assetmanagement.model.Login;
import com.mii.assetmanagement.viewmodel.LoginViewModel;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etEmail, etPassword;
    private Button btnLogin;
    private ScrollView view;
    private Animation animLogin;
    private Snackbar snackbar;
    private LoginViewModel loginViewModel;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Objects.requireNonNull(getSupportActionBar()).hide();
        initComponents();
        showLoading();

        SharedPrefManager sharedPrefManager = new SharedPrefManager(this);
        //mengecek apakah user sudah login atau belum
        if (sharedPrefManager.getSPSudahLogin()) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }

        loginViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(LoginViewModel.class);
        loginViewModel.getLiveData().observe(this, user -> {
            if (user == null) {
                Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
            } else {
                String name = user.getUserName();
                String email = user.getEmail();
                String nik = Integer.toString(user.getNik());
                sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, true);
                sharedPrefManager.saveSPString(SharedPrefManager.SP_NAMA, name);
                sharedPrefManager.saveSPString(SharedPrefManager.SP_EMAIL, email);
                sharedPrefManager.saveSPString(SharedPrefManager.SP_NIK, nik);
                startActivity(new Intent(LoginActivity.this, MainActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
            progressDialog.dismiss();
        });

        btnLogin.setOnClickListener(this);
    }

    private void initComponents() {
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        view = findViewById(R.id.main_view);
        animLogin = AnimationUtils.loadAnimation(this, R.anim.button_touch);
    }

    private void showLoading() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Please wait...");
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_login) {
            btnLogin.startAnimation(animLogin);
            InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(etEmail.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(etPassword.getWindowToken(), 0);
            }

            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            if (validateLogin(email, password)) {
                progressDialog.show();
                Login item = new Login();
                item.setEmail(email);
                item.setPassword(password);
                if (isNetworkAvailable()) {
                    loginViewModel.setLiveDataUser(item);
                } else {
                    progressDialog.dismiss();
                    showSnackBar();
                }
            }
        }
    }

    private boolean validateLogin(String email, String password) {
        if (email == null || email.length() == 0) {
            etEmail.setError("Email is required");
            return false;
        }
        if (password == null || password.length() == 0) {
            etPassword.setError("Password is required");
            return false;
        }
        return true;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void showSnackBar() {
        snackbar = Snackbar
                .make(view, "No Internet Connection", Snackbar.LENGTH_LONG)
                .setAction("DISMISS", v -> snackbar.dismiss());
        snackbar.show();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
