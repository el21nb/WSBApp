package com.example.wsbapp;

import java.util.List;

public class BusStop {

    public String getId() {
        return id;
    }

    private String id;
    private String name;
    private String address;

    public BusStop() {
    }

    public List<Child> getBusStopChildren() {
        return busStopChildren;
    }

    private List<Child> busStopChildren;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public void setBusStopChildren(List<Child> busStopChildren) {
        this.busStopChildren = busStopChildren;
    }

    public BusStop(String name, String address) {
        this.name = name;
        this.address = address;
    }
}
