package com.example.osnovy.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.osnovy.R;
import com.example.osnovy.models.Product;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.Holder> {
    Context context;
    List<Product> productList;

    public CartAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        Picasso.get()
                .load(productList.get(position).getImage_url()).into(holder.productImage);
        holder.title.setText(productList.get(position).getTitle());
        holder.description.setText(productList.get(position).getDescription());
        holder.price.setText(productList.get(position).getPrice());
        holder.count.setText(String.valueOf(productList.get(position).getCount()));

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView title, price, description, count;
        public Holder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.cart_image);
            title = itemView.findViewById(R.id.cart_product_title);
            description = itemView.findViewById(R.id.cart_product_desc);
            price = itemView.findViewById(R.id.cart_product_price);
            count = itemView.findViewById(R.id.cart_product_count);
        }
    }
}
