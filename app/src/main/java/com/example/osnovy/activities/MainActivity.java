package com.example.osnovy.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.osnovy.Api;
import com.example.osnovy.R;
import com.example.osnovy.Utils;
import com.example.osnovy.adapters.ProductAdapter;
import com.example.osnovy.models.Product;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    static RecyclerView recyclerView;
    static Spinner spinner;
    Retrofit retrofit;
    static ProductAdapter adapter;
    static List<Product> productList = new ArrayList<>();
    ArrayAdapter<String> spinnerAdapter;
    List<String> spinnerArrayList = new ArrayList<>();
    Call<List<Product>> request;
    Api api;
    List<Product> cartProducts = new ArrayList<>();
    CircularProgressIndicator refresh;
    TextView emptyText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.mainToolbar);
        toolbar.setTitleTextAppearance(this, R.style.MainTitleTextStyle);
        emptyText = findViewById(R.id.mainEmptyText);
        spinner = findViewById(R.id.spinner);
        refresh = findViewById(R.id.progressIndicatorMain);
        recyclerView = findViewById(R.id.mainRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 3));

        spinnerArrayList.add("Все товары");
        spinnerArrayList.add("Кроссовки");
        spinnerArrayList.add("Тапочки");
        spinnerArrayList.add("Туфли");

        spinner.setOnItemSelectedListener(this);
        spinnerAdapter = new ArrayAdapter<>(this, R.layout.custom_spinner_item, spinnerArrayList);
        spinnerAdapter.setDropDownViewResource(R.layout.custom_spinner_item);
        spinner.setAdapter(spinnerAdapter);

        retrofit = new Retrofit.Builder()
                .baseUrl(Utils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(Api.class);
        request = api.getAllProducts();
        getproducts(request);
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        refresh.setVisibility(View.VISIBLE);
        switch (position) {
            case 0:
                request = api.getAllProducts();
                break;
            case 1:
                request = api.getSortProduct(1);
                break;
            case 2:
                request = api.getSortProduct(2);
                break;
            case 3:
                request = api.getSortProduct(3);
                break;
        }
        getproducts(request);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        getproducts(request);
    }

    void getproducts(Call<List<Product>> request) {
        request.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    refresh.setVisibility(View.GONE);
                    productList = response.body();
                    if (productList.isEmpty()){
                        emptyText.setVisibility(View.VISIBLE);
                    }
                    else emptyText.setVisibility(View.GONE);
                    adapter = new ProductAdapter(MainActivity.this, productList);
                    recyclerView.setAdapter(adapter);
                    adapter.setOnItemClickListener(new ProductAdapter.RecyclerOnClickListener() {
                        @Override
                        public void onClick(int position) {
                            Log.d("click", productList.get(position).getImage_url().toString());
                            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                            dialog.setTitle(productList.get(position).getTitle().toString());

                            View layout = LayoutInflater.from(MainActivity.this).inflate(R.layout.product_layout, null);
                            ImageView img = layout.findViewById(R.id.alertProductImage);
                            Picasso.get()
                                    .load(productList.get(position).getImage_url()).into(img);
                            dialog.setView(layout);

                            dialog.setPositiveButton("Add to cart", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Product product = isHas(productList.get(position).getTitle(), cartProducts);
                                    if (productList.size()>0){
                                        if (product!=null){
                                            product.setCount(product.getCount()+1);
                                        }
                                        else cartProducts.add(productList.get(position));
                                    }
                                    else cartProducts.add(productList.get(position));

                                    Toast.makeText(getApplicationContext(), "Товар добавлен в корзину", Toast.LENGTH_SHORT).show();
                                }
                            });

                            dialog.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });

                            dialog.show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
    }

//    List<String> getImages(){
//        List<String> imagesList = new ArrayList<>();
//        for (Product product: productList) {
//            imagesList.add(product.getImage_url());
//        }
//        return  imagesList;
//    }
//
//    void getImagesUri(){
//        List<String> images = getImages();
//        imagesUriList = new ArrayList<>();
//        for (String s: images) {
//            Uri uri = Uri.parse(s);
//            imagesUriList.add(uri);
//        }
//    }
//
//    void pathImagesToSlider(){
//        getImagesUri();
//        ImageSliderAdapter adapter = new ImageSliderAdapter(imagesUriList, getApplicationContext());
//        imageSliderView.setSliderAdapter(adapter);
//        imageSliderView.setIndicatorAnimation(IndicatorAnimationType.DROP);
//        imageSliderView.setSliderTransformAnimation(SliderAnimations.ZOOMOUTTRANSFORMATION);
//        imageSliderView.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.cart_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(MainActivity.this, CartActivity.class);
        intent.putExtra("ProductCart", (Serializable) cartProducts);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    public Product isHas(String productName, List<Product> list){
        Product result = null;
        for (Product p:list) {
            if (p.getTitle().equals(productName)){
                result = p;
            }
        }
        return result;
    }

}