package com.example.subrapracticalexam.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.subrapracticalexam.R;
import com.example.subrapracticalexam.fragment.AddCustomerFragment;
import com.example.subrapracticalexam.fragment.AllCustomerFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        findViewById(R.id.button_get_customer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllCustomerFragment customerFragment = new AllCustomerFragment();
                customerFragment.show(getSupportFragmentManager(), null);
            }
        });

        findViewById(R.id.button_add_customer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddCustomerFragment addCustomerFragment = new AddCustomerFragment();
                addCustomerFragment.show(getSupportFragmentManager(), null);
            }
        });
    }
}
