package com.example.ryad.seaal_signalisation_fuite;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.StringTokenizer;

/**
 * Created by Ryad on 08/09/2018.
 */

public class infoperso extends Fragment {
    Button buttonIexist;
    Button next;
    EditText name;
    EditText code;
    EditText phone;
    File FichierInfoDejaFournies;
    FileOutputStream outputStream;
    FileInputStream inputStream;
    String infoperso = new String("hello world");
    // pour evite de dublicer les info dans le corps du mail a cause d'une mauvaise manipulation de l'utilisateur
    static boolean peuxEcrire=true;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.infoperso, container, false);

        buttonIexist = (Button) rootView.findViewById(R.id.iexistbutton);
        next = (Button) rootView.findViewById(R.id.next);
        name = rootView.findViewById(R.id.name);
        code = rootView.findViewById(R.id.client_code);
        phone = rootView.findViewById(R.id.phone_num);

        try {
            FichierInfoDejaFournies = new File(getActivity().getFilesDir(), "fileexist");
            if (!(FichierInfoDejaFournies.length() == 0)) {
                buttonIexist.setVisibility(View.VISIBLE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        buttonIexist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    inputStream = getActivity().getApplicationContext().openFileInput("fileexist");
                    StringBuffer fileContent = new StringBuffer("");
                    int n;
                    byte[] buffer = new byte[1024];

                    while ((n = inputStream.read(buffer)) != -1) {
                        fileContent.append(new String(buffer, 0, n));
                    }

                    StringTokenizer st = new StringTokenizer(fileContent.toString(),"#");
                    name.setText(st.nextToken());
                    code.setText(st.nextToken());
                    phone.setText(st.nextToken());
                    inputStream.close();


                } catch (Exception e) {
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name.setError(null);
                code.setError(null);
                phone.setError(null);

                hideKeyboardFrom();
                if (name.getText().toString().equals("") || name.getText().toString().equals(" ") || name.getText().toString().equals("\n")) {
                    name.setError("veuillez remplir ce champ");
                    name.requestFocus();
                    return;
                }
                if (code.getText().toString().equals("") || code.getText().toString().equals(" ") || code.getText().toString().equals("\n") ) {
                    code.setError("veuillez remplir ce champ");
                    code.requestFocus();
                    return;
                }
                if (phone.getText().toString().equals("") || phone.getText().toString().equals(" ") || phone.getText().toString().equals("\n")) {
                    phone.setError("veuillez remplir ce champ");
                    phone.requestFocus();
                    return;
                }
                if (peuxEcrire) {
                    tabActivity.s += "\n\n name: " + name.getText().toString() +
                            "\n\n code : " + code.getText().toString() +
                            "\n\n téléphone: " + phone.getText().toString();
                    peuxEcrire=false;
                }
                tabActivity.nom = name.getText().toString();
                tabActivity.code = code.getText().toString();
                tabActivity.telephone = phone.getText().toString();

                tabActivity.infoPersoRemplies = true;
                FichierInfoDejaFournies.delete();
                FichierInfoDejaFournies = new File(getActivity().getFilesDir(), "fileexist");

                infoperso = name.getText().toString() + "#" + code.getText().toString() + "#" + phone.getText().toString();
                try {
                    outputStream = getActivity().getApplicationContext().openFileOutput("fileexist", getActivity().MODE_PRIVATE);
                    outputStream.write(infoperso.getBytes());
                    outputStream.close();


                } catch (Exception e) {

                }

                TabLayout host = (TabLayout) getActivity().findViewById(R.id.tabs);
                TabLayout.Tab tab = host.getTabAt(1);
                tab.select();
            }
        });
        return rootView;
    }


    //hide keyboard from a fragment
    public void hideKeyboardFrom() {
        if (getActivity().getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }

    }


}
