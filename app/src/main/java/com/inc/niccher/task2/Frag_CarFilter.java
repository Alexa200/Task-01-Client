package com.inc.niccher.task2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

//import com.theartofdev.edmodo.cropper.CropImage;
//import com.theartofdev.edmodo.cropper.CropImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Frag_CarFilter extends Fragment {

    private Spinner vmaker,vbody,vyear,vmileage,vcondi,vregion;

    private Button btnSubmit,btnupload;


    public Frag_CarFilter() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragfilte= inflater.inflate(R.layout.frag_carfilter, container, false);

        return fragfilte;
    }

    private void InitSearch(){

    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

}
