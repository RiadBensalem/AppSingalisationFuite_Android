package com.example.ryad.seaal_signalisation_fuite;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by Ryad on 09/09/2018.
 */

public class infogenrlassgn extends Fragment {
    Button next;
    RadioGroup rg1;
    RadioButton rb1TypeFuite;
    EditText adresse;
    // pour evite de dublicer les info dans le corps du mail a cause d'une mauvaise manipulation de l'utilisateur
    static boolean peuxEcrire=true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.infogenrlassgn, container, false);

        next = rootView.findViewById(R.id.next3);
        rg1 = rootView.findViewById(R.id.grp);
        adresse = rootView.findViewById(R.id.adress);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adresse.setError(null);
                hideKeyboardFrom(getActivity(), rootView);
                if (adresse.getText().toString().equals("") || adresse.getText().toString().equals(" ") || adresse.getText().toString().equals("\n")) {
                    adresse.setError("veuillez remplir ce champ");
                    adresse.requestFocus();
                    return;
                }
                int rbId = rg1.getCheckedRadioButtonId();
                rb1TypeFuite = (RadioButton) rootView.findViewById(rbId);
                if (peuxEcrire) {
                    tabActivity.s += "\n\n FUITE D'ASSIGNISSMENT" +
                            "\n\n adresse de la fuite: " + adresse.getText().toString() +
                            "\n\n type de la fuite: " + rb1TypeFuite.getText().toString();
                peuxEcrire=false;
                }
                tabActivity.adresse = adresse.getText().toString();
                tabActivity.type = rb1TypeFuite.getText().toString();

                tabActivity.infoGnrlRemplies = true;
                TabLayout host = (TabLayout) getActivity().findViewById(R.id.tabs);
                TabLayout.Tab tab = host.getTabAt(2);
                tab.select();
            }
        });

        return rootView;
    }

    public void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }
}
