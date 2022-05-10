package com.example.osnovy.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.osnovy.Api;
import com.example.osnovy.R;
import com.example.osnovy.Utils;
import com.example.osnovy.activities.AuthActivity;
import com.example.osnovy.models.LoginRequest;
import com.example.osnovy.models.RequestBody;
import com.example.osnovy.models.User;
import com.example.osnovy.ui.main.SectionsPagerAdapter;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RegisterFragment extends Fragment {
    TextView email, login, password;
    Button btnEnter;
    Retrofit retrofit;
    CircularProgressIndicator refresh;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        password = view.findViewById(R.id.passwordText);
        email = view.findViewById(R.id.emailText);
        login = view.findViewById(R.id.loginText);
        btnEnter = view.findViewById(R.id.registerBtn);
        refresh = view.findViewById(R.id.registerIndicator);

        retrofit  = new Retrofit.Builder()
                .baseUrl(Utils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        User user = new User();

        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!login.getText().toString().isEmpty()&&
                        !password.getText().toString().isEmpty()&&!email.getText().toString().isEmpty()){
                    refresh.setVisibility(View.VISIBLE);
                    user.setUsername(login.getText().toString());
                    user.setPassword(password.getText().toString());
                    user.setEmail(email.getText().toString());
                    Call<RequestBody> request;
                    request = api.register(user);
                    request.enqueue(new Callback<RequestBody>() {
                        @Override
                        public void onResponse(Call<RequestBody> call, Response<RequestBody> response) {
                            if (response.isSuccessful()){
                                refresh.setVisibility(View.INVISIBLE);
                                Toast.makeText(getContext(), "Вы успешно зарегистрировались", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getContext(), AuthActivity.class);
                                intent.putExtra("RegisterLoginText", login.getText().toString() );
                                startActivity(intent);
                            }
                            else {
                                assert response.errorBody() != null;
                                Log.d("regEr",  response.errorBody().toString());
                            }
                        }

                        @Override
                        public void onFailure(Call<RequestBody> call, Throwable t) {
                            Log.d("regEr",  t.getMessage().toString());
                        }
                    });
                }
                else {
                    Toast.makeText(getContext(), "Все поля должны быть заполнены", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}