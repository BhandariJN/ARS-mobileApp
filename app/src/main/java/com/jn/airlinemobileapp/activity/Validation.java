package com.jn.airlinemobileapp.activity;

import android.widget.EditText;

public class Validation {
    public boolean inputValidation(EditText field) {
        if (field.getText().toString().trim().isEmpty()) {
            field.setError("Required");
            return false;
        }
        return true;
    }

    public boolean emailValidation(EditText field) {
        if (android.util.Patterns.EMAIL_ADDRESS.matcher(field.getText().toString()).matches()) {
            return true;
        }
        field.setError("Invalid Email");
        return false;
    }

    public boolean passwordValidation(EditText field) {
        if (field.getText().toString().trim().length() >= 8) {
            return true;
        }
        field.setError("Password must be at least 8 characters");
        return false;
    }

    public boolean passwordMatchValidation(EditText field1, EditText field2) {
        if (field1.getText().toString().equals(field2.getText().toString())) {
            return true;
        }
        field2.setError("Passwords do not match");
        return false;
    }



}
