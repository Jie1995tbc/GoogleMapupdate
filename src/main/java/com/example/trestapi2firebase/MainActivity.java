package com.example.trestapi2firebase;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trestapi2firebase.model.Event;
import com.example.trestapi2firebase.model.PaginatedEvents;
import com.example.trestapi2firebase.model.Pagination;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private final String TAG="firebaseTest";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference eventRef = db.collection("events");

    private EventAdapter eventAdapter;

    private EventbriteApi eventbriteApi;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EventCreateActivity.class);
                startActivity(intent);
            }
        });

        setUpRecyclerView();




        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.eventbriteapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


         eventbriteApi = retrofit.create(EventbriteApi.class);


        getEvents();


    }

    private void setUpRecyclerView() {
        Query query = eventRef.orderBy("changed", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Event> options = new FirestoreRecyclerOptions.Builder<Event>()
                .setQuery(query, Event.class)
                .build();

        eventAdapter = new EventAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(eventAdapter);

        eventAdapter.setOnClickListener(new EventAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Event event = documentSnapshot.toObject(Event.class);
                Intent eventIntent = new Intent(MainActivity.this, EventActivity.class);

                if (event.getLogo() != null) {
                    eventIntent.putExtra("image_url",event.getLogo().getOriginal().getUrl());
                } else {
                    eventIntent.putExtra("image_url","https://github.com/Fitness-Event-APP/Fitness/blob/master/HatchfulExport-All/instagram_profile_image.png");
                }

                eventIntent.putExtra("title",event.getName().getText());
                eventIntent.putExtra("description",event.getDescription().getText());
                eventIntent.putExtra("startTime",event.getStart().getLocal());
                eventIntent.putExtra("endTime",event.getEnd().getLocal());
                eventIntent.putExtra("address", event.getId());
                //////
                eventIntent.putExtra("latitude", event.getVenue().getLatitude());
                eventIntent.putExtra("longitude",event.getVenue().getLongitude());


                startActivity(eventIntent);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        eventAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        eventAdapter.stopListening();
    }



    private void getEvents() {

        Integer[] pages = new Integer[200];
        for (int i = 0; i < pages.length; i++) {
            pages[i] = i+1;
        }

        Call<PaginatedEvents> call = eventbriteApi.getPaginatedEvents(108, pages,"venue","IRN4X6MKLUWHLIFGBEDY");

        call.enqueue(new Callback<PaginatedEvents>() {
            @Override
            public void onResponse(Call<PaginatedEvents> call, Response<PaginatedEvents> response) {
                if (!response.isSuccessful()) {
                    Log.d("CallonResponse","failed! Code: " + response.code());
                    return;
                }

                PaginatedEvents paginatedEvents = response.body();



                Pagination pagination = paginatedEvents.getPagination();
                db.collection("Pagination").document("test4").
                        set(pagination).
                        addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("Pagination","successfully add");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("Pagination","error adding document",e);
                            }
                        });

                for (Event event : paginatedEvents.getEvents()) {

                    db.collection("events").document(event.getId())
                            .set(event)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "DocumentSnapshot added with events ");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error adding document", e);
                                }
                            });
                }
            }

            @Override
            public void onFailure(Call<PaginatedEvents> call, Throwable t) {
                Log.w("CallonFailure","Throwable:" + t.getMessage());
            }
        });
    }
}
