package com.example.osnovy.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.osnovy.Api;
import com.example.osnovy.R;
import com.example.osnovy.Utils;
import com.example.osnovy.activities.MainActivity;
import com.example.osnovy.models.LoginRequest;
import com.example.osnovy.models.TokenResponse;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginFragment extends Fragment {
    TextView password;
    TextView login;
    Retrofit retrofit;
    Button btn;
    CircularProgressIndicator refresh;
    String registerLoginText;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        registerLoginText = Objects.requireNonNull(getActivity()).getIntent().getStringExtra("RegisterLoginText");
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        password = view.findViewById(R.id.loginPasswordText);
        login = view.findViewById(R.id.LogloginText);
        btn = view.findViewById(R.id.loginEnterBtn);
        refresh = view.findViewById(R.id.loginIndicator);

        retrofit  = new Retrofit.Builder()
                .baseUrl(Utils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        LoginRequest loginRequest = new LoginRequest();
        if (registerLoginText!= null){
            login.setText(registerLoginText);
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refresh.setVisibility(View.VISIBLE);
                if (!login.getText().toString().isEmpty()&&!password.getText().toString().isEmpty()){
                    loginRequest.setPassword(password.getText().toString());
                    loginRequest.setUsername(login.getText().toString());
                    Call<TokenResponse> request;
                    request = api.login(loginRequest);
                    request.enqueue(new Callback<TokenResponse>() {
                        @Override
                        public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                            refresh.setVisibility(View.GONE);
                            if (response.isSuccessful()) {
                                Log.d("Response", "SUCCESS");
                                startActivity(new Intent(getContext(), MainActivity.class));
                            }
                        }

                        @Override
                        public void onFailure(Call<TokenResponse> call, Throwable t) {
                            Log.d("Response", "NOT SUCCESS");
                        }
                    });
                }
            }
        });
    }
}