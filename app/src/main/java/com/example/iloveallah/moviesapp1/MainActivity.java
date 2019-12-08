package com.example.iloveallah.moviesapp1;

import android.content.Context;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class MainActivity extends AppCompatActivity implements Listener {

    boolean twoPane = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainFragment fragment = new MainFragment();
        fragment.setListener(this);
        getSupportFragmentManager().beginTransaction().add(R.id.fmain, fragment).commit();
        if (null != findViewById(R.id.fdetail)) {
            twoPane = true;
        }
    }

    static Intent get_intent(Context context, Movies movie) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("my object", movie);

        return new Intent(context, Info.class).putExtra("data", bundle);

       // return new Intent(context, Info.class).putExtra("data", bundle);
    }

    @Override
    public void response(Movies move) {
        if (!twoPane) {
          startActivity(  get_intent(getApplicationContext(), move));
        } else {
            InfoFragment fragment = new InfoFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("my object", move);
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fdetail, fragment, "data").commit();
        }
    }
}
