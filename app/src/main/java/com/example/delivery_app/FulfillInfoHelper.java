package com.example.delivery_app;

public class FulfillInfoHelper {

    String store, myAddress, timeWindow;

    public FulfillInfoHelper(String store, String myAddress, String timeWindow) {
        this.store = store;
        this.myAddress = myAddress;
        this.timeWindow = timeWindow;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getMyAddress() {
        return myAddress;
    }

    public void setMyAddress(String myAddress) {
        this.myAddress = myAddress;
    }

    public String getTimeWindow() {
        return timeWindow;
    }

    public void setTimeWindow(String timeWindow) {
        this.timeWindow = timeWindow;
    }
}
