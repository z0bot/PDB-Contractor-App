package com.palodurobuilders.contractorapp.pages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.palodurobuilders.contractorapp.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CreateAccount extends AppCompatActivity
{
    Toolbar mToolbar;
    FloatingActionButton mCreateAccountFAB;
    EditText mEmailEntry;
    EditText mFirstNameEntry;
    EditText mLastNameEntry;
    EditText mPhoneEntry;
    EditText mPasswordEntry;
    EditText mPasswordConfirmEntry;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        mAuth = FirebaseAuth.getInstance();

        mToolbar = findViewById(R.id.toolbar);

        setStatusBarColor();
        setTitleBar();
        findViews();

        mCreateAccountFAB.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                createUserAccount();
            }
        });
    }

    private void createUserAccount()
    {
        if(mEmailEntry.getText().toString().isEmpty()||mPasswordEntry.getText().toString().isEmpty()||mPasswordConfirmEntry.getText().toString().isEmpty()||mPhoneEntry.getText().toString().isEmpty()||mFirstNameEntry.getText().toString().isEmpty()||mLastNameEntry.getText().toString().isEmpty())
        {
            Toast.makeText(CreateAccount.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!mPasswordEntry.getText().toString().equals(mPasswordConfirmEntry.getText().toString()))
        {
            Toast.makeText(CreateAccount.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.createUserWithEmailAndPassword(mEmailEntry.getText().toString(), mPasswordEntry.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
        {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if(task.isSuccessful())
                {
                    createUserInFirestore();
                    setDisplayName();
                }
                else
                {
                    Toast.makeText(CreateAccount.this, "Account creation failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setDisplayName()
    {
        mAuth.signInWithEmailAndPassword(mEmailEntry.getText().toString(), mPasswordEntry.getText().toString())
                .addOnCompleteListener(CreateAccount.this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(mFirstNameEntry.getText().toString()).build();
                            Objects.requireNonNull(user).updateProfile(profileUpdates);
                        } else
                        {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(CreateAccount.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void createUserInFirestore()
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> user = new HashMap<>();
        user.put("users_firstname", mFirstNameEntry.getText().toString());
        user.put("users_lastname", mLastNameEntry.getText().toString());
        user.put("users_type", true);
        user.put("users_phone", mPhoneEntry.getText().toString());

        db.collection("Contractors").document(Objects.requireNonNull(mAuth.getUid()))
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>()
                {
                    @Override
                    public void onSuccess(Void aVoid)
                    {
                        Toast.makeText(CreateAccount.this, "User created successfully", Toast.LENGTH_SHORT).show();
                        closeActivity();
                    }
                })
                .addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Toast.makeText(CreateAccount.this, "User creation failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void closeActivity()
    {
        this.finish();
    }

    private void findViews()
    {
        mCreateAccountFAB = findViewById(R.id.fab_create_account);
        mEmailEntry = findViewById(R.id.edittext_create_account_email);
        mFirstNameEntry = findViewById(R.id.edittext_first_name);
        mLastNameEntry = findViewById(R.id.edittext_last_name);
        mPhoneEntry = findViewById(R.id.edittext_phone);
        mPasswordEntry = findViewById(R.id.edittext_create_account_password);
        mPasswordConfirmEntry = findViewById(R.id.edittext_create_account_reenter_password);
    }

    public void setStatusBarColor()
    {
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#FFFFFF"));
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    @Override
    public boolean onSupportNavigateUp()
    {
        onBackPressed();
        return true;
    }

    private void setTitleBar()
    {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}