package com.jn.airlinemobileapp.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.jn.airlinemobileapp.R;

import java.util.List;

public class UserlistActivity extends AppCompatActivity {

    LinearLayout container;

    DatabaseHelper helper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);
        container = findViewById(R.id.container);

        helper = new DatabaseHelper(this);

        displayUserList();

    }

    public void displayUserList() {
        List<User> users = helper.getAllUsers();
        if (!users.isEmpty()) {
            for (User user : users) {
                RelativeLayout userView = new RelativeLayout(this);
                TextView name = new TextView(this);
                name.setText(user.getFirstName() + " " + user.getLastName());
                userView.addView(name);
                TextView email = new TextView(this);
                email.setText(user.getEmail());
                userView.addView(email);
                TextView phone = new TextView(this);
                phone.setText(user.getPhoneNumber());
                userView.addView(phone);
                TextView role = new TextView(this);
                role.setText(user.getRole());
                userView.addView(role);
                TextView delete = new TextView(this);
                delete.setText("Delete");
                delete.setOnClickListener(v -> {
                    showAlertDialog(String.valueOf(user.getId()));
                });

                ImageView image = new ImageView(this);
                image.setImageBitmap(RegisterActivity.getBitmap( user.getAvatar()));

                container.addView(userView);

            }

        } else {
            Toast.makeText(this, "No users found", Toast.LENGTH_SHORT).show();
        }
    }

    public void showAlertDialog(String id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete User!");
        builder.setMessage("Are you sure?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                helper.deleteUser(id);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(UserlistActivity.this, "Cancel click garyo", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    @SuppressLint("SetTextI18n")
    private RelativeLayout getRelativeLayout(User user) {
        RelativeLayout userView = new RelativeLayout(this);
        TextView name = new TextView(this);
        name.setText(user.getFirstName() + " " + user.getLastName());
        userView.addView(name);
        TextView email = new TextView(this);
        email.setText(user.getEmail());
        userView.addView(email);
        TextView phone = new TextView(this);
        phone.setText(user.getPhoneNumber());
        userView.addView(phone);
        TextView role = new TextView(this);
        role.setText(user.getRole());
        userView.addView(role);
        return userView;
    }
}
