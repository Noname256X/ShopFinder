package com.example.shopfinder;

public interface CustomWebSocketListener {
    void onMessage(String message);
    void onDataReceived(String marketplace, ProductData data);
    void onFailure(String error);
}