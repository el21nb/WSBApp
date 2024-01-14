package com.example.wsbapp;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class BusProvider {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public BusProvider() {
    }

    public void addBusStop(String stpId, String name, String address)
    {
        BusStop stopper = new BusStop(name, address);
        Log.w("DataAdding", "*************** addbustrop 1");
        db.collection("BusStop").document(stpId)
                .set(stopper)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("DataAdding", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("DataAdding", "Error writing document", e);
                    }
                });
    }

    public interface OnBusStopDataLoadedListener {
        void onDataLoaded(List<BusStop> busStopList);
    }

   public void fetchBusStops(final OnBusStopDataLoadedListener listener) {
        final List<BusStop> busStopList = new ArrayList<>();
        Log.d("pa123", "reading bus stops 5");

        db.collection("BusStop")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Log.d("pa123", "reading bus stops 6");

                        if (task.isSuccessful()) {
                            Log.d("pa123", "reading bus stops 7");

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                BusStop busStop = new BusStop((String) document.get("name"), (String) document.get("address"));
                                busStop.setId(document.getId());
                                busStopList.add(busStop);
                                Log.d("RC2", document.getId() + " => " + document.getData());

                            }
                            listener.onDataLoaded(busStopList);
                        } else {
                            Log.d("RC2", "Error getting documents: ", task.getException());
                            listener.onDataLoaded(null);
                        }
                    }
                });
    }

    public void AddToBusStop(String stopId, String childId)
    {
        BusStopChild bsChild = new BusStopChild(stopId, childId);

        db.collection("BusStopChildren").document()
                .set(bsChild)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("DataAdding", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("DataAdding", "Error writing document", e);
                    }
                });
    }

}
