package com.example.myapplication.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.data.network.RetrofitHelper;
import com.example.myapplication.data.network.RetrofitService;
import com.example.myapplication.data.network.responses.LoginResponse;
import com.example.myapplication.data.preferences.SharedPreferencesManager;
import com.example.myapplication.ui.main.MainActivity;
import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText usernameEditText;
    private TextInputEditText passwordEditText;
    private Button btnLogin;
    private ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.etUsername);
        passwordEditText = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.login);
        progressBar = findViewById(R.id.loading);

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO: 10/11/2020
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);

        btnLogin.setEnabled(true);
        btnLogin.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);

            loginUser();
        });
    }

    // shouldnt be in the Activity
    private void loginUser() {

        // TODO: 10/11/2020 get values from editTexts
        RetrofitService apiInterface = RetrofitHelper.createService(RetrofitService.class);
        Call<LoginResponse> call = apiInterface.login("TH1234", "3NItas1!");

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NotNull Call<LoginResponse> call, @NotNull Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    onLoginSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<LoginResponse> call, @NotNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                onLoginError();
            }
        });

    }

    private void onLoginSuccess(LoginResponse body) {
        // save tokens to shared_pref
        if (body != null) {
            SharedPreferencesManager.setAccessToken(this, body.getAccessToken());
            SharedPreferencesManager.setRefreshToken(this, body.getRefreshToken());
        }

        // navigate to Main screen
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void onLoginError() {
        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
    }
}