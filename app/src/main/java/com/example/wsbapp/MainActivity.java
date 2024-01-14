package com.example.wsbapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button viewPassengersButton;
    TextView passengerList;
    String childrenStr = "Children:\n";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DoStuff();
    }

    private void DoStuff() {
        Log.d("MyMain", "hello");
         makeBusStops();
         makeChildren();


        viewPassengersButton = findViewById(R.id.button);
        passengerList = findViewById(R.id.textView3);

        viewPassengersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open PassengersActivity
                Intent intent = new Intent(MainActivity.this, PassengersActivity.class);
                startActivity(intent);
            }
        });
    }

    public void makeBusStops () {
        BusProvider provider = new BusProvider();
        String[] name = {"Bournebridge Lane", "Brentwood Police Station", "Palmerston Road", "Traps Hill"};
        String[] address = {"Bournebridge Lane/High Street Junction Sk6 8YY", "Brentwood Rd/High Street Junction Sk6 8YY", "Palmerston Road/High Street Junction Sk6 8YY", "Traps Hill/High Street Junction Sk6 8YY"};
        for (int i = 0; i < 4; i++) {
            String stpId = "ST" + String.format("%05d", i + 1);
            provider.addBusStop(stpId, name[i], address[i]);
        }

    }

    public void makeChildren () {
        ChildProvider provider = new ChildProvider();
        BusStopChildProvider busStopChildProvider = new BusStopChildProvider();
        String[] firstname = {"John-James", "Jack-James", "Jannet-James", "Jane-James"};
        String[] lastName = {"Jones", "Johnson", "Jenkins", "Jordan"};
        String[] contactName = {"John (Father)", "Jack (Father)", "Jannet (Mother)", "Jane  (Other Mother)"};
        String[] contactDetail = {"0151 123456", "Johnson@gmail.com", "07770876987", "07776344567"};
        for (int i = 0; i < 4; i++) {
            String childId = "CH" + String.format("%05d", i + 1);
            String bSCId = String.format("%05d", i+1);
            //create child in database
            provider.addChild(childId, firstname[i], lastName[i], contactName[i], contactDetail[i]);
            //assign child to a bus stop
            if(i==0){
                busStopChildProvider.AddToBusStopChild(bSCId,"ST00001", childId);
            }
            else if(i==1){
                busStopChildProvider.AddToBusStopChild(bSCId,"ST00003", childId);
            }
            else if(i==2||i==3){
                busStopChildProvider.AddToBusStopChild(bSCId,"ST00004", childId);
            }
        }

    }

    private String[] readChildren(ChildProvider provider) {
        final String[] chStr = {""};
        provider.fetchChildren(new ChildProvider.OnDataLoadedListener() {
            @Override
            public void onDataLoaded(List<Child> childList) {
                if (childList != null) {
                    // Process the childList here
                    for (Child child : childList) {
                        Log.d("RC2", "Child: " + child.getFirstName() + " " + child.getLastName());
                        chStr[0] += "\n" + child.getFirstName() + " " + child.getLastName();
                    }
                } else {
                    // Handle the error
                    Log.d("RC2", "Error retrieving children");
                }
            }
        });
        return chStr;
    }
}