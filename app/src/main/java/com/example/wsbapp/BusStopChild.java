package com.example.wsbapp;

import java.util.List;

public class BusStopChild {
    private String busStopId;
    private String childId;



    public String getBusStopId() {
        return busStopId;
    }

    public void setBusStopId(String busStopId) {
        this.busStopId = busStopId;
    }

    public String getChildId() {
        return childId;
    }

    public void setChildId(String childId) {
        this.childId = childId;
    }



    public BusStopChild(String busStopId, String childId) {
        this.busStopId = busStopId;
        this.childId = childId;
    }
}
