package com.example.iloveallah.moviesapp1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Reviews extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
        getSupportFragmentManager().beginTransaction().add(R.id.review,new ReviewsFragment()).commit();

    }
}