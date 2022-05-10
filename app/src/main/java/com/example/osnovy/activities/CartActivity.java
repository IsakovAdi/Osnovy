package com.example.osnovy.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.osnovy.R;
import com.example.osnovy.adapters.CartAdapter;
import com.example.osnovy.models.Product;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    List<Product> productList = new ArrayList<>();
    RecyclerView recyclerView;
    CartAdapter adapter;
    TextView emptyText;
    MaterialButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        button = findViewById(R.id.makeOrderBtn);
        emptyText = findViewById(R.id.emptyCartText);
        recyclerView = findViewById(R.id.cartRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        Intent intent = getIntent();
        productList = (List<Product>) intent.getSerializableExtra("ProductCart");
        adapter = new CartAdapter(this, productList);
        recyclerView.setAdapter(adapter);

        if (productList.isEmpty()){
            emptyText.setVisibility(View.VISIBLE);
            button.setClickable(false);
            button.setFocusable(false);

        }
        else {
            emptyText.setVisibility(View.INVISIBLE);
            button.setClickable(true);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}