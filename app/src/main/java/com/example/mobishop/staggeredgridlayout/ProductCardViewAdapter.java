package com.example.mobishop.staggeredgridlayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobishop.PaymentsUtil;
import com.example.mobishop.ProductPaymentDelegate;
import com.example.mobishop.R;
import com.example.mobishop.network.ImageRequester;
import com.example.mobishop.network.ProductEntry;
import com.google.android.gms.wallet.AutoResolveHelper;
import com.google.android.gms.wallet.PaymentDataRequest;
import com.google.gson.JsonIOException;

import java.util.List;
import java.util.Optional;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Adapter used to show an asymmetric grid of products, with 2 items in the first column, and 1
 * item in the second column, and so on.
 */
public class ProductCardViewAdapter extends RecyclerView.Adapter<ProductCardViewHolder> {

    private List<ProductEntry> productList;
    private ImageRequester imageRequester;
    private ProductPaymentDelegate productPaymentDelegate;

    public ProductCardViewAdapter(List<ProductEntry> productList, ProductPaymentDelegate productPaymentDelegate) {
        this.productList = productList;
        imageRequester = ImageRequester.getInstance();
        this.productPaymentDelegate = productPaymentDelegate;
    }

    @Override
    public int getItemViewType(int position) {
        return position % 3;
    }

    @NonNull
    @Override
    public ProductCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId = R.layout.mobishop_staggered_product_card_first;
        if (viewType == 1) {
            layoutId = R.layout.mobishop_staggered_product_card_second;
        } else if (viewType == 2) {
            layoutId = R.layout.mobishop_staggered_product_card_third;
        }

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return new ProductCardViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductCardViewHolder holder, int position) {
        if (productList != null && position < productList.size()) {
            ProductEntry product = productList.get(position);
            holder.productTitle.setText(product.title);
            holder.productPrice.setText(product.price);
            imageRequester.setImageFromUrl(holder.productImage, product.url);
            holder.payButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Double price = 100.00;
                    productPaymentDelegate.payment(price);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
