package com.example.ryad.seaal_signalisation_fuite;

/**
 * Created by Ryad on 08/09/2018.
 */

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;


public class form extends Fragment {
    ImageView img;
    Button bsend, buttonImg;
    static Bitmap b = null;
    EditText details;

    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    byte[] bArray;

    private static String urlfuite = "http://" + utilities.ipServeur + "/seaal_app/post_fuite.php";
    private static String urlassgn = "http://" + utilities.ipServeur + "/seaal_app/post_assgn.php";
    private ProgressDialog pDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.form, container, false);
        img = rootView.findViewById(R.id.imageView2);
        bsend = rootView.findViewById(R.id.fab);
        details = rootView.findViewById(R.id.detailsf);

        //
        bsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(tabActivity.infoPersoRemplies & tabActivity.infoGnrlRemplies))
                {// revenir a la partie du formulaire non remplie
                    TabLayout host = (TabLayout) getActivity().findViewById(R.id.tabs);
                    TabLayout.Tab tab;
                    if (!tabActivity.infoPersoRemplies)
                    {// si informations personnele non remplies
                        Snackbar.make(rootView, "Remplir le formulaire avant! et clickez SUIVANT", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                        tab = host.getTabAt(0);
                        tab.select();
                        return;
                    } else if (!tabActivity.infoGnrlRemplies)
                    {// si informations generale de la fuite  non remplies
                        Snackbar.make(rootView, "Remplir le formulaire avant! et clickez SUIVANT", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                        tab = host.getTabAt(1);
                        tab.select();
                        return;

                    }
                }
                tabActivity.infoPersoRemplies = false;
                tabActivity.infoGnrlRemplies = false;
                tabActivity.s += "\n\n plus de détails sur la fuite: " + details.getText().toString();

                tabActivity.detail = details.getText().toString();
                /*----------------------- Pour l'envoi du mail...
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("message/rfc822");
                String[] TO = {utilities.mailDestination};
                emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                emailIntent.putExtra(Intent.EXTRA_CC, "");
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Détection des fuites - FUITE ASS -");
                emailIntent.putExtra(Intent.EXTRA_TEXT, tabActivity.s);
                tabActivity.s = "";
                infoperso.peuxEcrire=true;
                infogenrlfuite.peuxEcrire=true;
                infogenrlassgn.peuxEcrire=true;
                //----- bitmap --> file et ajouter la photo au mail ---------------------------------
                File mFile = savebitmp(b);
                Uri u = null;

                u = Uri.fromFile(mFile);
                emailIntent.putExtra(Intent.EXTRA_STREAM, u);
                emailIntent.setType("image/png");

                try {
                    startActivity(Intent.createChooser(emailIntent, "envoyer mail..."));

                } catch (ActivityNotFoundException ex) {

                }
                */
                new envoiInfo().execute();
            }

        });
        buttonImg = rootView.findViewById(R.id.fb_pic);
        buttonImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i, 0);

            }
        });
        return rootView;
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            b = (Bitmap) data.getExtras().get("data");

            img.setImageBitmap(b);
            img.setMinimumHeight(200);
            img.setMaxWidth(200);
            img.setVisibility(View.VISIBLE);
        } catch (NullPointerException e) {
        }
    }

    /* ---------------- enregister le resultat retourné par l'emailintent (bitmap --> file)  ----------------------------
    private File savebitmp(Bitmap bmp) {
        String esd = Environment.getExternalStorageDirectory().toString();
        OutputStream outStream = null;
        File f = new File(esd, b + ".png");
        if (f.exists()) {
            f.delete();
            f = new File(esd, b + ".png");
        }
        try {

            outStream = new FileOutputStream(f);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, outStream);

            outStream.flush();
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            if (outStream == null) Log.e("catch", "msg");
            return null;
        }
        return f;
    }*/


    class envoiInfo extends AsyncTask<String, String, String> {

        String reponseRequet = new String("");
        String temp = new String("");

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("envoi des informations...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            //list contient les parametres a envoyer dans la requete post
            List<NameValuePair> list = new ArrayList<>();

            if (MainActivity.bool) {//fuite
                //preparation des parametres
                list.add(new BasicNameValuePair("nom", tabActivity.nom));
                list.add(new BasicNameValuePair("code", tabActivity.code));
                list.add(new BasicNameValuePair("telephone", tabActivity.telephone));
                list.add(new BasicNameValuePair("type", tabActivity.type));
                list.add(new BasicNameValuePair("presentation", tabActivity.presentation));
                list.add(new BasicNameValuePair("adresse", tabActivity.adresse));
                list.add(new BasicNameValuePair("detail", tabActivity.detail));
                list.add(new BasicNameValuePair("status", "0"));
                if (!(tabActivity.lattitude.equals("") || tabActivity.longitude.equals(""))) {
                    Log.e("longitude", tabActivity.longitude);
                    Log.e("latitude", tabActivity.lattitude);
                    list.add(new BasicNameValuePair("longitude", tabActivity.longitude));
                    list.add(new BasicNameValuePair("latitude", tabActivity.lattitude));
                } else {

                    list.add(new BasicNameValuePair("longitude", "0"));
                    list.add(new BasicNameValuePair("latitude", "0"));
                }
                if (b != null) {
                    b.compress(Bitmap.CompressFormat.PNG, 100, bos);
                    bArray = bos.toByteArray();
                    temp = Base64.encodeToString(bArray, Base64.DEFAULT);
                    Log.e("tempString", temp);
                    list.add(new BasicNameValuePair("image", temp));

                } else {
                    list.add(new BasicNameValuePair("image", "null"));
                }
                reponseRequet = utilities.POST(urlfuite, list);
            } else {// assignissment
                //preparation des parametres
                list.add(new BasicNameValuePair("nom", tabActivity.nom));
                list.add(new BasicNameValuePair("code", tabActivity.code));
                list.add(new BasicNameValuePair("telephone", tabActivity.telephone));
                list.add(new BasicNameValuePair("type", tabActivity.type));
                list.add(new BasicNameValuePair("adresse", tabActivity.adresse));
                list.add(new BasicNameValuePair("detail", tabActivity.detail));
                list.add(new BasicNameValuePair("status", "0"));
                if (!(tabActivity.lattitude.equals("")) && !(tabActivity.longitude.equals(""))) {
                    Log.e("longitude", tabActivity.longitude);
                    Log.e("latitude", tabActivity.lattitude);
                    list.add(new BasicNameValuePair("longitude", tabActivity.longitude));
                    list.add(new BasicNameValuePair("latitude", tabActivity.lattitude));
                } else {

                    list.add(new BasicNameValuePair("longitude", "0"));
                    list.add(new BasicNameValuePair("latitude", "0"));
                }
                if (b != null) {
                    b.compress(Bitmap.CompressFormat.PNG, 100, bos);
                    bArray = bos.toByteArray();
                    temp = Base64.encodeToString(bArray, Base64.DEFAULT);

                    list.add(new BasicNameValuePair("image", temp));
                    Log.e("tempString", temp);

                } else {
                    list.add(new BasicNameValuePair("image", "null"));
                }
                reponseRequet = utilities.POST(urlassgn, list);
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if ((reponseRequet.contains("1")) || reponseRequet.contains("2")) {
                pDialog.setMessage("données envoyées avec succès!");
            } else if (reponseRequet.contains("Required field(s) is missing"))
                pDialog.setMessage("erreur, echec de l'envoi!");
            else if (reponseRequet.contains("Oops! An error occurred."))
                pDialog.setMessage("erreur, echec dans l'enregistrement des données");

            timeDelayRemoveDialog(5000, pDialog);
            //Intent i=new Intent(getActivity(),MainActivity.class);
            //startActivity(i);
        }
    }

    //fermer le dilog g apres un temps egale a time
    private void timeDelayRemoveDialog(long time, final Dialog g) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                g.dismiss();
            }
        }, time);
    }
}


