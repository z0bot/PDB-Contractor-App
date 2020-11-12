package com.palodurobuilders.contractorapp.pages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.palodurobuilders.contractorapp.R;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity
{
    Button mLoginButton;
    Button mCreateAccountButton;
    EditText mUsernameEditText;
    EditText mPasswordEditText;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_login);
        setStatusBarColor();
        findViews();
        setOnClickEvents();
    }

    private void findViews()
    {
        mLoginButton = findViewById(R.id.button_login);
        mUsernameEditText = findViewById(R.id.edittext_username);
        mPasswordEditText = findViewById(R.id.edittext_password);
        mCreateAccountButton = findViewById(R.id.button_create_account);
    }

    private void setOnClickEvents()
    {
        mLoginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mAuth.signInWithEmailAndPassword(mUsernameEditText.getText().toString(), mPasswordEditText.getText().toString())
                        .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>()
                        {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task)
                            {
                                if (task.isSuccessful())
                                {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    pushToPropertySelection();
                                } else
                                {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(Login.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        mCreateAccountButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //showCreateAccountDialog();
                Intent createAccountIntent = new Intent(getApplicationContext(), CreateAccount.class);
                startActivity(createAccountIntent);
            }
        });
    }

    public void setStatusBarColor()
    {
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#FFFFFF"));
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    public void pushToPropertySelection()
    {
        mUsernameEditText.setText("");
        mPasswordEditText.setText("");
        Intent propertyUtilitiesIntent = new Intent(getApplicationContext(), PropertySelection.class);
        startActivity(propertyUtilitiesIntent);
    }
}