package com.palodurobuilders.contractorapp.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.palodurobuilders.contractorapp.R;
import com.palodurobuilders.contractorapp.interfaces.IUploadFirebaseStorageImageCallback;
import com.palodurobuilders.contractorapp.models.Message;
import com.palodurobuilders.contractorapp.models.Property;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Objects;

public class Messaging extends Fragment
{
    public static class MessageViewHolder extends RecyclerView.ViewHolder
    {
        final ImageView mIncomingMedia;
        final ImageView mOutgoingMedia;
        final TextView outgoingMessageText;
        final ConstraintLayout outgoingMessageConstraint;
        final TextView incomingMessageText;
        final ConstraintLayout incomingMessageConstraint;
        final TextView mTimeText;
        final TextView mNameText;
        final LinearLayout mNameDateLinear;

        public MessageViewHolder(View view)
        {
            super(view);
            mOutgoingMedia = view.findViewById(R.id.image_outgoing_media);
            mIncomingMedia = view.findViewById(R.id.image_incoming_media);
            outgoingMessageText = view.findViewById(R.id.textview_outgoing_message);
            outgoingMessageConstraint = view.findViewById(R.id.constraint_outgoing_message);
            incomingMessageText = view.findViewById(R.id.textview_incoming_message);
            incomingMessageConstraint = view.findViewById(R.id.constraint_incoming_message);
            mTimeText = view.findViewById(R.id.textview_message_time);
            mNameText = view.findViewById(R.id.textview_message_name);
            mNameDateLinear = view.findViewById(R.id.linear_message_name_time);
        }
    }

    //Constants
    public static final String MESSAGES_CHILD = "messaging/projects/";

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference mFirebaseReference;
    String mUsername;
    String _selectedPropertyID;
    Uri _imagePath;
    Bitmap _bitmap;

    ProgressBar mProgressBar;
    RecyclerView mMessageRecycler;
    LinearLayoutManager mLinearLayoutManager;
    FirebaseRecyclerAdapter<Message, MessageViewHolder> mFirebaseAdapter;
    ImageButton mSendButton;
    ImageButton mAddImageButton;
    EditText mMessageEntry;
    ImageView mPreviewMedia;
    ConstraintLayout mPreviewConstraint;
    ImageButton mCancelPreviewButton;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        _selectedPropertyID = Objects.requireNonNull(getArguments()).getString(Property.PROPERTY_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_messaging, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        mProgressBar = view.findViewById(R.id.progressbar);
        mMessageRecycler = view.findViewById(R.id.recycler_messages);
        mSendButton = view.findViewById(R.id.button_send);
        mMessageEntry = view.findViewById(R.id.edittext_message);
        mAddImageButton = view.findViewById(R.id.button_add_image);
        mPreviewMedia = view.findViewById(R.id.image_outgoing_media_preview);
        mCancelPreviewButton = view.findViewById(R.id.button_cancel_media);
        mPreviewConstraint = view.findViewById(R.id.constraint_media_preview);

        setupFirebase();

        mCancelPreviewButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                _imagePath = null;
                mPreviewConstraint.setVisibility(ConstraintLayout.GONE);
            }
        });
        mSendButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(_imagePath != null)
                {
                    uploadOutgoingMedia(new IUploadFirebaseStorageImageCallback()
                    {
                        @Override
                        public void onCallback(String firebaseUrl)
                        {
                            Message message = new Message(mMessageEntry.getText().toString(), mUsername, mUser.getEmail(), firebaseUrl);
                            mFirebaseReference.child(MESSAGES_CHILD + _selectedPropertyID).push().setValue(message);
                            mMessageEntry.setText("");
                            _imagePath = null;
                            mPreviewConstraint.setVisibility(ConstraintLayout.GONE);
                        }
                    });
                }
                else
                {
                    Message message = new Message(mMessageEntry.getText().toString(), mUsername, mUser.getEmail());
                    mFirebaseReference.child(MESSAGES_CHILD + _selectedPropertyID).push().setValue(message);
                    mMessageEntry.setText("");
                }
            }
        });

        mAddImageButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                startActivityForResult(Intent.createChooser(intent, "Select a JPEG Image"), 1);
            }
        });
    }

    private void uploadOutgoingMedia(final IUploadFirebaseStorageImageCallback callback)
    {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Uploading...");
        progressDialog.show();

        final StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/" + _selectedPropertyID);
        storageReference.putFile(_imagePath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
                {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                    {
                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
                        {
                            @Override
                            public void onSuccess(Uri uri)
                            {
                                String firebaseImageUrl = uri.toString();
                                callback.onCallback(firebaseImageUrl);
                                progressDialog.dismiss();
                            }
                        });
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK)
        {
            if(requestCode == 1)
            {
                _imagePath = data.getData();
                setImagePreview();
            }
        }
    }

    private void setImagePreview()
    {
        InputStream inputStream;
        try
        {
            inputStream = Objects.requireNonNull(getActivity()).getContentResolver().openInputStream(_imagePath);
            _bitmap = BitmapFactory.decodeStream(inputStream);
            mPreviewMedia.setImageBitmap(_bitmap);
            mPreviewConstraint.setVisibility(ConstraintLayout.VISIBLE);
        }
        catch(FileNotFoundException e)
        {
            Toast.makeText(getActivity(), "An error occurred.", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupFirebase()
    {
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        if (mUser == null)
        {
            Toast.makeText(getContext(), "Error authenticating user", Toast.LENGTH_SHORT).show();
            //push to login
        } else
        {
            mUsername = mUser.getDisplayName();
        }
        mFirebaseReference = FirebaseDatabase.getInstance().getReference();

        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mLinearLayoutManager.setStackFromEnd(true);
        mMessageRecycler.setLayoutManager(mLinearLayoutManager);

        SnapshotParser<Message> parser = new SnapshotParser<Message>()
        {
            @NonNull
            @Override
            public Message parseSnapshot(DataSnapshot dataSnapshot)
            {
                Message message = dataSnapshot.getValue(Message.class);
                if(message != null)
                {
                    message.setId(dataSnapshot.getKey());
                }
                return Objects.requireNonNull(message);
            }
        };

        DatabaseReference messagesReference = mFirebaseReference.child(MESSAGES_CHILD + _selectedPropertyID);
        FirebaseRecyclerOptions<Message> options = new FirebaseRecyclerOptions.Builder<Message>()
                .setQuery(messagesReference, parser)
                .build();

        mFirebaseAdapter = new FirebaseRecyclerAdapter<Message, MessageViewHolder>(options)
        {
            @Override
            protected void onBindViewHolder(@NonNull MessageViewHolder holder, int position, @NonNull Message message)
            {
                mProgressBar.setVisibility(ProgressBar.INVISIBLE);
                if(message.getText() != null)
                {
                    if(message.getSenderID().equals(mUser.getEmail()))
                    {
                        if(message.getMediaURL() != null)
                        {
                            Glide.with(Objects.requireNonNull(getContext()))
                                    .load(message.getMediaURL())
                                    .into(holder.mOutgoingMedia);
                        }
                        holder.mNameDateLinear.setVisibility(LinearLayout.GONE);
                        holder.incomingMessageConstraint.setVisibility(ConstraintLayout.INVISIBLE);
                        holder.outgoingMessageConstraint.setVisibility(ConstraintLayout.VISIBLE);
                        holder.outgoingMessageText.setText(message.getText());
                    }
                    else
                    {
                        if(message.getMediaURL() != null)
                        {
                            Glide.with(Objects.requireNonNull(getContext()))
                                    .load(message.getMediaURL())
                                    .into(holder.mIncomingMedia);
                        }
                        //set name and time
                        holder.mNameDateLinear.setVisibility(LinearLayout.VISIBLE);
                        holder.mNameText.setVisibility(TextView.VISIBLE);
                        holder.mNameText.setText(message.getSender());
                        holder.mTimeText.setText(message.getDate().replace('T', ' '));
                        //set correct background for message
                        holder.outgoingMessageConstraint.setVisibility(ConstraintLayout.INVISIBLE);
                        holder.incomingMessageConstraint.setVisibility(ConstraintLayout.VISIBLE);
                        holder.incomingMessageText.setText(message.getText());
                    }
                }
            }

            @NonNull
            @Override
            public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                return new MessageViewHolder(inflater.inflate(R.layout.item_message, parent, false));
            }

            @Override
            public long getItemId(int position)
            {
                return position;
            }

            @Override
            public int getItemViewType(int position)
            {
                return position;
            }
        };

        mFirebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver()
        {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount)
            {
                super.onItemRangeInserted(positionStart, itemCount);
                int messageCount = mFirebaseAdapter.getItemCount();
                int lastVisiblePosition = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();

                if(lastVisiblePosition == -1 || (positionStart >= (messageCount - 1) && lastVisiblePosition == (positionStart - 1)))
                {
                    mMessageRecycler.scrollToPosition(positionStart);
                }
            }
        });

        mMessageRecycler.setAdapter(mFirebaseAdapter);
    }

    @Override
    public void onPause()
    {
        mFirebaseAdapter.stopListening();
        super.onPause();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        mFirebaseAdapter.startListening();
    }

}