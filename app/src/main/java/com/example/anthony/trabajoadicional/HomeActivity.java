package com.example.anthony.trabajoadicional;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.anthony.trabajoadicional.Entities.Score;

public class HomeActivity extends FragmentActivity implements InfoFragment.OnFragmentInteractionListener,
        GameFragment.OnFragmentInteractionListener,
        ResumeFragment.OnFragmentInteractionListener,
        ScoreFragment.OnListFragmentInteractionListener
{

    private TextView mTextMessage;
    private String userName;
    private Long userid;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment= null;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                   fragment= new InfoFragment();break;
                case R.id.navigation_dashboard:
                    fragment= new ScoreFragment();break;
            }
            Bundle bundle= new Bundle();
            bundle.putString("userName",userName);
            bundle.putLong("userid",userid);
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Fragment fragment = new InfoFragment();
        Bundle bundle = getIntent().getExtras();
        userName= bundle.getString("userName");
        userid=bundle.getLong("userid");
        fragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,fragment).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onListFragmentInteraction(Score item) {

    }
}
