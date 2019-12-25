package com.example.iloveallah.moviesapp1.adapters;

//import android.app.Activity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//
//import com.example.iloveallah.moviesapp1.Movies;
//import com.example.iloveallah.moviesapp1.R;
//import com.squareup.picasso.Picasso;
//
//import java.util.ArrayList;
//
///**
// * Created by I Love Allah on 10/3/2016.
// */
//public class Adapter extends BaseAdapter {
//    //images must be .png
//    ArrayList<Movies>arr_moves;
//    Activity activity;
//public  Adapter(){};
//    public Adapter(ArrayList<Movies> arr_moves, Activity activity) {
//        this.arr_moves = arr_moves;
//        this.activity = activity;
//    }
//public void setItems(ArrayList<Movies> arr_moves, Activity activity)
//{
//    this.arr_moves = arr_moves;
//    this.activity = activity;
//}
//
//public ArrayList<Movies> getItems(){
//    return arr_moves;
//}
//    @Override
//    public int getCount() {
//        return arr_moves.size();
//    }
//
//    @Override
//    public Object getItem(int i) {
//        return arr_moves.get(i);
//    }
//
//    @Override
//    public long getItemId(int i) {
//        return i;
//    }
//
//    @Override
//    public View getView(int i, View view, ViewGroup viewGroup) {
//        View viewg=view;
//        holder ho;
//        if(viewg==null)
//        {
//            LayoutInflater inflater=activity.getLayoutInflater();
//            viewg=inflater.inflate(R.layout.row,null);
//            ho=new holder();
//            ho.image=(ImageView)viewg.findViewById(R.id.image);
//            viewg.setTag(ho);
//        }
//        else {
//            ho=(holder)viewg.getTag();
//        }
//       /* LayoutInflater inflater=activity.getLayoutInflater();
//        View view1=inflater.inflate(R.layout.grid_item,null);
//        ImageView image=(ImageView)view1.findViewById(R.id.image);*/
//
//        Picasso.with(activity).load(arr_moves.get(i).image).into(ho.image);
//
//        //ho.image.setImageResource(arr_moves[i].image);
//        return viewg;
//    }
//class holder{
//    ImageView image;
//}
//}

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.iloveallah.moviesapp1.Movies;
import com.example.iloveallah.moviesapp1.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by vamsi on 06-May-16 for android recyclerview and cardview tutorial
 */
public class MoviesRecyclerViewAdapter extends RecyclerView.Adapter<MoviesRecyclerViewAdapter.ViewHolder> {

    private List<Movies> moviesList;
    Activity activity;

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public MoviesRecyclerViewAdapter(List<Movies> moviesList, Activity activity) {

        this.moviesList = moviesList;
        this.activity = activity;
    }

    //Provide a reference to the views for each data item
    //Complex data items may need more than one view per item, and
    //you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder{
        //each data item is just a string in this case
        public ImageView MovieCover;
        public TextView title;
        public ViewHolder(View v) {
            super(v);
            MovieCover = (ImageView) v.findViewById(R.id.imageView);
            title=(TextView ) v.findViewById(R.id.textViewTitle);
        }
    }



    //Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        //Creating a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item,parent,false);

        //set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    //Replace the contents of a view (invoked by the layout manager
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        // - get element from arraylist at this position
        // - replace the contents of the view with that element

        Movies movie = moviesList.get(position);

        Picasso.with(activity).load(movie.image).into(holder.MovieCover);
        holder.title.setText(movie.original_title);
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}