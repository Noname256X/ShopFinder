package com.example.shopfinder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

import retrofit2.Retrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "http://192.168.1.4:3000/api/";
    private Retrofit retrofit;

    public ApiClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void searchProducts(String ipAddress, String query, int pageNumber, List<String> marketplaces, ApiCallback<SearchResponse> callback) {
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("ip", ipAddress);
            requestBody.put("query", query);
            requestBody.put("pageNumber", pageNumber);
            requestBody.put("marketplaces", new JSONArray(marketplaces));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(
                MediaType.parse("application/json"),
                requestBody.toString()
        );

        Call<SearchResponse> call = apiInterface.searchProducts(body);
        call.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure("Ошибка сервера: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    public interface ApiInterface {
        @POST("search")
        Call<SearchResponse> searchProducts(@Body RequestBody body);
    }

    public interface ApiCallback<T> {
        void onSuccess(T response);
        void onFailure(String error);
    }

    public static class SearchResponse {
        public String status;
    }
}
