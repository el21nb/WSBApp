package com.example.wsbapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class PassengersActivity extends AppCompatActivity {
    Button busStopButton;

    private ListView passengerList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passengers);
        passengerList = findViewById(R.id.passengerList); // Replace with your actual ListView ID
        busStopButton = findViewById(R.id.busStopButton);
        Log.d("pa", "bout 2 rd children ");

        readChildren();

        busStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open PassengersActivity
                Intent intent = new Intent(PassengersActivity.this, BusStopsActivity.class);
                startActivity(intent);
            }
        });

    }

    private void readChildren() {
        Log.d("pa123", "rding children 1");

        ChildProvider provider = new ChildProvider();

        Log.d("pa123", "rding children 2");

        provider.fetchChildren(new ChildProvider.OnDataLoadedListener() {

            @Override
            public void onDataLoaded(List<Child> childList) {
                Log.d("pa123", "rding children 3");

                if (childList != null) {
                    // Process the childList here
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(PassengersActivity.this, android.R.layout.simple_list_item_1);
BusStopChildProvider busStopChildProvider = new BusStopChildProvider();
                    int i =0;

                    for (Child child : childList) {
                        i++;
                        String strId = String.format("%05d", i);
                        String childInfo = child.getFirstName() + " " + child.getLastName() + " " + child.getId();
                        Log.d("RC2", "Child: " + childInfo);
                        adapter.add(childInfo);
                        busStopChildProvider.AddToBusStopChild(strId,"trtMNygkl5DX05kgH44s", child.getId());
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            passengerList.setAdapter(adapter);
                        }
                    });
                } else {
                    // Handle the error
                    Log.d("RC2", "Error retrieving children");
                }
            }
        });
    }
}