package com.example.eventer;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class UploadFragmnet extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 100;

    private Spinner _spinner;
    private Button _choose_image,_upload_button;
    private EditText _event_name,_date,_time,_venue,_entrance,_organizer,_description,_contact;
    private TextView _show_upload;
    private ImageView _imageView;
    private ProgressBar _progressBar;

    private Uri mImageUrl;

    private StorageReference storageReference;
    private DatabaseReference databaseReference,databaseReference2;
    FirebaseAuth mAuth;


    private StorageTask storageTask;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upload, container, false);

        _spinner = (Spinner)view.findViewById(R.id.spinner);
        _choose_image = (Button)view.findViewById(R.id.choose_image);
        _upload_button = (Button)view.findViewById(R.id.upload_button);
        _event_name = (EditText)view. findViewById(R.id.event_name);
        _show_upload = (TextView)view.findViewById(R.id.show_upload);
        _imageView = (ImageView)view.findViewById(R.id.imageView);
        _progressBar = (ProgressBar)view.findViewById(R.id.progressBar);

        _date = (EditText)view.findViewById(R.id.date);
        _time = (EditText)view.findViewById(R.id.time);
        _venue = (EditText)view.findViewById(R.id.venue);
        _entrance = (EditText)view.findViewById(R.id.entrance);
        _organizer = (EditText)view.findViewById(R.id.organizer);
        _description = (EditText)view.findViewById(R.id.description);
        _contact = (EditText)view.findViewById(R.id.contact);
        _spinner = (Spinner)view.findViewById(R.id.spinner);

        mAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference("uploads");
        databaseReference = FirebaseDatabase.getInstance().getReference("uploads");
        databaseReference2 = FirebaseDatabase.getInstance().getReference("adds");

        List<String> categories = new ArrayList<>();
        categories.add(0," Choose category");
        categories.add("IT");
        categories.add("Music");
        categories.add("Art");
        categories.add("Social");
        categories.add("Culture");
        categories.add("Sports");
        categories.add("Dancing");
        categories.add("Politics");

        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item,categories);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        _spinner.setAdapter(dataAdapter);

        _spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                if(parent.getItemAtPosition(i).equals("Choose Category")){

                }else{
                    String item = parent.getItemAtPosition(i).toString();
                    // Toast.makeText(parent.getContext(),"Selected:"+item,Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        _choose_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fileChooser();
            }
        });

        _upload_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(storageTask !=null && storageTask.isInProgress()){
                    Toast.makeText(getActivity(),"upload in progress",Toast.LENGTH_SHORT).show();
                }else {
                    UploadFile();
                }
            }
        });

        _show_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new SettingsFragment());
            }
        });


        return view;





    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }


    private void fileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() !=null){
            mImageUrl = data.getData();

            Picasso.with(getActivity()).load(mImageUrl).into(_imageView);
            _imageView.setImageURI(mImageUrl);

        }
    }

    private String getFileExtention(Uri uri){
        ContentResolver contentResolver = getActivity().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }

    private void UploadFile(){

        if(mImageUrl!=null){

            final StorageReference fileReference = storageReference.child(System.currentTimeMillis()+ "."
                    + getFileExtention(mImageUrl));

            storageTask = fileReference.putFile(mImageUrl)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    _progressBar.setProgress(0);
                                }
                            },300);

                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Toast.makeText(getActivity(),"upload successfully",Toast.LENGTH_LONG).show();
                                    Upload upload = new Upload(_event_name.getText().toString().trim(),uri.toString(),_spinner.getSelectedItem().toString().trim(),_date.getText().toString().trim(),
                                            _time.getText().toString().trim(),_venue.getText().toString().trim(),_entrance.getText().toString().trim(),_organizer.getText().toString().trim()
                                            ,_description.getText().toString().trim(),_contact.getText().toString().trim());
                                    //String uid = databaseReference.push().getKey();
                                    FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
                                    FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                                    databaseReference.child(mFirebaseUser.getUid()).child("acc").push().setValue(upload);
                                    databaseReference2.child("acc").push().setValue(upload);

                                }
                            });


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            _progressBar.setProgress((int)progress);
                        }

                    });
        }else {
            Toast.makeText(getActivity(),"No file selected",Toast.LENGTH_LONG).show();
        }

    }


}
