package com.example.iloveallah.moviesapp1;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
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

public class ReviewsFragment extends Fragment {
    ListView list;
    ArrayList<String>authors,arr_reviews;
    String id="";

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("autots",authors);
        outState.putStringArrayList("arr_reviews",arr_reviews);

    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_reviews_fragment,container,false);
        list=(ListView)view.findViewById(R.id.list_reviews);
        //id=getActivity().getIntent().getExtras().getString("id");
       // Toast.makeText(getActivity(), id, Toast.LENGTH_SHORT).show();
        arr_reviews=new ArrayList<String>();
        authors=new ArrayList<String>();
        if(savedInstanceState != null&&savedInstanceState.containsKey("authors")&&savedInstanceState.containsKey("arr_reviews")) {
            authors=savedInstanceState.getStringArrayList("authors");
            arr_reviews=savedInstanceState.getStringArrayList("arr_reviews");
            ReviewsAdapter adapter=new ReviewsAdapter(authors,arr_reviews,getActivity());
            list.setAdapter(adapter);
        }
        else {
            fetch_reviews reviews = new fetch_reviews();
            reviews.execute();
        }
        return view;
    }



    class fetch_reviews extends AsyncTask<Void,Void,String> {

        @Override
        protected String doInBackground(Void... voids) {
            String line = "", result = "";

            try {
                id=getActivity().getIntent().getExtras().getString("id");
                URL url=new URL("http://api.themoviedb.org/3/movie/"+id+"/Reviews?api_key=8eb8717d379460a259bc7fc18f41d191");
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

                for(int i=0;i<arr_results.length();i++){
                    JSONObject data_into_array=arr_results.getJSONObject(i);
                    String review=data_into_array.getString("content");

                    arr_reviews.add(review);
                    String author=data_into_array.getString("author");
                    authors.add(author);
                }
                  if(arr_reviews.size()==0)
                      Toast.makeText(getContext(), "There are no Reviews", Toast.LENGTH_SHORT).show();
                ReviewsAdapter adapter=new ReviewsAdapter(authors,arr_reviews,getActivity());
                list.setAdapter(adapter);

            }

            catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
