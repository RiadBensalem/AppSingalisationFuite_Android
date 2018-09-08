package com.example.ryad.ggg;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

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

        return super.onOptionsItemSelected(item);
    }

    public void assgnClick(View view) {
        Intent intent=new Intent(this,assgn_form.class);
        startActivity(intent);

    }

    public void fuiteClick(View view) {
        Intent intent=new Intent(this,fuite_form.class);
        startActivity(intent);
    }

    public static void sendClick(View view) {
        /*int rbId = rg1.getCheckedRadioButtonId();
        int rbId2=rg2.getCheckedRadioButtonId();
        rb=(RadioButton) findViewById(rbId);
        if (rbId>=0) Toast.makeText(this, rb.getText(), Toast.LENGTH_SHORT).show();
        tv.setText(rb.getText());*/
        Snackbar.make(view, "sent!", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();


    }




}
