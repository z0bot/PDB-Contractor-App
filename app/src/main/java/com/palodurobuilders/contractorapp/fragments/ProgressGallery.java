package com.palodurobuilders.contractorapp.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.palodurobuilders.contractorapp.R;
import com.palodurobuilders.contractorapp.adapters.GalleryRoomViewAdapter;
import com.palodurobuilders.contractorapp.databases.ImageDatabase;
import com.palodurobuilders.contractorapp.interfaces.IHandleChildRecyclerClick;
import com.palodurobuilders.contractorapp.interfaces.IQueryRoomsCallback;
import com.palodurobuilders.contractorapp.models.Image;
import com.palodurobuilders.contractorapp.models.Room;
import com.palodurobuilders.contractorapp.models.Property;
import com.palodurobuilders.contractorapp.pages.DisplayImage;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class ProgressGallery extends Fragment implements IHandleChildRecyclerClick
{
    RecyclerView _recyclerView;
    GalleryRoomViewAdapter _recyclerViewAdapter;
    Uri _imagePath;
    ImageView _mSelectedImageView;
    FirebaseStorage _storage;
    StorageReference _storageReference;
    String _propertyID, _firebaseImageUrlString, _roomID;
    private String _inputText = "";
    FloatingActionButton _addRoomButton;
    Bitmap _bitmap;
    int _imageSize;

    private static final int RESULT_LOAD_IMAGE = 1;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_progress_gallery, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        _recyclerView = Objects.requireNonNull(getView()).findViewById(R.id.recyclerview_progress_gallery_parent);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        _addRoomButton = view.findViewById(R.id.fab_add_room);
        _addRoomButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                addButtonClicked();
            }
        });

        _recyclerView.setLayoutManager(layoutManager);

        // gets the property ID from passed in arg of page
        _propertyID = Objects.requireNonNull(this.getArguments()).getString(Property.PROPERTY_ID);

        // allows us to access storage bucket
        _storage = FirebaseStorage.getInstance();
        // creating a storage reference to upload and download files
        _storageReference = _storage.getReference();

        downloadRooms(new IQueryRoomsCallback()
        {
            @Override
            public void onCallback(List<Room> roomList)
            {
                Collections.sort(roomList);
                setRecyclerViewAdapter(roomList);
            }
        });
    }

    @Override
    public void onClick(View view, Image image)
    {
        if(image.getImageURL() == null)
        {
            _roomID = image.getRoomID();
            //setting up the intent for file browsing
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/jpeg");
            startActivityForResult(Intent.createChooser(intent, "Select a JPEG Image"), RESULT_LOAD_IMAGE);
        }
        else
        {
            //Toast.makeText(getActivity(), "You selected an image.", Toast.LENGTH_SHORT).show();
            ImageDatabase imageDatabase = ImageDatabase.getInstance(getActivity());
            try
            {
                imageDatabase.imageDao().insertImage(image);
            }
            catch(Exception e)
            {
                imageDatabase.imageDao().updateImage(image);
            }
            Intent intent = new Intent(getActivity(), DisplayImage.class);
            intent.putExtra("imageURL", image.getImageURL());
            startActivity(intent);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK)
        {
            if(requestCode == RESULT_LOAD_IMAGE)
            {
                _imagePath = data.getData();

                //get image size
                InputStream inputStream;
                try
                {
                    inputStream = Objects.requireNonNull(getContext()).getContentResolver().openInputStream(_imagePath);
                    _bitmap = BitmapFactory.decodeStream(inputStream);
                }
                catch (FileNotFoundException e)
                {
                    Toast.makeText(getActivity(), "Image not supported", Toast.LENGTH_SHORT).show();
                }

                _imageSize = _bitmap.getRowBytes() * _bitmap.getHeight();
                beginImageUpload();
            }
        }
    }
    private void setRecyclerViewAdapter(List<Room> roomList)
    {
        _recyclerViewAdapter = new GalleryRoomViewAdapter(roomList);
        _recyclerViewAdapter.setClickListener(this);
        _recyclerView.setAdapter(_recyclerViewAdapter);
    }

    public void downloadRooms(final IQueryRoomsCallback callback)
    {
        final List<Room> roomList = new ArrayList<>();
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference roomReference = db.collection("Projects").document(_propertyID).collection("Rooms");
        roomReference.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
                {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task)
                    {
                        if(task.isSuccessful())
                        {
                            for(QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult()))
                            {
                                final Room room = document.toObject(Room.class);
                                CollectionReference imageReference = db.collection("Projects").document(_propertyID).collection("Rooms").document(room.getRoomID()).collection("Images");
                                imageReference.get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
                                        {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task)
                                            {
                                                if(task.isSuccessful())
                                                {
                                                    for(QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult()))
                                                    {
                                                        room.addImage(document.toObject(Image.class));
                                                    }
                                                    roomList.add(room);
                                                    callback.onCallback(roomList);
                                                }
                                            }
                                        });
                            }
                            //callback.onCallback(roomList);
                        }
                    }
                });
    }

    public void beginImageUpload()
    {
        // this child reference now points to images/GUID
        final StorageReference imageReference = _storageReference.child("images/" + UUID.randomUUID().toString());
        imageReference.putFile(_imagePath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
                {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                    {
                        imageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
                        {
                            @Override
                            public void onSuccess(Uri uri)
                            {
                                _firebaseImageUrlString = uri.toString();
                                uploadImageToFirebase(_imageSize);
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Toast.makeText(getActivity(), "Error uploading image", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void uploadImageToFirebase(int imageSize)
    {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Uploading...");
        progressDialog.show();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> image = new HashMap<>();
        image.put("propertyID", _propertyID);
        image.put("imageURL", _firebaseImageUrlString);
        image.put("date", getDate());
        image.put("is360", false);

        if(imageSize == 57802752)
        {
            image.put("is360", true);
        }

        db.collection("Projects").document(_propertyID).collection("Rooms").document(_roomID).collection("Images")
                .add(image)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>()
                {
                    @Override
                    public void onSuccess(DocumentReference documentReference)
                    {
                        progressDialog.dismiss();
                        reloadFragment();
                        Toast.makeText(getActivity(), "Image uploaded", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Error uploading image", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void uploadRoomToFirebase(String roomName)
    {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Uploading...");
        progressDialog.show();

        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> room = new HashMap<>();
        room.put("name", roomName);
        //room.put("roomID", _firebaseImageUrlString);

        db.collection("Projects").document(_propertyID).collection("Rooms")
                .add(room)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>()
                {
                    @Override
                    public void onSuccess(DocumentReference documentReference)
                    {
                        String id = documentReference.getId();

                        db.collection("Projects").document(_propertyID).collection("Rooms").document(id)
                        .update("roomID", id)
                                .addOnSuccessListener(new OnSuccessListener<Void>()
                                {
                                    @Override
                                    public void onSuccess(Void aVoid)
                                    {
                                        progressDialog.dismiss();
                                        reloadFragment();
                                        Toast.makeText(getActivity(), "Room uploaded", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Error uploading room", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private String getDate()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        return sdf.format(new Date());
    }

    private void reloadFragment()
    {
        FragmentManager fragMan = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        FragmentTransaction fragTrans = fragMan.beginTransaction();
        Bundle args = new Bundle();
        args.putString(Property.PROPERTY_ID, _propertyID);
        ProgressGallery pg = new ProgressGallery();
        pg.setArguments(args);
        fragTrans.replace(R.id.frame_property_utility, pg);
        fragTrans.commit();
    }

    public void addButtonClicked()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add room");

        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                _inputText = input.getText().toString();
                uploadRoomToFirebase(_inputText);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.cancel();
            }
        });
        builder.show();
    }
}