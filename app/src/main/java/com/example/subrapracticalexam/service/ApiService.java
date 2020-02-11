package com.example.subrapracticalexam.service;

import com.example.subrapracticalexam.model.Customer;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @GET("Customer/GetAllCustomer")
    Call<List<Customer>> getAllCustomer(@Query("OCode") int oCode);

    @POST("Customer/SaveCustomer")
    Call<String> saveCustomer(@Body Customer customer);

    @POST("Customer/UpdateCustomer")
    Call<String> updateCustomer(@Query("Customer_Id") String customerId, @Body Customer customer);
}
