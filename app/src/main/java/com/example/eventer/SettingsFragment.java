package com.example.eventer;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class SettingsFragment extends Fragment implements imageAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private imageAdapter imageAdapter1;

    private DatabaseReference databaseReference;
    private FirebaseStorage firebaseStorage;
    private ValueEventListener valueEventListener;

    private List<Upload> uploads;

    FirebaseAuth firebaseAuth;

    private TextView _user_email,_help;
    private Button _user_logout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        _help = (TextView)view.findViewById(R.id.help);

        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();


        _user_email = (TextView)view.findViewById(R.id.user_email);
        _user_email.setText(firebaseUser.getEmail());

        _user_logout = (Button)view.findViewById(R.id.user_logout);

        _user_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                Intent intent = new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        uploads = new ArrayList<>();
        imageAdapter1 = new imageAdapter(getActivity(),uploads);
        imageAdapter1.setOnItemClickListener(SettingsFragment.this);
        recyclerView.setAdapter(imageAdapter1);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        firebaseStorage = FirebaseStorage.getInstance();
        databaseReference = database.getReference("uploads");

        FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();

        valueEventListener=databaseReference.child(mFirebaseUser.getUid()).child("acc")
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                uploads.clear();
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    Upload upload = postSnapshot.getValue(Upload.class);
                    upload.setKey(postSnapshot.getKey());
                    uploads.add(upload);
                }
                imageAdapter1.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        _help = (TextView)view.findViewById(R.id.help);
        _help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),HelpActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onDeleteClick(int position) {
        Upload selectedItem = uploads.get(position);
        final String selectedKey = selectedItem.getKey();


        StorageReference imgRef = firebaseStorage.getReferenceFromUrl(selectedItem.getImageView());
        imgRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                databaseReference.child(mFirebaseUser.getUid()).child("acc").child(selectedKey).removeValue();
                //databaseReference2.child("acc").child(selectedKey).removeValue();
                Toast.makeText(getActivity(),"Post Deleted",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        databaseReference.removeEventListener(valueEventListener);
    }
}
