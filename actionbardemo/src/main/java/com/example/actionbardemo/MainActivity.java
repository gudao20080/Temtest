package com.example.actionbardemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        Log.d("way", "actionBar" + actionBar);
        if (null != actionBar) {
            actionBar.setLogo(R.drawable.ic_launcher);
            actionBar.setTitle("aa");
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDefaultDisplayHomeAsUpEnabled(true);
//            actionBar.setHomeAsUpIndicator(android.R.drawable.arrow_down_float);
            actionBar.setHomeAsUpIndicator(R.drawable.arrow_left);
        }
//        EditText
//
//        actionBar.hide();
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        toolbar.setVisibility(View.VISIBLE);
//        toolbar.setLogo(R.mipmap.ic_launcher);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            }
        });
         findViewById(R.id.btn_replaceLayout).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 changLayout();
             }
         });
    }

    public void changLayout() {
//        getFragmentManager().beginTransaction().replace(R.id.content, new BlankFragment()).commit();
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);

    }

//    @Nullable
//    @Override
//    public Intent getSupportParentActivityIntent() {
//        return new Intent(this, Main2Activity.class);
//    }
//
//    @Override
//    public void onCreateSupportNavigateUpTaskStack(TaskStackBuilder builder) {
//        Intent intent = new Intent(this, Main2Activity.class);
//        builder.addNextIntent(intent);
//        super.onCreateSupportNavigateUpTaskStack(builder);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        if (id == android.R.id.home) {
            Intent intent = new Intent(this, Main2Activity.class);
//            if (NavUtils.shouldUpRecreateTask(this, intent)) {
//                TaskStackBuilder.create(this).addNextIntentWithParentStack(intent).startActivities();
//            } else {
//
//            }
            NavUtils.navigateUpTo(this, intent );
        }

        return super.onOptionsItemSelected(item);
    }
}
