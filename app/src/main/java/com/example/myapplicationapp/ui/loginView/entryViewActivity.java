package com.example.myapplicationapp.ui.loginView;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplicationapp.MainActivity;
import com.example.myapplicationapp.R;
import com.example.myapplicationapp.ui.utilities.validations;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class entryViewActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    TextInputLayout emailInputApp;
    TextInputLayout passwordInputApp;
    ProgressDialog mDialog;
    validations mValidations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_view);

        mValidations = new validations(this);
        mAuth = FirebaseAuth.getInstance();

        emailInputApp = findViewById(R.id.email_input_app);
        passwordInputApp = findViewById(R.id.password_input_app);

        TextView checkInId = findViewById(R.id.check_in_id);
        checkInId.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), registrationViewActivity.class)));

        Button logInId = findViewById(R.id.log_in_id);
        logInId.setOnClickListener(v -> logIn());
    }

    private void logIn() {


        if (!mValidations.validateEmailPassword(emailInputApp) | !mValidations.validateEmailPassword(passwordInputApp)) {
            return;
        }

        mDialog = ProgressDialog.show(this, "", "Ingresando...", false);

        String email = Objects.requireNonNull(emailInputApp.getEditText()).getText().toString().trim();
        String password = Objects.requireNonNull(passwordInputApp.getEditText()).getText().toString().trim();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        mDialog.dismiss();
                        mainActivity();
                    } else {
                        mDialog.dismiss();
                        invLogin();
                    }
                });
    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            mainActivity();
        }
    }

    private void mainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void invLogin() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Error");
        builder.setMessage("Correo o contraseña invalido")
                .setPositiveButton(getString(R.string.confirm), (dialog, which) -> dialog.dismiss());
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
        Button positiveButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        positiveButton.setTextColor(Color.parseColor("#FF000000"));

    }
}