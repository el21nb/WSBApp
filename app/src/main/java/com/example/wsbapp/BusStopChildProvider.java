package com.example.wsbapp;
import android.util.Log;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class BusStopChildProvider {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public BusStopChildProvider() {
    }

    public void addBusStopChild(String name, String address) {
        BusStopChild stopper = new BusStopChild(name, address);
        Log.w("DataAdding", "*************** addbustrop 1");
        db.collection("BusStopChild").document(name)
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

    public interface OnBusStopChildDataLoadedListener {
        void onDataLoaded(List<BusStopChild> busStopChildList);
    }

    public void fetchBusStopChild(final OnBusStopChildDataLoadedListener listener) {
        final List<BusStopChild> busStopChildList = new ArrayList<>();
        Log.d("FBS", "reading bus stops 5");

        db.collection("BusStopChild")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Log.d("FBS", "reading bus stops 6");

                        if (task.isSuccessful()) {
                            Log.d("FBS", "reading bus stops 7");

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                BusStopChild busStopChild = new BusStopChild((String) document.get("busStopId"), (String) document.get("childId"));
                                // busStopChild.setId(document.getId());
                                busStopChildList.add(busStopChild);
                                Log.d("FBS", document.getId() + " => " + document.getData());

                            }
                            listener.onDataLoaded(busStopChildList);
                        } else {
                            Log.d("FBS", "Error getting documents: ", task.getException());
                            listener.onDataLoaded(null);
                        }
                    }
                });
    }

    public void AddToBusStopChild(String id, String stopId, String childId) {
        BusStopChild bsChild = new BusStopChild(stopId, childId);

        db.collection("BusStopChild").document(id)
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


    public void fetchBusStopChildData(String busStopId, OnBusStopChildDataLoadedListener listener) {
        db.collection("BusStopChild")
                .whereEqualTo("busStopId", busStopId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Log.d("RBSD", "FBSCD 1");
                        if (task.isSuccessful()) {
                            try {
                                Log.d("RBSD", "FBSCD 2");
                                List<BusStopChild> busStopChildList = new ArrayList<>();
                                for (QueryDocumentSnapshot document : task.getResult()) {

                                        if (document != null) {
                                            Log.d("RBSD", "FBSCD 3");
                                            Log.d("RBSD", "doc = " + document.getData());

                                            BusStopChild busStopChild = new BusStopChild((String) document.get("busStopId"), (String) document.get("childId"));
                                            //BusStopChild busStopChild = document.toObject(BusStopChild.class);
                                            Log.d("RBSD", "made list" + busStopChild.getBusStopId());
                                            busStopChildList.add(busStopChild);
                                            Log.d("RBSD", "adding BSC to list" + busStopChild.getBusStopId());
                                        }
                                        Log.d("RBSD", "FBSCD 4");
                                }
                                listener.onDataLoaded(busStopChildList);
                                } catch (Exception e) {
                                Log.d("RBSD", "FBSCD catch 22- " + e.getMessage());
                            }

                        } else {
                            Log.d("RBSD", "Error getting documents: ", task.getException());
                            listener.onDataLoaded(null);
                        }
                    }
                });
    }

    public void fetchChildrenForBusStop(QueryDocumentSnapshot busStopDocument, List<BusStopChild> busStopChildList, OnChildrenDataLoadedListener listener) {
        Log.d("RBSD", "In fetchChildrenForBusStop");

        List<String> childIds = new ArrayList<>();
        Log.d("RBSD","busStopChildList size = " + busStopChildList.size());


        for (BusStopChild busStopChild : busStopChildList) {
            childIds.add(busStopChild.getChildId());
            Log.d("RBSD", "child id = " + busStopChild.getChildId());
        }
        Log.d("RBSD", "childIds.size() = " + childIds.size() );

        db.collection("Children")
                .whereIn(FieldPath.documentId(), childIds)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            try {
                            List<Child> childrenList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(document!=null) {
                                    Child child = document.toObject(Child.class);
                                    Log.d("RBSD", "adding child to list" + child.getFirstName());
                                    childrenList.add(child);
                                }
                            }
                            listener.onDataLoaded(childrenList);
                        } catch (Exception e) {
                                Log.d("RBSD", "FBSCD catch 23- " + e.getMessage());
                            }
                        }else {
                            Log.d("RC2", "Error getting documents: ", task.getException());
                            listener.onDataLoaded(null);
                        }
                    }
                });
    }


    public interface OnChildrenDataLoadedListener {
        void onDataLoaded(List<Child> childrenList);
    }
}