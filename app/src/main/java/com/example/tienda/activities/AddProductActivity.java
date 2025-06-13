package com.example.tienda.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.tienda.R;
import com.example.tienda.models.Product;
import com.example.tienda.services.network.ApiService;
import com.example.tienda.services.network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProductActivity extends AppCompatActivity {
    private EditText etName, etPrice;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        apiService = RetrofitClient.getApiService();
        etName = findViewById(R.id.etName);
        etPrice = findViewById(R.id.etPrice);

        Button btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString();
            double price = Double.parseDouble(etPrice.getText().toString());

            Product newProduct = new Product(name, price);

            apiService.createProduct(newProduct).enqueue(new Callback<Product>() {
                @Override
                public void onResponse(Call<Product> call, Response<Product> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(AddProductActivity.this, "Producto creado!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddProductActivity.this, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Product> call, Throwable t) {
                    Toast.makeText(AddProductActivity.this, "Fallo en la conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}