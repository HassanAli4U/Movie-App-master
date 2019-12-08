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
public class ReviewsAdapter extends BaseAdapter {
    ArrayList<String>authors,reviews;
    Activity activity;

    public ReviewsAdapter(ArrayList<String> author, ArrayList<String> review, Activity activity) {
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
        View viewg=view;
        holder ho;
        if(viewg==null)
        {
            LayoutInflater inflater=activity.getLayoutInflater();
            viewg=inflater.inflate(R.layout.activity_reviews_row,null);
            ho=new holder();
            ho.author=(TextView) viewg.findViewById(R.id.author);
            ho.review=(TextView) viewg.findViewById(R.id.review);
            viewg.setTag(ho);
        }
        else {
            ho=(holder)viewg.getTag();
        }

        ho.author.setText(authors.get(i)+" :");
        ho.review.setText(reviews.get(i));
        return viewg;
    }
    class holder{
        TextView author,review;

    }
}
