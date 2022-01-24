package com.example.myapplicationapp.ui.medico;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplicationapp.R;


public class MedicoFragment extends Fragment {


    public MedicoFragment() {
        //constructor required
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_medico, container, false);
    }
}