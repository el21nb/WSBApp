package com.example.wsbapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class BusStopsActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    Button busStopButton;

    private ListView listView;
    private ArrayAdapter<String> adapter;

    private List<String> dataList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_stops);
        listView = findViewById(R.id.busStopList); // Replace with your actual ListView ID
        dataList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);

        readBusStopsData();        readBusStopsData();
    }
    public void readBusStopsData() {
        BusStopChildProvider provider = new BusStopChildProvider();
        Log.d("RBSD", "***1 ");

        db.collection("BusStop")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Log.d("RBSD", "***2 ");

                        if (task.isSuccessful()) {
                            Log.d("RBSD", "***3 ");

                            for (QueryDocumentSnapshot busStopDocument : task.getResult()) {
                                Log.d("RBSD", "***4 ");

                                String busStopId = busStopDocument.getId();

                                provider.fetchBusStopChildData(busStopId, new BusStopChildProvider.OnBusStopChildDataLoadedListener() {
                                    @Override
                                    public void onDataLoaded(List<BusStopChild> busStopChildList) {
                                        Log.d("RBSD", "***5 ");

                                        provider.fetchChildrenForBusStop(busStopDocument, busStopChildList, new BusStopChildProvider.OnChildrenDataLoadedListener() {
                                            @Override
                                            public void onDataLoaded(List<Child> childrenList) {
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        // Add bus stop name
                                                        String busStopName = busStopDocument.getString("name");
                                                        dataList.add("Bus Stop: " + busStopName);

                                                        // Add each child to the list
                                                        for (Child child : childrenList) {
                                                            dataList.add("  Child: " + child.getFirstName());
                                                        }

                                                        // Notify the adapter that the data has changed
                                                        adapter.notifyDataSetChanged();
                                                    }
                                                });
                                            }
                                        });
                                    }
                                });
                            }
                        } else {
                            Log.d("RBSD", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}
//    public void readBusStopsData() {
//        BusStopChildProvider provider = new BusStopChildProvider(); // Create an instance outside the loop
//        Log.d("RBSD", "***1 ");
//
//        db.collection("BusStop")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        Log.d("RBSD", "***2 ");
//
//
//                        if (task.isSuccessful()) {
//                            Log.d("RBSD", "***3 ");
//
//                            for (QueryDocumentSnapshot busStopDocument : task.getResult()) {
//                                Log.d("RBSD", "***4 ");
//
//                                String busStopId = busStopDocument.getId();
//
//                                // Fetch BusStopChild documents for the current BusStop
//                                provider.fetchBusStopChildData(busStopId, new BusStopChildProvider.OnBusStopChildDataLoadedListener() {
//                                    @Override
//                                    public void onDataLoaded(List<BusStopChild> busStopChildList) {
//                                        Log.d("RBSD", "***5 ");
//
//                                        // Fetch Children for each BusStopChild
//                                        provider.fetchChildrenForBusStop(busStopDocument, busStopChildList, new BusStopChildProvider.OnChildrenDataLoadedListener() {
//                                            @Override
//                                            public void onDataLoaded(List<Child> childrenList) {
//
//                                                // Print or process the data
//                                                Log.d("RBSD", "Bus Stop: " + busStopDocument.getString("name"));
//                                                for (Child child : childrenList) {
//                                                    Log.d("RBSD", "  Child: " + child.getFirstName());
//                                                }
//                                            }
//                                        });
//                                    }
//                                });
//                            }
//                        } else {
//                            Log.d("RBSD", "Error getting documents: ", task.getException());
//                        }
//                    }
//                });
//    }
//
//}
//
//
//    private void readBusStops() {
//        Log.d("pa123", "rding children 1");
//
//        BusProvider provider = new BusProvider();
//
//        Log.d("pa123", "rding children 2");
//
//        provider.fetchBusStops(new BusProvider.OnBusStopDataLoadedListener() {
//
//            @Override
//            public void onDataLoaded(List<BusStop> busStopList) {
//                Log.d("pa123", "rding children 3");
//                BusStopChildProvider busChildProvider = new BusStopChildProvider();
//                if (busStopList != null) {
//                    // Process the childList here
//                    ArrayAdapter<String> adapter = new ArrayAdapter<>(BusStopsActivity.this, android.R.layout.simple_list_item_1);
//                    for (BusStop busStop : busStopList) {
//                        String adapterStr = "";
//                        String stopId = busStop.getId();
//                        db.collection("BusStopChild")
//                                .whereEqualTo("busStopId", stopId)(new OnCompleteListener<QuerySnapshot>() {
//                            @Override
//                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                if (task.isSuccessful()) {
//                                    for (QueryDocumentSnapshot document : task.getResult()) {
//                                        adapterStr+= document.getChildId();
//                                    }
//                                    }
//                                } else {
//                                    Log.d("BSA", "Error getting documents: ", task.getException());
//                                }
//                            }
//
//
//
//
//
//                        busChildProvider.fetchBusStopChild(new BusStopChildProvider.OnBusStopChildDataLoadedListener() {
//
//                            @Override
//                            public void onDataLoaded(List<BusStopChild> list) {
//                                Log.d("pa123", "rding children 3");
//
//                                if (list != null) {
//                                    Log.d("FBS", "loaded bus stop childs");
//                                    // Process the childList here
//                                    ArrayAdapter<String> adapter = new ArrayAdapter<>(BusStopsActivity.this, android.R.layout.simple_list_item_1);
//
//                                    for (BusStopChild child : list) {
//                                        Log.d("FBS", "for loop 1");
//
//                                        String childInfo = child.getChildId() + " " + child.getBusStopId();
//                                        Log.d("FBS", "for loop 2");
//
//                                        Log.d("RC2", "Child: " + childInfo);
//                                        adapter.add(childInfo);
//                                        Log.d("FBS", "for loop 3");
//
//                                    }
//
//                                    runOnUiThread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            Log.d("FBS", "setting adapter");
//
//                                            busStopListView.setAdapter(adapter);
//                                        }
//                                    });
//                                } else {
//                                    // Handle the error
//                                    Log.d("FBS", "Error retrieving children");
//                                }
//                            }
//                        });
//                        Log.d("FBS", "bsStr setting");
//
//                        String bsStr = busStop.getAddress() + " " + busStop.getName();
//                        adapter.add(bsStr);
//                    }
//
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            busStopListView.setAdapter(adapter);
//                        }
//                    });
//                } else {
//                    // Handle the error
//                    Log.d("RC2", "Error retrieving children");
//                }
//            }
//        });
//    }

//    private void readBusStops() {
//        Log.d("pa123", "rding children 1");
//
//        BusProvider provider = new BusProvider();
//
//        Log.d("pa123", "rding children 2");
//
//        provider.fetchBusStops(new BusProvider.OnBusStopDataLoadedListener() {
//
//            @Override
//            public void onDataLoaded(List<BusStop> busStopList) {
//                Log.d("pa123", "rding children 3");
//                BusStopChildProvider busChildProvider = new BusStopChildProvider();
//                if (busStopList != null) {
//                    // Process the childList here
//                    ArrayAdapter<String> adapter = new ArrayAdapter<>(BusStopsActivity.this, android.R.layout.simple_list_item_1);
//                    for (BusStop busStop : busStopList) {
//                        String stopId = busStop.getId();
//                        db.collection("BusStopChild")
//                                        .whereEqualTo("busStopId", stopId);
//
//
//
//
//                        busChildProvider.fetchBusStopChild(new BusStopChildProvider.OnBusStopChildDataLoadedListener() {
//
//                            @Override
//                            public void onDataLoaded(List<BusStopChild> list) {
//                                Log.d("pa123", "rding children 3");
//
//                                if (list != null) {
//                                    Log.d("FBS", "loaded bus stop childs");
//                                    // Process the childList here
//                                    ArrayAdapter<String> adapter = new ArrayAdapter<>(BusStopsActivity.this, android.R.layout.simple_list_item_1);
//
//                                    for (BusStopChild child : list) {
//                                        Log.d("FBS", "for loop 1");
//
//                                        String childInfo = child.getChildId() + " " + child.getBusStopId();
//                                        Log.d("FBS", "for loop 2");
//
//                                        Log.d("RC2", "Child: " + childInfo);
//                                        adapter.add(childInfo);
//                                        Log.d("FBS", "for loop 3");
//
//                                    }
//
//                                    runOnUiThread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            Log.d("FBS", "setting adapter");
//
//                                            busStopListView.setAdapter(adapter);
//                                        }
//                                    });
//                                } else {
//                                    // Handle the error
//                                    Log.d("FBS", "Error retrieving children");
//                                }
//                            }
//                        });
//                        Log.d("FBS", "bsStr setting");
//
//                        String bsStr = busStop.getAddress() + " " + busStop.getName();
//                        adapter.add(bsStr);
//                    }
//
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            busStopListView.setAdapter(adapter);
//                        }
//                    });
//                } else {
//                    // Handle the error
//                    Log.d("RC2", "Error retrieving children");
//                }
//            }
//        });
//    }

//    private void readBusStops() {
//        Log.d("pa123", "rding children 1");
//
//        DatabaseProvider provider = new DatabaseProvider();
//
//        Log.d("pa123", "rding children 2");
//
//        provider.fetchBusStops(new DatabaseProvider.OnBusStopDataLoadedListener() {
//
//            @Override
//            public void onBusStopDataLoaded(List<BusStop> busStopList) {
//                Log.d("pa123", "rding BS 3");
//
//                if (busStopList != null) {
//                    ArrayAdapter<String> adapter = new ArrayAdapter<>(BusStopsActivity.this, android.R.layout.simple_list_item_1);
//
//                    for (BusStop busStop : busStopList) {
//                        String busStopStr = busStop.getName() + "\n Address: " + busStop.getAddress();
//                        Log.d("RC2", "BusStop: " + busStop);
//                        adapter.add(busStopStr);
//                    }
//
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//
//                            passengerList.setAdapter(adapter);
//                        }
//                    });
//                } else {
//                    // Handle the error
//                    Log.d("RC2", "Error retrieving busStop");
//                }
//            }
//       });
//    }
