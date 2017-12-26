package com.example.hp.pms_project.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.hp.pms_project.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private Button btnRegister;
    //  private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        sessionManager = new SessionManager(MainActivity.this);
//        if (!sessionManager.isSiteSignedIn()) {
//            startActivity(new Intent(MainActivity.this, LoginActivity.class));
//            finish();
//        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        btnRegister = (Button) findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), AddTransactionActivity.class);
                startActivity(intent);

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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

        if (id == R.id.nav_reports) {

            Intent intent = new Intent(getApplicationContext(), ReportActivity.class);
            startActivity(intent);
            // Handle the camera action
        } else if (id == R.id.nav_forcast) {

            Intent intent = new Intent(getApplicationContext(), ForcastActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_budgets) {

            Intent intent = new Intent(getApplicationContext(), BudgtsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_accounts) {

            Intent intent = new Intent(getApplicationContext(), AccountsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_reminders) {

            Intent intent = new Intent(getApplicationContext(), RemindersActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_tages) {

            Intent intent = new Intent(getApplicationContext(), TagsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_groups) {

            Intent intent = new Intent(getApplicationContext(), GroupsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_contactus) {
            Intent intent = new Intent(getApplicationContext(), ContactUsAcivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_logout) {


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}