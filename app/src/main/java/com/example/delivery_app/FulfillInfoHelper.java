package com.example.delivery_app;

enum ful_status{
    pending, pending_confirmation, matched, expired, deleted
}
public class FulfillInfoHelper {

    private String store, start, end, device_token;
    private Address address;
    private ful_status ful_status;

    public FulfillInfoHelper(){}
    public FulfillInfoHelper(String store, String start, String end, String device_token, Address address, com.example.delivery_app.ful_status ful_status) {
        this.store = store;
        this.start = start;
        this.end = end;
        this.device_token = device_token;
        this.address = address;
        this.ful_status = ful_status;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public ful_status getFul_status() {
        return ful_status;
    }

    public void setFul_status(ful_status ful_status) {
        this.ful_status = ful_status;
    }

    public String getDevice_token() {
        return device_token;
    }

    public void setDevice_token(String device_token) {
        this.device_token = device_token;
    }
}
