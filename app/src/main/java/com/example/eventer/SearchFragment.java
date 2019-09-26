package com.example.eventer;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment{

    private RecyclerView recyclerView;
    private imageAdapter3 imageAdapter3;

    private DatabaseReference databaseReference;
    private List<Upload> uploads;
    EditText editText;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);



        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_search);
        editText =(EditText)view.findViewById(R.id.textView_search);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });



        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        uploads = new ArrayList<>();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("adds");
        databaseReference.child("acc").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    Upload upload = postSnapshot.getValue(Upload.class);
                    uploads.add(upload);
                }
                imageAdapter3 = new imageAdapter3(getActivity(),uploads);
                recyclerView.setAdapter(imageAdapter3);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;

    }
    private void filter(String text){
        ArrayList<Upload> list = new ArrayList<>();
            for(Upload item:uploads){
                if(item.getEvent_name().toLowerCase().contains(text.toLowerCase())){
                    list.add(item);
                }
            }
            imageAdapter3.filterList(list);
    }
}
