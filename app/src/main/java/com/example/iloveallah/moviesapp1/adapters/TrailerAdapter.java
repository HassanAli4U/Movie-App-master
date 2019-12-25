package com.example.iloveallah.moviesapp1.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.iloveallah.moviesapp1.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by I Love Allah on 10/3/2016.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder> {

    ArrayList<Integer>images;
    ArrayList<String>keys,traillersTitles;
    Activity activity;

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public TrailerAdapter(ArrayList<Integer> images, ArrayList<String> keys, ArrayList<String> traillersTitles, Activity activity) {
        this.images = images;
        this.keys = keys;
        this.traillersTitles = traillersTitles;
        this.activity = activity;
    }



    //Provide a reference to the views for each data item
    //Complex data items may need more than one view per item, and
    //you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder{
        //each data item is just a string in this case
        public ImageView traillerImage;
        public TextView traillerTitle;
        public ViewHolder(View v) {
            super(v);
            traillerImage = (ImageView) v.findViewById(R.id.image);
            traillerTitle=(TextView ) v.findViewById(R.id.traillerNumber);
        }
    }



    //Create new views (invoked by the layout manager)
    @Override
    public TrailerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //Creating a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailller_row,parent,false);

        //set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    //Replace the contents of a view (invoked by the layout manager
    @Override
    public void onBindViewHolder(TrailerAdapter.ViewHolder holder, int position) {

        // - get element from arraylist at this position
        // - replace the contents of the view with that element


        Picasso.with(activity).load(images.get(position)).into(holder.traillerImage);
        holder.traillerTitle.setText(traillersTitles.get(position));
    }

    @Override
    public int getItemCount() {
        return images.size();
    }
}