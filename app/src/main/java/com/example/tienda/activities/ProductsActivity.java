package com.example.tienda.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.graphics.PointKt;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tienda.R;
import com.example.tienda.adapters.ProductAdapter;
import com.example.tienda.models.Product;
import com.example.tienda.services.ProductsServices;
import com.example.tienda.services.network.ApiService;
import com.example.tienda.services.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsActivity extends AppCompatActivity {
    static String TAG = "Listado";
    private RecyclerView rvProducts;
    private ProductAdapter adapter;
    private ApiService apiService;
    private List<Product> products = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        apiService = RetrofitClient.getApiService();
        adapter = new ProductAdapter(products);

        rvProducts = findViewById(R.id.rvProducts);
        rvProducts.setLayoutManager(new LinearLayoutManager(this));
        rvProducts.setAdapter(adapter);

        loadProducts();
    }

    private void loadProducts(){
        apiService.getProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(response.isSuccessful()){
                    products.clear(); //Limpiar productos registrados
                    products.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }
                else{
                    Toast.makeText(ProductsActivity.this, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(ProductsActivity.this, "Fallo en la conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadProducts();
    }

}