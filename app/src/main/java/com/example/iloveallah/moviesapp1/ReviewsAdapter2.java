package com.example.iloveallah.moviesapp1;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by I Love Allah on 11/13/2016.
 */
public class ReviewsAdapter2 extends BaseAdapter {
    ArrayList<String>authors,reviews;
    Activity activity;

    public ReviewsAdapter2(ArrayList<String> author, ArrayList<String> review, Activity activity) {
        this.authors = author;
        this.reviews = review;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return reviews.size();
    }

    @Override
    public Object getItem(int i) {
        return authors.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
            LayoutInflater inflater=activity.getLayoutInflater();
           View view1=inflater.inflate(R.layout.activity_reviews_row,null);
           TextView author=(TextView) view1.findViewById(R.id.author);
           TextView review=(TextView) view1.findViewById(R.id.review);
           author.setText(authors.get(i));
           review.setText(reviews.get(i));


        return view1;
    }

}
