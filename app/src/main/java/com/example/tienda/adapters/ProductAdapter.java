package com.example.tienda.adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tienda.R;
import com.example.tienda.models.Product;
import com.example.tienda.services.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>{
    private List<Product> products;

    private List<Product> productList;
    private Context context;

    public ProductAdapter(List<Product> products){
        this.products = products;
    }

    // Crear nuevas vistas (Invocadas por el Layout Manager)
    @Override
    public ViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }


    // Reemplazar el contenido de una vista (invocada por el layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        Product product = products.get(position);
        holder.tvName.setText(product.getName());
        holder.tvPrice.setText("$" + product.getPrice());

        holder.btnShowProduct.setOnClickListener(v->{
            Toast.makeText(v.getContext(),"Producto Clickeado: " + product.getName(), Toast.LENGTH_SHORT).show();
        });

        holder.btnDeleteProduct.setOnClickListener(v -> {

            // Llama a Retrofit para eliminar
            RetrofitClient.getApiService().deleteProduct(product.getId())
                    .enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                productList.remove(position);
                                notifyItemRemoved(position);
                                Toast.makeText(context, "Producto eliminado", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Error al eliminar", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });

    }


    // Devuelve la cantidad de elementos que tenemos en la lista
    @Override
    public int getItemCount(){
        return products.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvName, tvPrice;

        Button btnShowProduct;
        Button btnDeleteProduct;

        public ViewHolder(View itemView){
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            btnShowProduct = itemView.findViewById(R.id.btnShowProduct);

            btnDeleteProduct = itemView.findViewById(R.id.btnDeleteProduct);
        }
    }
}