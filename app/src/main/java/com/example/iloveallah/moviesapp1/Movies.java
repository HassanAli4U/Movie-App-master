package com.example.iloveallah.moviesapp1;

import android.widget.ImageView;

import java.io.Serializable;

/**
 * Created by I Love Allah on 10/8/2016.
 */
public class Movies implements Serializable {
   public String original_title,overview,rating,release_date,image,id;

   public Movies(String original_title, String overview, String rating, String release_date, String image, String id) {
      this.original_title = original_title;
      this.overview = overview;
      this.rating = rating;
      this.release_date = release_date;
      this.image = image;
      this.id = id;
   }

    public Movies() {

    }
}
