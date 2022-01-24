package com.example.myapplicationapp.ui.mascota;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplicationapp.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;


public class MascotaFragment extends Fragment {

    private MainAdapter mainAdapter;
    FloatingActionButton floatingActionButton;

    public MascotaFragment() {
        //constructor required
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mascota, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        assert user != null;
        FirebaseRecyclerOptions<MainModel> options =
                new FirebaseRecyclerOptions.Builder<MainModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child(getString(R.string.get_pet)).child(user.getUid()), MainModel.class)
                        .build();
        mainAdapter = new MainAdapter(options);
        recyclerView.setAdapter(mainAdapter);

        floatingActionButton = view.findViewById(R.id.floatingActionButton);

        Fragment newFragment = new AddFragment();
        floatingActionButton.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frmas, newFragment).commit();
            floatingActionButton.setVisibility(View.GONE);
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mainAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mainAdapter.stopListening();
    }
}