package com.example.iloveallah.moviesapp1.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.iloveallah.moviesapp1.R;

public class Info extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        InfoFragment info=new InfoFragment();
        info.setArguments(getIntent().getExtras().getBundle("data"));
        getSupportFragmentManager().beginTransaction().add(R.id.info,info).commit();

    }


}
