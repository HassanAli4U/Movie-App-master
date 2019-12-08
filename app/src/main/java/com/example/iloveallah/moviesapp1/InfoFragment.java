package com.example.iloveallah.moviesapp1;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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

public class InfoFragment extends Fragment   {
    ImageView imageView;
    TextView title,release_date,rate,overview;
    Button reviews,favourite;
    ListView trailers;
    String id;
    ArrayList<String> trailer_key;
    ArrayList<Integer>images;
Movies saveMove;
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(saveMove!=null)
        outState.putSerializable("saveMove",saveMove);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.activity_info_fragment, container, false);
        imageView=(ImageView)view.findViewById(R.id.imag_info);
        title=(TextView)view.findViewById(R.id.title);
        release_date=(TextView)view.findViewById(R.id.date);
        rate=(TextView)view.findViewById(R.id.rate);
        overview=(TextView)view.findViewById(R.id.overview);
        favourite=(Button)view.findViewById(R.id.favourite);
        reviews=(Button)view.findViewById(R.id.getreviews);

        trailers=(ListView)view.findViewById(R.id.list_trailers);

         final Movies move= (Movies) getArguments().getSerializable("my object");
         saveMove=move;

        if(savedInstanceState != null&&savedInstanceState.containsKey("saveMove")) {
            saveMove= (Movies) savedInstanceState.getSerializable("saveMove"); // Load saved data if any.

        }

        Picasso.with(getContext()).load(move.image).into(imageView);

        title.setText(move.original_title);
        release_date.setText(move.release_date);
        rate.setText(move.rating+"min");
        overview.setText(move.overview);
        id=move.id;
        reviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), Reviews.class);
                intent .putExtra("id",id);
                startActivity(intent);
            }
        });


        isFavourite(id);//app was crashing here i have fixed it;

        favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(favourite.getText().toString().equals("Un Favourite"))
                {

                    ContentResolver contentResolver=getActivity().getContentResolver();
                    contentResolver.delete(Contract.MovieEntry.CONTENT_URI, Database.movie_id+" = ?",new String[]{id});
                    favourite.setText("mark as favorite");


            }
              else {

                    ContentValues values = new ContentValues();
                    values.put(Database.image, move.image);
                    values.put(Database.title, move.original_title);
                    values.put(Database.date, move.release_date);
                    values.put(Database.rate, move.rating);
                    values.put(Database.overview, move.overview);
                    values.put(Database.movie_id, move.id);
                    Uri mNewUri = getActivity().getContentResolver().insert(Contract.MovieEntry.CONTENT_URI, values);
                    favourite.setText("Un Favourite");
                }
            }
        });

        fetch_trailer fetch=new fetch_trailer();
        fetch.execute();




        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    class fetch_trailer extends AsyncTask<Void,Void,String> {
        @Override
        protected String doInBackground(Void... voids) {
            String line = "", result = "";

            try {
                URL url=new URL("http://api.themoviedb.org/3/movie/"+id+"/videos?api_key=8eb8717d379460a259bc7fc18f41d191");
                URLConnection connection = url.openConnection();
                connection.connect();
                InputStream input = new BufferedInputStream(url.openStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                StringBuffer all = new StringBuffer();
                while ((line = reader.readLine()) != null) {
                    all.append(line);
                }
                result = all.toString();

                Log.d("response", result);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            JSONObject string_object= null;
            try {
                string_object = new JSONObject(result);
                JSONArray arr_results=string_object.getJSONArray("results");
                trailer_key=new ArrayList<String>();
                images=new ArrayList<Integer>();
                for(int i=0;i<arr_results.length();i++){
                    JSONObject data_into_array=arr_results.getJSONObject(i);
                    String key=data_into_array.getString("key");
                    trailer_key.add(key);
                    images.add(R.drawable.youtube);
                }

                TrailerAdapter adapter=new TrailerAdapter(images,trailer_key,getActivity());
                trailers.setAdapter(adapter);
                trailers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v="+trailer_key.get(i)));
                        startActivity(intent);
                    }
                });
            }

            catch (JSONException e) {
                e.printStackTrace();
            }

        }
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
              favourite.setText("Un Favourite");

        }

        }
    }.execute();



    }
}
