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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class assgn_form extends AppCompatActivity {
    EditText fuiteass_name;
    EditText fuiteass_Code_client;
    EditText fuiteass_Num_Tele;
    EditText fuiteass_Adr;
    EditText fuiteass_Détail;
    ImageView img;

    RadioButton rb;
    RadioGroup radioButtonGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assgn_form);
        fuiteass_name=findViewById(R.id.fuiteass_name);
        fuiteass_Code_client=findViewById(R.id.fuiteass_Code_client);
        fuiteass_Num_Tele=findViewById(R.id.fuiteass_Num_Tele);
        fuiteass_Adr=findViewById(R.id.fuiteass_Adr);
        fuiteass_Détail=findViewById(R.id.fuiteass_Détail);
        radioButtonGroup=findViewById(R.id.grp);
        img=(ImageView) findViewById(R.id.imageView2);
        int radioButtonID = radioButtonGroup.getCheckedRadioButtonId();
        rb = radioButtonGroup.findViewById(radioButtonID);

        Toast.makeText(this, rb.getText(), Toast.LENGTH_SHORT).show();
        ///////////////////////////////////////////////////
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    public void take_picClick(View view) {
        Intent i= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap b=(Bitmap) data.getExtras().get("data");
        img.setImageBitmap(b);
    }
}

