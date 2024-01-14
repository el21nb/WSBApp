package com.example.wsbapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wsbapp.Child;
import com.example.wsbapp.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChildInfoActivity extends AppCompatActivity {
TextView name;
TextView contactsView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_info);
        name = findViewById(R.id.nome);
        contactsView = findViewById(R.id.endereco);
        Intent intent = getIntent();
        String childId = intent.getExtras().getString("childId");
        Log.d("CIA", "childId = " +childId);
        readChild(childId);
    }

    private void readChild(String childId) {
        ChildProvider childProvider = new ChildProvider();
        childProvider.fetchChild(childId, new ChildProvider.ChildFetchListener() {
            @Override
            public void onChildFetched(Child child) {
                if (child != null) {
                    // Do something with the Child object
                    Log.d("CIA", "ChildId: " + childId);
                    name.setText(child.getFirstName()+" "+child.getLastName());
                    List<Contact> contacts = child.getChildContacts();
                    if(contacts!=null){
                        Contact contact1 = contacts.get(0);
                        contactsView.setText(contact1.getName()+ contact1.getContactDetail());
                    }
                } else {
                    Log.e("CIA", "Child not found");
                    // Handle the case when the child is not found
                }
            }
            @Override
            public void onFetchFailed(Exception e) {
                Log.e("RC2", "Error fetching child: " + e.getMessage());
                // Handle the case when fetching the child fails
            }
        });
    }
}


