package com.inc.niccher.task2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//import com.theartofdev.edmodo.cropper.CropImage;
//import com.theartofdev.edmodo.cropper.CropImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Frag_Home extends Fragment {

    CardView cvcars,cvestate,cvadcar,cvadest,cvprof;


    public Frag_Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fraghome= inflater.inflate(R.layout.frag_home, container, false);

        cvcars=fraghome.findViewById(R.id.cardcars);
        cvestate=fraghome.findViewById(R.id.cardestate);
        cvadcar=fraghome.findViewById(R.id.cardaddcar);
        cvadest=fraghome.findViewById(R.id.cardaddestate);
        cvprof=fraghome.findViewById(R.id.cardprof);

        cvcars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Frag_PostV fracar=new Frag_PostV();

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.maincontaina, fracar);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        cvestate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Frag_PostE fraest=new Frag_PostE();

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.maincontaina, fraest);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        cvadcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Add_Car fracar=new Add_Car();

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.maincontaina, fracar);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();*/

                //startActivity(new Intent(getActivity(), Add_Car.class));
            }
        });

        cvadest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Add_Estate fraest=new Add_Estate();

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.maincontaina, fraest);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();*/

                //startActivity(new Intent(getActivity(), Add_Estate.class));
            }
        });

        cvprof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Add_Car fracar=new Add_Car();
                //startActivity(new Intent(getActivity(), Act_myProfile.class));
            }
        });


        return fraghome;
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

}
