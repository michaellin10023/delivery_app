package com.example.delivery_app;

public class FulfillInfoHelper {

    String store, start, end;
    Address address;

    public FulfillInfoHelper(String store, String start, String end, Address address) {
        this.store = store;
        this.start = start;
        this.end = end;
        this.address = address;
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
}
