package com.example.delivery_app;

public class RequestInfoHelperClass {

    String store, items, address, time_window;

    public RequestInfoHelperClass(String store, String items, String address, String time_window) {
        this.store = store;
        this.items = items;
        this.address = address;
        this.time_window = time_window;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTime_window() {
        return time_window;
    }

    public void setTime_window(String time_window) {
        this.time_window = time_window;
    }
}
