package com.example.osnovy;

import com.example.osnovy.models.LoginRequest;
import com.example.osnovy.models.Product;
import com.example.osnovy.models.RequestBody;
import com.example.osnovy.models.TokenResponse;
import com.example.osnovy.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {
    @POST(Utils.REGISTER)
    Call<RequestBody> register(
            @Body User user
    );

    @POST(Utils.LOGIN)
    Call<TokenResponse> login(
            @Body LoginRequest loginRequest
    );

    @GET(Utils.PRODUCTS)
    Call<List<Product>> getSortProduct(
            @Query("category") int category
    );

    @GET(Utils.PRODUCTS)
    Call<List<Product>> getAllProducts();
}
