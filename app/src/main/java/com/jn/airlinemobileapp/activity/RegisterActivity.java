package com.jn.airlinemobileapp.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jn.airlinemobileapp.R;

import java.io.ByteArrayOutputStream;

public class RegisterActivity extends AppCompatActivity {
    private EditText firstName, lastName, email, password, rePassword, phoneNumber;
    private TextView loginLink;
    ImageView image;

    private Button registerButton;

    private Spinner roleSpinner;

    private Validation validation = new Validation();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        firstName = findViewById(R.id.first_name);
        lastName = findViewById(R.id.last_name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        rePassword = findViewById(R.id.re_password);
        loginLink = findViewById(R.id.login_link);
        registerButton = findViewById(R.id.register_button);
        phoneNumber = findViewById(R.id.phone_number);
        roleSpinner = findViewById(R.id.role_spinner);
        image = findViewById(R.id.image);
        ArrayAdapter<String> roles = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new String[]{"USER", "AIRPORT_ADMIN", "AIRLINE_ADMIN"});

        roleSpinner.setAdapter(roles);
        String role = roleSpinner.getSelectedItem().toString();


        registerButton.setOnClickListener(v -> {
            if (validation.inputValidation(firstName)
                    && validation.inputValidation(lastName)
                    && validation.inputValidation(email)
                    && validation.inputValidation(password)
                    && validation.emailValidation(email)
                    && validation.passwordValidation(password)
                    && validation.inputValidation(phoneNumber)
                    && validation.passwordMatchValidation(password, rePassword)) {


                try (DatabaseHelper helper = new DatabaseHelper(this)) {
                    if (helper.getUserByEmail(email.getText().toString()) != null) {
                        Toast.makeText(this, "Email already exists", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    String hashPwd = PasswordUtils.passwordEncryption(password.getText().toString());
                    ContentValues values = new ContentValues();
                    values.put("first_name", firstName.getText().toString());
                    values.put("last_name", lastName.getText().toString());
                    values.put("email", email.getText().toString());
                    values.put("password", hashPwd);
                    values.put("phone_number", phoneNumber.getText().toString());
//                    values.put("role", role);
                    if (bitmap != null)
                        values.put("avatar", getByteArray(bitmap));


                    Log.e("userinfo", "role " + role);
                    Log.e("userinfo", "password " + hashPwd);


                    helper.createUser(values);
                    Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));

                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    Toast.makeText(RegisterActivity.this, "Registration Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });

        loginLink.setOnClickListener(v -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 1072);
            }
        });


    }

    Bitmap bitmap;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1072 && resultCode == RESULT_OK) {
            bitmap = (Bitmap) data.getExtras().get("data");
            image.setImageBitmap(bitmap);
        }

    }

    public static Bitmap getBitmap(byte[] arr) {
        return BitmapFactory.decodeByteArray(arr, 0, arr.length);

    }

    public static byte[] getByteArray(Bitmap bitmap) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] barray = bos.toByteArray();
        return barray;
    }
}
