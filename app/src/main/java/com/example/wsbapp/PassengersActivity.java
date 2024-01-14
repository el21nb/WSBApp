package com.example.wsbapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PassengersActivity extends AppCompatActivity {
    Button busStopButton;

    private ListView passengerList;
    private TableLayout tl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passengers);
        passengerList = findViewById(R.id.passengerList); // Replace with your actual ListView ID
        busStopButton = findViewById(R.id.busStopButton);
        tl = findViewById(R.id.tl);
        Log.d("pa", "bout 2 rd children ");

        readChildren();
        passengerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected child
                Child selectedChild = (Child) parent.getItemAtPosition(position);

                // Open ChildDetailsActivity and pass the selected child's details
                Intent intent = new Intent(PassengersActivity.this, ChildInfoActivity.class);
                intent.putExtra("childId", selectedChild.getId());
                intent.putExtra("firstName", selectedChild.getFirstName());
                intent.putExtra("lastName", selectedChild.getLastName());
                // Add more details as needed
                startActivity(intent);
            }
        });
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
                  //  ArrayAdapter<Pair<String, String>> adapterKV = new ArrayAdapter<>(PassengersActivity.this, android.R.layout.simple_list_item_1);
                    Map<String, String> adapterKV = new HashMap<String, String>();
                    //BusStopChildProvider busStopChildProvider = new BusStopChildProvider();
                    int i =0;

                    for (Child child : childList) {
                        i++;
                        String strId = String.format("%05d", i);
                        String childInfo = child.getFirstName() + " " + child.getLastName();
                        Log.d("RC2", "Child: " + childInfo);
                        adapter.add(childInfo);
                      //  Pair<String, String> pair = new Pair<>(child.getId(), childInfo);
                     //   adapterKV.add(pair);
                        adapterKV.put(child.getId(), childInfo);
                        //busStopChildProvider.AddToBusStopChild(strId,"ST00001", child.getId());
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int i =0;
                            passengerList.setAdapter(adapter);
                            i ++;
                            for (Map.Entry<String, String> entry : adapterKV.entrySet()) {
                             ///   System.out.println(entry.getKey() + ":" + entry.getValue());
                                TableRow tr_head = new TableRow(PassengersActivity.this);
                                tr_head.setId(i);
                                tr_head.setBackgroundColor(Color.GRAY);        // part1
                                tr_head.setLayoutParams(new TableRow.LayoutParams(
                                        TableRow.LayoutParams.MATCH_PARENT,
                                        TableRow.LayoutParams.WRAP_CONTENT));
                                TextView label_hello = new TextView(PassengersActivity.this);
                                label_hello.setTag(entry.getKey());
                                label_hello.setText(entry.getValue());
                                label_hello.setTextColor(Color.WHITE);
                                label_hello.setPadding(5, 5, 5, 5);
                                label_hello.setClickable(true);
                                label_hello.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(PassengersActivity.this, ChildInfoActivity.class);
                                        intent.putExtra("childId", (String) v.getTag());
                                        Log.d("RC2", "intent.putExtra(\"childId\", (String) v.getTag())" +(String) v.getTag() );
                                        startActivity(intent);
                                    }
                                });
                                tr_head.addView(label_hello);// add the column to the table row here

                                tl.addView(tr_head, new  TableLayout.LayoutParams(
                                        TableRow.LayoutParams.MATCH_PARENT,                    //part4
                                        TableRow.LayoutParams.WRAP_CONTENT));
                            }
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