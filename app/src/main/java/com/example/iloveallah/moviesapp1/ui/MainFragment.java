package com.example.iloveallah.moviesapp1.ui;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.iloveallah.moviesapp1.ContentProvider.Contract;
import com.example.iloveallah.moviesapp1.ContentProvider.Database;
import com.example.iloveallah.moviesapp1.Listener;
import com.example.iloveallah.moviesapp1.Movies;
import com.example.iloveallah.moviesapp1.NetworkConnection;
import com.example.iloveallah.moviesapp1.R;
import com.example.iloveallah.moviesapp1.RecyclerItemClickListener;
import com.example.iloveallah.moviesapp1.adapters.MoviesRecyclerViewAdapter;
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

public class MainFragment  extends Fragment     {
    URL url;
    ArrayList<Movies> arr_movies=new ArrayList<Movies>();;
    RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<String>images,origina_title,overview,rating,release_data,id;
    private Listener listener;
    void setListener( Listener listener)
    {
        this.listener=listener;
    }


    MoviesRecyclerViewAdapter adapter;
    Movies m1;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
         outState.putSerializable("myAdapter", ( arr_movies));


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.activity_main_fragment, container, false);


        getActivity().setTitle("Most Popular");



        recyclerView = (RecyclerView)view. findViewById(R.id.recyclerView);






        if (recyclerView != null) {
            recyclerView.setHasFixedSize(true);
        }

        //        //using a linear layout manager
       // mLayoutManager = new LinearLayoutManager(getContext());

       // use this in case of gridlayoutmanager


        mLayoutManager = new GridLayoutManager(getContext(),2);

        /*
        use this in case of Staggered GridLayoutManager
         */
//        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);


        recyclerView.setLayoutManager(mLayoutManager);





        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                listener.response(arr_movies.get(position));
            }
        }));


/*
        if(state != null) {
            gridView.onRestoreInstanceState(state);
        }*/
       /* Log.d("st", String.valueOf(savedInstanceState));
        if(savedInstanceState!=null)
        {
            ArrayList<Movies> movies= (ArrayList<Movies>) savedInstanceState.getSerializable("key");
            ad=new Adapter(movies,getActivity());
            gridView.setAdapter(ad);
        }
*/

        if(savedInstanceState != null&&savedInstanceState.containsKey("myAdapter")) {
            arr_movies=(ArrayList<Movies>) savedInstanceState.getSerializable("myAdapter");
            adapter=new MoviesRecyclerViewAdapter(arr_movies,getActivity());
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }



        try {
            if (NetworkConnection.getInstance(getActivity()).isOnline()){
                url = new URL("http://api.themoviedb.org/3/movie/popular?api_key=8eb8717d379460a259bc7fc18f41d191");

                data data=new data();
                data.execute();
            }
            else
                Toast.makeText(getActivity(), "NO Internet NetworkConnection", Toast.LENGTH_LONG).show();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
       // Log.d(TAG, "saving listview state @ onPause");
//        state = recyclerView.onSaveInstanceState();
    }

    @Override
    public void onStart() {
        super.onStart();
        //ad.notifyDataSetChanged();
       // gridView.setAdapter(ad);
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                listener.response(arr_movies.get(i));
//
//            }
//        });
    }

    static Intent get_intent(Context context, Movies movie){
        Bundle bundle = new Bundle();
        bundle.putSerializable("my object", movie);


        return new Intent(context, Info.class).putExtra("data", bundle);
    }



    class data extends AsyncTask<Void,Void,String> {

        @Override
        protected String doInBackground(Void... voids) {
            String line = "", result = "";

            try {

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
            try {
               // System.out.print("inner");
                JSONObject string_object=new JSONObject(result);
                JSONArray arr_results=string_object.getJSONArray("results");
                images=new ArrayList<String>();
                origina_title=new ArrayList<String>();
                overview=new ArrayList<String>();
                rating=new ArrayList<String>();
                release_data=new ArrayList<String>();
                id=new ArrayList<String>();
                for(int i=0;i<arr_results.length();i++)
                {
                    JSONObject data_into_array=arr_results.getJSONObject(i);
                    String poster_path="http://image.tmdb.org/t/p/w185/"+data_into_array.getString("poster_path");
                    images.add(poster_path);
                    String title=data_into_array.getString("original_title");
                    origina_title.add(title);
                    String view=data_into_array.getString("overview");
                    overview.add(view);
                    String vote_average=data_into_array.getString("vote_average");
                    rating.add(vote_average);
                    String rel_data=data_into_array.getString("release_date");
                    release_data.add(rel_data);
                    String id_data=data_into_array.getString("id");
                    id.add(id_data);
                }
                arr_movies=new ArrayList<Movies>();

                for(int g=0;g<images.size();g++)
                {
                    m1=new Movies();
                    m1.image=images.get(g);
                    m1.original_title=origina_title.get(g);
                    m1.overview=overview.get(g);
                    m1.rating=rating.get(g);
                    m1.release_date=release_data.get(g);
                    m1.id=id.get(g);
                    arr_movies.add(m1);
                }


                adapter=new MoviesRecyclerViewAdapter(arr_movies,getActivity());
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case R.id.most_popula: {
                arr_movies.clear();
                //
                try {
                    if (NetworkConnection.getInstance(getActivity()).isOnline()){
                        getActivity().setTitle("Most Popular");

                        url = new URL("http://api.themoviedb.org/3/movie/popular?api_key=8eb8717d379460a259bc7fc18f41d191");

                        data data=new data();
                        data.execute();
                    }
                    else
                        Toast.makeText(getContext(), "NO Internet NetworkConnection", Toast.LENGTH_LONG).show();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                return true;
            }
            case R.id.highest_rated: {
                arr_movies.clear();
                try {
                    if (NetworkConnection.getInstance(getContext()).isOnline()){
                        getActivity().setTitle("Highest Rated");

                        url = new URL("http://api.themoviedb.org/3/movie/top_rated?api_key=8eb8717d379460a259bc7fc18f41d191");
                        data data=new data();
                        data.execute();
                    }
                    else
                        Toast.makeText(getContext(), "NO Internet NetworkConnection", Toast.LENGTH_LONG).show();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                return true;
            }

            case R.id.favourite:{

                Log.d("asd","out");
                new AsyncTask<Void,Void,Cursor>() {
                    @Override
                    protected Cursor doInBackground(Void... voids) {
                        Log.d("asd","in");

                        Cursor cursor2 = getContext().getContentResolver().query(
                                Contract.MovieEntry.CONTENT_URI,
                                null,
                                null,
                                null,
                                null
                        );
                        return cursor2;
                    }

                    @Override
                    protected void onPostExecute(Cursor cursor) {
                        super.onPostExecute(cursor);
                        if(cursor.moveToFirst())
                        {
                            arr_movies.clear();
                            getActivity().setTitle("Favorites ");

                            do {
                                 m1=new Movies();
                                String title = cursor.getString(cursor.getColumnIndex(Database.title));

                                String image = cursor.getString(cursor.getColumnIndex(Database.image));
                                String overV = cursor.getString(cursor.getColumnIndex(Database.overview));

                                String rate = cursor.getString(cursor.getColumnIndex(Database.rate));

                                String date = cursor.getString(cursor.getColumnIndex(Database.date));

                                String ID = cursor.getString(cursor.getColumnIndex(Database.movie_id));
                                m1.image=image;
                                m1.original_title=title;
                                m1.overview=overV;
                                m1.rating=rate;
                                m1.release_date=date;
                                m1.id=ID;
                                arr_movies.add(m1);

                            }while (cursor.moveToNext());
                            adapter=new MoviesRecyclerViewAdapter(arr_movies,getActivity());
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                        else {
                            Toast.makeText(getContext(), "There is no favourites till now", Toast.LENGTH_SHORT).show();
                        }

                    }
                }.execute();




                return true;
            }
            default:{
                Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                return  true;}
        }
    }
    public void get_favourites(Cursor cursor) {
        if (cursor.moveToFirst()) {
            images = new ArrayList<String>();
            origina_title = new ArrayList<String>();
            overview = new ArrayList<String>();
            rating = new ArrayList<String>();
            release_data = new ArrayList<String>();
            id = new ArrayList<String>();
            do {
                String title = cursor.getString(cursor.getColumnIndex(Database.title));
                origina_title.add(title);
                Toast.makeText(getContext(), title, Toast.LENGTH_SHORT).show();
                String image = cursor.getString(cursor.getColumnIndex(Database.image));
                images.add(image);
                String overV = cursor.getString(cursor.getColumnIndex(Database.overview));
                overview.add(overV);
                String rate = cursor.getString(cursor.getColumnIndex(Database.rate));
                rating.add(rate);
                String date = cursor.getString(cursor.getColumnIndex(Database.date));
                release_data.add(date);
                String ID = cursor.getString(cursor.getColumnIndex(Database.movie_id));
                id.add(ID);
            }
            while (cursor.moveToNext());



        }
        for(int g=0;g<images.size();g++)
        {
            m1=new Movies();
            m1.image=images.get(g);
            m1.original_title=origina_title.get(g);
            m1.overview=overview.get(g);
            m1.rating=rating.get(g);
            m1.release_date=release_data.get(g);
            m1.id=id.get(g);
            arr_movies.add(m1);



        }






    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.my_menue, menu);
    }


}
