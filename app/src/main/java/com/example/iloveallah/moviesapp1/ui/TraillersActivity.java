package com.example.iloveallah.moviesapp1.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.iloveallah.moviesapp1.R;
import com.example.iloveallah.moviesapp1.RecyclerItemClickListener;
import com.example.iloveallah.moviesapp1.adapters.TrailerAdapter;

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

public class TraillersActivity extends AppCompatActivity {
    RecyclerView trailers;
    String id;
    ArrayList<String> trailer_key,traillerTitles;
    ArrayList<Integer>images;
    TrailerAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traillers);
id=getIntent().getExtras().getString("id");
        trailers=(RecyclerView)findViewById(R.id.list_trailers);
        trailer_key=new ArrayList<String>();
        traillerTitles=new ArrayList<String>();
        images=new ArrayList<Integer>();

        mLayoutManager = new GridLayoutManager(this,1);

        /*
        use this in case of Staggered GridLayoutManager
         */
//        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);


        trailers.setLayoutManager(mLayoutManager);


        trailers.addOnItemTouchListener(new RecyclerItemClickListener(TraillersActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v="+trailer_key.get(position)));
                startActivity(intent);
            }
        }));

        fetch_trailer fetch=new fetch_trailer();
        fetch.execute();




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
                traillerTitles=new ArrayList<String>();
                images=new ArrayList<Integer>();
                for(int i=0;i<arr_results.length();i++){
                    JSONObject data_into_array=arr_results.getJSONObject(i);
                    String key=data_into_array.getString("key");
                    String name=data_into_array.getString("name");

                    trailer_key.add(key);
                    traillerTitles.add(name);
                    images.add(R.drawable.youtube);
                }


                adapter=new TrailerAdapter(images,trailer_key,traillerTitles,TraillersActivity.this);
                trailers.setAdapter(adapter);
            }

            catch (JSONException e) {
                e.printStackTrace();
            }
//                trailers.setNestedScrollingEnabled(true);
adapter.notifyDataSetChanged();

        }
    }

}
