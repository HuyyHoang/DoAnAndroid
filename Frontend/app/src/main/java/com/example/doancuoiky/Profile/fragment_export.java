package com.example.doancuoiky.Profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.example.doancuoiky.R;


public class fragment_export extends Fragment implements AdapterView.OnItemSelectedListener {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Tạo spinner
        View view = inflater.inflate(R.layout.fragment_export_data, container, false);

        Spinner dataReport = (Spinner) view.findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> myAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.dataReport, android.R.layout.simple_spinner_item);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataReport.setAdapter(myAdapter);
        dataReport.setOnItemSelectedListener(this);


        Spinner dataRange = (Spinner) view.findViewById(R.id.dataRange);
        ArrayAdapter<CharSequence> myAdapter2 = ArrayAdapter.createFromResource(getActivity(), R.array.dataRange, android.R.layout.simple_spinner_item);
        //ArrayAdapter<String> myAdapter2 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.dataRange));
        myAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataRange.setAdapter(myAdapter2);
        dataRange.setOnItemSelectedListener(this);

        Spinner dataFormat = (Spinner) view.findViewById(R.id.dataFormat);
        ArrayAdapter<CharSequence> myAdapter3 = ArrayAdapter.createFromResource(getActivity(), R.array.dataFormat, android.R.layout.simple_spinner_item);
        //ArrayAdapter<String> myAdapter3 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.dataFormat));
        myAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataFormat.setAdapter(myAdapter3);
        dataFormat.setOnItemSelectedListener(this);


        ImageButton btn_back_export = (ImageButton) view.findViewById(R.id.btn_back_export);

        btn_back_export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(
                                R.anim.slide_in_from_left,  // enter
                                R.anim.slide_out_to_the_right // exit
                        )
                        .replace(R.id.container,new fragment_Profile())
                        .commit();
            }
        });

        return view;
    }



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


}