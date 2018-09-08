package com.san.heartcare;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LogicRules {

    static boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    static boolean isPasswordValid(String password) {
        return password.length() > 5;
    }

    static boolean isValidSignInForm(TextInputEditText emailView, TextInputEditText passwordView, String email, String password) {
        if (!isEmailValid(email)) {
            emailView.setError("Correo Invalido");
            return false;
        }
        if (!isPasswordValid(password)) {
            passwordView.setError("Contraseña Invalida");
            return false;
        }
        return true;
    }

    static boolean isValidSignUpForm(TextInputEditText emailView, String email, TextInputEditText passwordView, String password,
                                     TextInputEditText ageView, int age) {
        if (!isValidSignInForm(emailView, passwordView, email, password))
            return false;
        if (age < 18 || age > 130) {
            ageView.setError("Edad invalida");
            return false;
        }
        return true;
    }
}
