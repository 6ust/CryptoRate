package com.example.cryptorate;

import android.os.Bundle;
import android.provider.Telephony;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ViewListContents extends AppCompatActivity {
    ArrayList<String> userList;
    ListView listView;
    Telephony.Mms.Rate user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewcontents_layout);

        userList = new ArrayList<>();

        ThreeColumn_ListAdapter adapter = new ThreeColumn_ListAdapter(this, R.layout.list_rates, userList);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }
}
