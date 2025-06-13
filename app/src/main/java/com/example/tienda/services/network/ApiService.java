package com.example.tienda.services.network;

import com.example.tienda.models.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    String BASE_URL = "https://683f90d25b39a8039a54f453.mockapi.io";

    @GET("/products")
    Call<List<Product>> getProducts();

    @GET("/products/{id}")
    Call<Product> getProductById(@Path("id") String id);

    @POST("/products")
    Call<Product> createProduct(@Body Product product);

    @PUT("/products/{id}")
    Call<Product> updateProduct(@Path("id") String id, @Body Product product);

    @DELETE("/products/{id}")
    Call<Void> deleteProduct(@Path("id") String id);

}
