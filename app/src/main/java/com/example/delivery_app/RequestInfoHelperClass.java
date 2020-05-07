package com.example.delivery_app;

public class RequestInfoHelperClass {

    String store, items, start_time,end_time;
    Address address;

    public RequestInfoHelperClass(String store, String items, Address address, String start_time, String end_time) {
        this.store = store;
        this.items = items;
        this.start_time = start_time;
        this.end_time = end_time;
        this.address = address;
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

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
