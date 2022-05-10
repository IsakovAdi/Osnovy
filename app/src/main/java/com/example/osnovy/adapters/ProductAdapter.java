package com.example.osnovy.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.osnovy.R;
import com.example.osnovy.Utils;
import com.example.osnovy.models.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    Context context;
    RecyclerOnClickListener listener;
    List<Product> productList;

    public void setOnItemClickListener(RecyclerOnClickListener listener) {
        this.listener = listener;
    }

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false);
        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Picasso.get()
                .load(productList.get(position).getImage_url()).into(holder.productImage);
        holder.productPrice.setText(productList.get(position).getPrice());
        holder.productDescription.setText(productList.get(position).getDescription());
        holder.title.setText(productList.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView title, productPrice, productDescription;
        public ViewHolder(@NonNull View itemView, RecyclerOnClickListener listener) {
            super(itemView);
            productDescription = itemView.findViewById(R.id.productDescription);
            productPrice = itemView.findViewById(R.id.productPrice);
            title  = itemView.findViewById(R.id.productTitle);
            productImage = itemView.findViewById(R.id.productImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(getAdapterPosition());
                }
            });
        }
    }

    public interface RecyclerOnClickListener {
        void onClick(int position);
    }
}
