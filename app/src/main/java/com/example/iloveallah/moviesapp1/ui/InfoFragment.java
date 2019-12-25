package com.example.iloveallah.moviesapp1.ui;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iloveallah.moviesapp1.ContentProvider.Contract;
import com.example.iloveallah.moviesapp1.ContentProvider.Database;
import com.example.iloveallah.moviesapp1.Movies;
import com.example.iloveallah.moviesapp1.R;
import com.example.iloveallah.moviesapp1.RecyclerItemClickListener;
import com.example.iloveallah.moviesapp1.adapters.TrailerAdapter;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import ru.dimorinny.floatingtextbutton.FloatingTextButton;

public class InfoFragment extends Fragment   {
    ImageView imageView;
    TextView title,release_date,overview;
    RatingBar simpleRatingBar;
    FloatingTextButton reviews,traillers;
    LikeButton favourite;
    String id;

    Movies savemovie;
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(savemovie!=null)
        outState.putSerializable("savemovie",savemovie);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.activity_info_fragment, container, false);
        imageView=(ImageView)view.findViewById(R.id.imag_info);
        title=(TextView)view.findViewById(R.id.title);
        release_date=(TextView)view.findViewById(R.id.date);
        overview=(TextView)view.findViewById(R.id.overview);

         simpleRatingBar = (RatingBar)view.findViewById(R.id.simpleRatingBar); // initiate a rating bar
         favourite=(LikeButton)view.findViewById(R.id.favourite);
        reviews=(FloatingTextButton)view.findViewById(R.id.getreviews);
        traillers=(FloatingTextButton)view.findViewById(R.id.getTraillers);


         final Movies movie= (Movies) getArguments().getSerializable("my object");
         savemovie=movie;

        if(savedInstanceState != null&&savedInstanceState.containsKey("savemovie")) {
            savemovie= (Movies) savedInstanceState.getSerializable("savemovie"); // Load saved data if any.

        }

        Picasso.with(getContext()).load(movie.image).into(imageView);

        title.setText(movie.original_title);
        release_date.setText(movie.release_date);
        simpleRatingBar.setRating((Float.parseFloat(movie.rating))/2); // set default rating
        overview.setText(movie.overview);
        id=movie.id;
        
        reviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), Reviews.class);
                intent .putExtra("id",id);
                startActivity(intent);
            }
        });

        traillers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), TraillersActivity.class);
                intent .putExtra("id",id);
                startActivity(intent);
            }
        });

        isFavourite(id);//app was crashing here i have fixed it;












        favourite.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                ContentValues values = new ContentValues();
                values.put(Database.image, movie.image);
                values.put(Database.title, movie.original_title);
                values.put(Database.date, movie.release_date);
                values.put(Database.rate, movie.rating);
                values.put(Database.overview, movie.overview);
                values.put(Database.movie_id, movie.id);
                Uri mNewUri = getActivity().getContentResolver().insert(Contract.MovieEntry.CONTENT_URI, values);

            }

            @Override
            public void unLiked(LikeButton likeButton) {

                ContentResolver contentResolver=getActivity().getContentResolver();
                contentResolver.delete(Contract.MovieEntry.CONTENT_URI, Database.movie_id+" = ?",new String[]{id});

            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }


public void isFavourite( String Id){

    new AsyncTask<Void,Void,Cursor>() {
        @Override
        protected Cursor doInBackground(Void... voids) {
            Log.d("asd","in");

            Cursor cursor2 = getContext().getContentResolver().query(
                    Contract.MovieEntry.CONTENT_URI,
                    null,
                    "movie_id= ?",
                    new String[]{id},
                    null
            );
            return cursor2;
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
          if (cursor.getCount()!=0){
              Log.d("co", ""+cursor.getCount());
              favourite.setLiked(true);

        }

        }
    }.execute();



    }
}
