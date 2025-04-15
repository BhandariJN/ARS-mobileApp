package com.jn.airlinemobileapp.validation;

import android.util.Patterns;
import android.widget.EditText;

public class InputValidation {
    public boolean validateInputs(EditText input) {
        if (input.getText().toString().isEmpty()) {
            input.setError("Please Fill up!");
            input.setFocusable(true);

        } else {
            return true;
        }
        return false;

    }

    public boolean validateEmail(EditText email) {
        if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            email.setError("Invalid Email");
            email.setFocusable(true);

        } else {
            return true;
        }
        return false;
    }

    public boolean validatePassword(EditText input, EditText reInput) {

        if (!input.getText().toString().equals(reInput.getText().toString())) {
            reInput.setError("Password Doesn't Match");
            reInput.setFocusable(true);
        } else {
            return true;
        }
        return false;
    }
}
