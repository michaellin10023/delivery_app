package com.example.delivery_app;

enum req_status{
    pending, confirmed, expired, completed, deleted,matched
}

public class RequestInfoHelperClass {

    private String store, items, start_time,end_time, device_token;
    private Address address;
    private req_status req_status;

    public  RequestInfoHelperClass(){}
    public RequestInfoHelperClass(String store, String items, String start_time, String end_time, String device_token, Address address, req_status req_status) {
        this.store = store;
        this.items = items;
        this.start_time = start_time;
        this.end_time = end_time;
        this.device_token = device_token;
        this.address = address;
        this.req_status = req_status;
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

    public String getDevice_token() {
        return device_token;
    }

    public void setDevice_token(String device_token) {
        this.device_token = device_token;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public req_status getReq_status() {
        return req_status;
    }

    public void setReq_status(req_status req_status) {
        this.req_status = req_status;
    }
}
