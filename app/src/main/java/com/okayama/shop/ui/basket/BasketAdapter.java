package com.okayama.shop.ui.basket;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.okayama.shop.R;
import com.okayama.shop.base.ItemClickListener;
import com.okayama.shop.data.models.Product;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BasketAdapter extends RecyclerView.Adapter<BasketAdapter.ViewHolder> {

    private List<Product> products = new ArrayList<>();
    private ItemClickListener itemClickListener;

    public BasketAdapter(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_basket, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(products.get(position), itemClickListener);
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.product_name_text_view)
        TextView productNameTextView;

        @BindView(R.id.product_count_text_view)
        TextView productCountTextView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        private void onBind(final Product product, final ItemClickListener itemClickListener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onClick(product.getId());
                }
            });
            productNameTextView.setText(product.getName());
            productCountTextView.setText(String.valueOf(product.getCount()));
        }
    }
}
