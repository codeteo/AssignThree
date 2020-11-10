package com.example.myapplication.ui.login;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.data.network.RetrofitHelper;
import com.example.myapplication.data.network.RetrofitService;
import com.example.myapplication.data.network.responses.LoginResponse;
import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final TextInputEditText usernameEditText = findViewById(R.id.etUsername);
        final TextInputEditText passwordEditText = findViewById(R.id.etPassword);
        final Button btnLogin = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

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
        passwordEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                // TODO: 10/11/2020
            }
            return false;
        });

        btnLogin.setEnabled(true);
        btnLogin.setOnClickListener(v -> {
            loadingProgressBar.setVisibility(View.VISIBLE);
            // TODO: 10/11/2020
            loginUser();

        });
    }

    private void loginUser() {

        RetrofitService apiInterface = RetrofitHelper.createService(RetrofitService.class);
        Call<LoginResponse> call = apiInterface.login("TH1234", "3NItas1!");

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NotNull Call<LoginResponse> call, @NotNull Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    onLoginSuccess();
                }
            }

            @Override
            public void onFailure(@NotNull Call<LoginResponse> call, @NotNull Throwable t) {
                onLoginError();
            }
        });

    }

    private void onLoginSuccess() {
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), "welcome", Toast.LENGTH_LONG).show();
    }

    private void onLoginError() {
        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
    }
}