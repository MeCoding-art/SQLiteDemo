package com.machine.sqlitedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.machine.sqlitedemo.data.DBHelper;
import com.machine.sqlitedemo.databinding.ActivityAddOrEditBinding;
import com.machine.sqlitedemo.model.Contact;

//Databse is a structure that allows us to store data in a well-organized fashion.
public class ActivityAddOrEdit extends AppCompatActivity {

    ActivityAddOrEditBinding binding;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddOrEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String from = getIntent().getStringExtra("FROM");
        if (from.equals("edit") ){
            String name = getIntent().getStringExtra("NAME");
            String phone = getIntent().getStringExtra("PHONE");
            id = getIntent().getStringExtra("ID");
            binding.etName.setText(name);
            binding.etPhone.setText(phone);
        }

        binding.fbSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(validation()){

                if(from.equals("edit")){
                    DBHelper db = new DBHelper(ActivityAddOrEdit.this);
                    Contact contact = new Contact();
                    contact.setName(binding.etName.getText().toString());
                    contact.setPhone(binding.etPhone.getText().toString());
                    contact.setId(Integer.parseInt(id));

                    int effectedRows = db.updateContact(contact);
                    Toast.makeText(ActivityAddOrEdit.this, effectedRows+"", Toast.LENGTH_SHORT).show();
                }else{
                    DBHelper db = new DBHelper(ActivityAddOrEdit.this);
                    Contact contact = new Contact();
                    contact.setName(binding.etName.getText().toString());
                    contact.setPhone(binding.etPhone.getText().toString());
                    db.addContact(contact);
                }

                startActivity(new Intent(ActivityAddOrEdit.this, MainActivity.class));
                finish();
            }
            }
        });

        binding.btSearch.setOnClickListener(view -> {

        });
    }

    private boolean validation() {
        String name = binding.etName.getText().toString();
        String number = binding.etPhone.getText().toString();

        if (name.length() > 0  && number.length() == 10){
            return true;
        }else {
            binding.etName.setError("Please enter valid values");
            binding.etPhone.setError("Please enter valid values");
            return false;
        }
    }



}