package com.machine.sqlitedemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.machine.sqlitedemo.data.DBHelper;
import com.machine.sqlitedemo.databinding.ActivityMainBinding;
import com.machine.sqlitedemo.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    RecyclerView rvContacts;
    List<Contact> allContacts = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DBHelper db = new DBHelper(MainActivity.this);

        Contact lucky = new Contact();
        lucky.setPhone("1234567890");
        lucky.setName("Lucky");

        //db.addContact(lucky);

        Contact monu = new Contact();
        monu.setPhone("0987654321");
        monu.setName("Monu");

        //db.addContact(monu);

        Contact anu = new Contact();
        anu.setPhone("1122334455");
        anu.setName("Anu");

        //db.addContact(anu);

        Contact naresh = new Contact();
        naresh.setPhone("7788994455");
        naresh.setName("Naresh");

        //db.addContact(naresh);

        allContacts = db.getAllContacts();
        for (Contact contact: allContacts){
            Log.d("dbLucky", "Id: " + contact.getId() + "\n" +
                    "Name: " + contact.getName() + "\n" +
                    "Phone Number: " + contact.getPhone() + "\n");
        }

        binding.fbAdd.setOnClickListener(view ->
        {
            startActivity(new Intent(MainActivity.this, ActivityAddOrEdit.class));
            Intent intent = new Intent(MainActivity.this, ActivityAddOrEdit.class);
            intent.putExtra("FROM", "Add");

            startActivity(intent);
        });

        ContactAdapter adapter = new ContactAdapter(allContacts, this );

        binding.rvList.setLayoutManager(new LinearLayoutManager(this));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        binding.rvList.setLayoutManager(linearLayoutManager);
        binding.rvList.setHasFixedSize(true);
        binding.rvList.setAdapter(adapter);

        adapter.notifyDataSetChanged();

    }

}