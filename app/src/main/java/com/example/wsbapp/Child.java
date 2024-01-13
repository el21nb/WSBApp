package com.example.wsbapp;

import java.util.List;

public class Child {

    private String id;
    private String firstName;
    private String lastName;

    List<Contact> childContacts;

    public Child(){}
    public Child(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getId() {
        return id;
    }
    public String getFirstName() {
        return firstName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Contact> getChildContacts() {
        return childContacts;
    }
    public void setChildContacts(List<Contact> childContacts) {
        this.childContacts = childContacts;
    }
}
