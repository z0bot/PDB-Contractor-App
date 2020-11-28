package com.palodurobuilders.contractorapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.api.Distribution;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.palodurobuilders.contractorapp.R;
import com.palodurobuilders.contractorapp.models.Message;
import com.palodurobuilders.contractorapp.models.Property;

import org.w3c.dom.Text;

public class Messaging extends Fragment
{
    public static class MessageViewHolder extends RecyclerView.ViewHolder
    {
        TextView outgoingMessageText;
        ConstraintLayout outgoingMessageConstraint;
        TextView incomingMessageText;
        ConstraintLayout incomingMessageConstraint;
        TextView mTimeText;
        TextView mNameText;
        LinearLayout mNameDateLinear;

        public MessageViewHolder(View view)
        {
            super(view);
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

    ProgressBar mProgressBar;
    RecyclerView mMessageRecycler;
    LinearLayoutManager mLinearLayoutManager;
    FirebaseRecyclerAdapter<Message, MessageViewHolder> mFirebaseAdapter;
    ImageButton mSendButton;
    EditText mMessageEntry;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        _selectedPropertyID = getArguments().getString(Property.PROPERTY_ID);
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

        setupFirebase();

        mSendButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Message message = new Message(mMessageEntry.getText().toString(), mUsername, mUser.getEmail());
                mFirebaseReference.child(MESSAGES_CHILD + _selectedPropertyID).push().setValue(message);
                mMessageEntry.setText("");
            }
        });
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
                return message;
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
                        holder.mNameDateLinear.setVisibility(LinearLayout.GONE);
                        holder.incomingMessageConstraint.setVisibility(ConstraintLayout.INVISIBLE);
                        holder.outgoingMessageConstraint.setVisibility(ConstraintLayout.VISIBLE);
                        holder.outgoingMessageText.setText(message.getText());
                    }
                    else
                    {
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
    public void onStart()
    {
        super.onStart();
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

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }
}