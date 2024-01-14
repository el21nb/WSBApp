package com.example.wsbapp;
import android.util.Log;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ChildProvider {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public ChildProvider() {
    }
        public void fetchChild(String childId, final ChildFetchListener listener) {
            DocumentReference docRef = db.collection("Children").document(childId);

            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        Child child = documentSnapshot.toObject(Child.class);
                        listener.onChildFetched(child);
                        Log.d("FC", "DocumentSnapshot data: " + documentSnapshot.getData());
                    } else {
                        Log.d("FC", "No such document");
                        listener.onChildFetched(null); // or handle this case accordingly
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("FC", "get failed with ", e);
                    listener.onFetchFailed(e);
                }
            });
        }
    public interface ChildFetchListener {
        void onChildFetched(Child child);
        void onFetchFailed(Exception e);
    }
    public void addChild(String cHid, String firstName, String lastName, String contact, String contactDetail) {
        Child child = new Child(firstName, lastName);
        List<Contact> childContacts = new ArrayList<>();
        childContacts.add(new Contact(contact, contactDetail));
        child.setChildContacts(childContacts);

        db.collection("Children").document(cHid)
                .set(child)
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

    public interface OnDataLoadedListener {
        void onDataLoaded(List<Child> childList);
    }

public void fetchChildren(final OnDataLoadedListener listener) {
    final List<Child> childList = new ArrayList<>();
    Log.d("pa123", "rding children 5");

    db.collection("Children")
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    Log.d("pa123", "rding children 6");

                    if (task.isSuccessful()) {
                        Log.d("pa123", "rding children 7");

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d("pa123", "rding children 8");
                            Child child = new Child((String) document.get("firstName"), (String) document.get("lastName"));
                            child.setId(document.getId());
                                Log.d("RC2", "child lastName = " + child.getLastName() );
                               // Log.d("RC2", "childList.sizeBefore = " + childList.size());
                                childList.add(child);
                               //Log.d("RC2", "childList.sizeA = " + childList.size());
                                Log.d("RC2", document.getId() + " => " + document.getData());
                          // childList.add(document.toObject(Child.class));
                            Log.d("pa123", "rding children 9");

                            Log.d("RC2", "childlist size = " + childList.size());
                        }
                        listener.onDataLoaded(childList);
                    } else {
                        Log.d("RC2", "Error getting documents: ", task.getException());
                        listener.onDataLoaded(null);
                    }
                }
            });
}


//    public void readChildren(final OnDataLoadedListener listener) {
//        final List<Child> childList = new ArrayList<>();
//
//        db.collection("Children")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                childList.add(document.toObject(Child.class));
//                                Log.d("RC2", "childlist size = " + childList.size());
//                            }
//                            listener.onDataLoaded(childList);
//                        } else {
//                            Log.d("RC2", "Error getting documents: ", task.getException());
//                            listener.onDataLoaded(null);
//                        }
//                    }
//                });
//    }

    public void addJacket(String identifier)
    {

    }


}
