package com.palodurobuilders.contractorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.base.FinalizableWeakReference;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
        mLoginButton = findViewById(R.id.button_login);
        mUsernameEditText = findViewById(R.id.edittext_username);
        mPasswordEditText = findViewById(R.id.edittext_password);
        mCreateAccountButton = findViewById(R.id.button_create_account);

        mLoginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mAuth.signInWithEmailAndPassword(mUsernameEditText.getText().toString(), mPasswordEditText.getText().toString())
                        .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    pushToPropertySelection();
                                } else {
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
                showCreateAccountDialog();
            }
        });
    }

    public void showCreateAccountDialog()
    {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.popup_create_account, null);
        dialogBuilder.setView(dialogView);

        final EditText emailEdittext = (EditText)dialogView.findViewById(R.id.edittext_create_account_email);
        final EditText passwordEdittext = (EditText)dialogView.findViewById(R.id.edittext_create_account_password);
        final EditText reenterPasswordEdittext = (EditText)dialogView.findViewById(R.id.edittext_create_account_reenter_password);

        dialogBuilder.setTitle(R.string.create_an_account);
        dialogBuilder.setPositiveButton(R.string.create_account, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                if(!passwordEdittext.getText().toString().equals(reenterPasswordEdittext.getText().toString()))
                {
                    Toast.makeText(Login.this, "The passwords you provided do not match. Please try again.",
                            Toast.LENGTH_SHORT).show();
                }
                else
                {
                    createUserProfile(emailEdittext.getText().toString(), passwordEdittext.getText().toString());
                }
            }
        });
        dialogBuilder.setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                dialogInterface.cancel();
            }
        });
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }

    private void createUserProfile(String email, String password)
    {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
        {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if(task.isSuccessful())
                {
                    //FirebaseUser user = mAuth.getCurrentUser();
                    Toast.makeText(Login.this, "User creation successful.",
                            Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(Login.this, "User creation failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void pushToPropertySelection()
    {
        Intent propertyUtilitiesIntent = new Intent(getApplicationContext(), PropertySelection.class);
        startActivity(propertyUtilitiesIntent);
    }
}