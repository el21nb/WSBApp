package com.example.wsbapp;

public class JacketChild {
private String jacketId;
private String childId;

    public JacketChild() {
    }

    public String getJacketId() {
        return jacketId;
    }

    public void setJacketId(String jacketId) {
        this.jacketId = jacketId;
    }

    public String getChildId() {
        return childId;
    }

    public void setChildId(String childId) {
        this.childId = childId;
    }

    public JacketChild(String jacketId, String childId) {
        this.jacketId = jacketId;
        this.childId = childId;
    }
}
