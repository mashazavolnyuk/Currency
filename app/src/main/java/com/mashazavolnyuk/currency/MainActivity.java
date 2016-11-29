package com.mashazavolnyuk.currency;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mashazavolnyuk.currency.fragments.FragmentFindByDate;
import com.mashazavolnyuk.currency.fragments.FragmentSetting;
import com.mashazavolnyuk.currency.fragments.FragmentTab;
import com.mashazavolnyuk.currency.interfaces.ICorrectDesigner;
import com.mashazavolnyuk.currency.interfaces.INavigation;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ICorrectDesigner, INavigation {
    AppBarLayout appBarLayout;
    Toolbar toolbar;
    List<View> listView = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        toMainScreen();
    }

    @Override
    public void toMainScreen() {
        FragmentTab fragmentTab = new FragmentTab();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_main, fragmentTab)
                .setTransition(android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack("tab")
                .commit();
    }

    @Override
    public void toScreenSetting() {
        FragmentSetting fragmentTab = new FragmentSetting();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_main, fragmentTab)
                .setTransition(android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack("setting")
                .commit();
        cleanChildDesigner();
    }

    @Override
    public void toFindByDate(String date) {
        FragmentFindByDate fragmentFindByDate = new FragmentFindByDate();
        fragmentFindByDate.DATA_FIND = date;
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_main, fragmentFindByDate)
                .setTransition(android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack("findByDate")
                .commit();
        cleanChildDesigner();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            toMainScreen();
        } else if (id == R.id.nav_gallery) {
            toScreenSetting();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void cleanChildDesigner() {
        appBarLayout.removeView(listView.get(0));
        toolbar.removeView(listView.get(1));
        listView.clear();
    }

    @Override
    public void addChild(View v, int parrent) {
        listView.add(v);
        if (parrent == 0)
            appBarLayout.addView(listView.get(0));
        else
            toolbar.addView(listView.get(1));
    }
}
