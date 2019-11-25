package com.example.trestapi2firebase;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;


public class EventActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = "EventActivity";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    private String longitude;
    private String latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG,"hello world");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_fragment);

        supportMapFragment.getMapAsync(this);


        getIncomingIntent();
    }


    public void onMapReady(GoogleMap googleMap) {
        Log.d("longitude: ", longitude + "**********************");
        Log.d("latitude: ", latitude + "**********************");

        double d_longitude = Double.parseDouble(longitude);
        double d_latitude = Double.parseDouble(latitude);
        LatLng coordinate = new LatLng(d_latitude,d_longitude);
        googleMap.addMarker(new MarkerOptions().position(coordinate));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinate,12));

    }

    private void getIncomingIntent() {
        Log.d(TAG,"getIncomingIntent: Checking for incoming intents.");
        if (getIntent().hasExtra("image_url") && getIntent().hasExtra("title")
                && getIntent().hasExtra("description") && getIntent().hasExtra("startTime")
                && getIntent().hasExtra("endTime") && getIntent().hasExtra("address") && getIntent().hasExtra("latitude")
                && getIntent().hasExtra("longitude")) {
            Log.d(TAG,"getIncomingIntent: found intent extras");

            String imageUrl = getIntent().getStringExtra("image_url");
            String title = getIntent().getStringExtra("title");
            String description = getIntent().getStringExtra("description");
            String startTime = getIntent().getStringExtra("startTime");
            String endTime = getIntent().getStringExtra("endTime");
            String address = getIntent().getStringExtra("address");

            longitude = getIntent().getStringExtra("longitude");
            latitude = getIntent().getStringExtra("latitude");



            setEvent(imageUrl, title, description, startTime, endTime, address);
        }
    }

    private void setEvent(String imageUrl, String title, String description, String startTime, String endTime, String address) {
        Log.d(TAG,"Set Event");

        ImageButton imageButton = findViewById(R.id.location_icon);

        ImageView imageView = findViewById(R.id.imageViewHR);
        Picasso.get().load(imageUrl).into(imageView);

        TextView textViewTitle = findViewById(R.id.title_eventPage);
        textViewTitle.setText(title);

        TextView textViewDescription = findViewById(R.id.contentOfDescription);
        textViewDescription.setText(description);

        TextView textViewtime = findViewById(R.id.time);
        textViewtime.setText(startTime + "-" + endTime);

        TextView textViewAddress = findViewById(R.id.location);
        textViewAddress.setText(address);
    }

    public void ToMapApp(View v) {
        // Create a Uri from an intent string. Use the result to create an Intent.
        Log.d(TAG,"Go to Google Map *****");
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + latitude + "," + longitude);

        // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        // Make the Intent explicit by setting the Google Maps package
        mapIntent.setPackage("com.google.android.apps.maps");

        // Attempt to start an activity that can handle the Intent
        startActivity(mapIntent);




    }



}
