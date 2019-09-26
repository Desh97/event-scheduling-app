package com.example.eventer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PoliticsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private imageAdapter2 imageAdapter;


    private DatabaseReference databaseReference;
    private List<Upload> uploads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_politics);

        recyclerView = (RecyclerView) findViewById(R.id.politics_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(PoliticsActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        uploads = new ArrayList<>();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("adds");

        databaseReference.child("acc").orderByChild("category").equalTo("Politics").addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    Upload upload = postSnapshot.getValue(Upload.class);
                    upload.setKey(postSnapshot.getKey());
                    uploads.add(upload);
                }
                imageAdapter = new imageAdapter2(PoliticsActivity.this,uploads);
                recyclerView.setAdapter(imageAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
