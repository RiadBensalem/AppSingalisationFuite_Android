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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class fuite_form extends AppCompatActivity {

    RadioGroup rg1,rg2;
    RadioButton rb;
    TextView tv;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuite_form);
        rg1 = (RadioGroup) findViewById(R.id.rg1);
        rg2 = (RadioGroup) findViewById(R.id.rg2);
        img=(ImageView) findViewById(R.id.imageView2);
    tv=(TextView)findViewById(R.id.name);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendClick(view);
            }
        });
        ////////////////////////// floating button to take pic //////////////////////////////////////////////////////
        FloatingActionButton fb_pic = (FloatingActionButton) findViewById(R.id.fb_pic);
        fb_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i,0);

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    // this methode collect the infos from the form and process them for sending
     public void sendClick(View view) {
        /*int rbId = rg1.getCheckedRadioButtonId();
        int rbId2=rg2.getCheckedRadioButtonId();
        rb=(RadioButton) findViewById(rbId);
        if (rbId>=0) Toast.makeText(this, rb.getText(), Toast.LENGTH_SHORT).show();
        tv.setText(rb.getText());*/
        MainActivity.sendClick(view);
         //Snackbar.make(view, "sent!", Snackbar.LENGTH_LONG).setAction("Action", null).show();


    }
    public void take_picClick(View view) {
        Intent i= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i,0);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap b=(Bitmap) data.getExtras().get("data");
        if (b!=null) img.setImageBitmap(b);
        img.setVisibility(1);
    }
}
