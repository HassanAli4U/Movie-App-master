package com.example.iloveallah.moviesapp1;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by I Love Allah on 10/3/2016.
 */
public class TrailerAdapter extends BaseAdapter {
    //images must be .png
    ArrayList<Integer>images;
    ArrayList<String>keys;
    Activity activity;

    public TrailerAdapter(ArrayList<Integer> images, ArrayList<String> keys, Activity activity) {
        this.images = images;
        this.keys = keys;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int i) {
        return images.get(i);
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
            viewg=inflater.inflate(R.layout.row,null);
            ho=new holder();
            ho.image=(ImageView)viewg.findViewById(R.id.image);
            viewg.setTag(ho);
        }
        else {
            ho=(holder)viewg.getTag();
        }
       /* LayoutInflater inflater=activity.getLayoutInflater();
        View view1=inflater.inflate(R.layout.grid_item,null);
        ImageView image=(ImageView)view1.findViewById(R.id.image);*/

        Picasso.with(activity).load(images.get(i)).into(ho.image);

        //ho.image.setImageResource(arr_moves[i].image);
        return viewg;
    }
    class holder{
        ImageView image;

    }
}
