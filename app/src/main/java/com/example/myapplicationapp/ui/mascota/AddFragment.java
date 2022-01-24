package com.example.myapplicationapp.ui.mascota;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplicationapp.R;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddFragment extends Fragment {


    Button btn;
    EditText nombre;
    EditText tipo;
    EditText edad;
    Button btnRegistrar;
    Button btnCancel;
    CheckBox rabia;
    CheckBox distemper;
    CheckBox parvovirus;

    TextView tvValor;
    TextView valopm;
    TextView valopme;
    TextView valorgrande;
    TextView valorgigante;

    Spinner spinner;
    DatabaseReference dbref;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    FirebaseUser user;

    public AddFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        View view = inflater.inflate(R.layout.fragment_add, container, false);

        nombre = view.findViewById(R.id.txtNombreR);
        tipo = view.findViewById(R.id.txtTipoR);
        edad = view.findViewById(R.id.txtEdadR);
        btnRegistrar = view.findViewById(R.id.btnRegistrar);
        btnCancel = view.findViewById(R.id.btnBack);

        rabia = view.findViewById(R.id.vRabia);
        distemper = view.findViewById(R.id.vDistemper);
        parvovirus = view.findViewById(R.id.vParvovirus);

        tvValor = view.findViewById(R.id.valosipin);
        valopm = view.findViewById(R.id.valorpesom);
        valopme = view.findViewById(R.id.valorpesome);
        valorgrande = view.findViewById(R.id.valorgrande);
        valorgigante = view.findViewById(R.id.valorgigante);
        spinner = view.findViewById(R.id.idspiner);

        DatabaseReference ref1 = dbref = FirebaseDatabase.getInstance().getReference("edadM");
        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list = new ArrayList<>();
                for (DataSnapshot areaSnapshot : snapshot.getChildren()) {

                    String areaName = areaSnapshot.child("cachorro").getValue(String.class);
                    String areaapeso = areaSnapshot.child("mini").getValue(String.class);
                    String areaapeso2 = areaSnapshot.child("mediano").getValue(String.class);
                    String areaapeso3 = areaSnapshot.child("grande").getValue(String.class);
                    String areaapeso4 = areaSnapshot.child("gigante").getValue(String.class);

                    list.add(areaName + " % " + areaapeso + " : " + areaapeso2 + "$" + areaapeso3 + "#" + areaapeso4);
                }

                adapter = new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item, list);
                adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String item = parent.getSelectedItem().toString();

                        String parte1 = item.substring(0, item.lastIndexOf('%'));
                        String parte2 = item.substring(item.lastIndexOf('%') + 1, item.lastIndexOf(':'));
                        String parte3 = item.substring(item.lastIndexOf(':') + 1, item.lastIndexOf('$'));
                        String parte4 = item.substring(item.lastIndexOf('$') + 1, item.lastIndexOf('#'));
                        String parte5 = item.substring(item.lastIndexOf('#') + 1);

                        tvValor.setText(parte1);
                        valopm.setText(parte2);
                        valopme.setText(parte3);
                        valorgrande.setText(parte4);
                        valorgigante.setText(parte5);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btnRegistrar.setOnClickListener(v -> insertaDatos());

        btn.setOnClickListener(v -> Toast.makeText(getActivity(), "Regreso a inicio", Toast.LENGTH_SHORT).show());

        return view;
    }


    private void vacunas() {
        if (rabia.isChecked())
            rabia.setText(R.string.rage);

        else
            rabia.setText("");

        if (distemper.isChecked())
            distemper.setText(R.string.distemper);
        else
            distemper.setText("");

        if (parvovirus.isChecked())
            parvovirus.setText(R.string.parvovirus);
        else
            parvovirus.setText("");
    }

    private void insertaDatos() {

        vacunas();
        Fragment newFragment = new MascotaFragment();
        Map<String, Object> map = new HashMap<>();
        map.put("nombre", nombre.getText().toString());
        map.put("tipo", tipo.getText().toString());
        map.put("edad", edad.getText().toString());

        map.put("vDistemper", distemper.getText().toString());
        map.put("vParvovirus", parvovirus.getText().toString());
        map.put("vRabia", rabia.getText().toString());

        map.put("tamanio", tvValor.getText().toString());
        map.put("tmini", valopm.getText().toString());
        map.put("tmediano", valopme.getText().toString());
        map.put("tgrande", valorgrande.getText().toString());
        map.put("tgigante", valorgigante.getText().toString());


        FirebaseDatabase.getInstance().getReference().child("mascotag").child(user.getUid()).push()
                .setValue(map)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(getActivity(), "Se registro correctamenta", Toast.LENGTH_SHORT).show();
                    limpiar();
                    requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frmas, newFragment).commit();
                    requireActivity().getSupportFragmentManager().beginTransaction().remove(newFragment).commit();
                })
                .addOnFailureListener(e -> Toast.makeText(getActivity(), "Error No se registro ", Toast.LENGTH_SHORT).show());
    }

    private void limpiar() {
        nombre.setText("");
        tipo.setText("");
        edad.setText("");
        distemper.setText("");
        parvovirus.setText("");
        rabia.setText("");
        tvValor.setText("");
        valopm.setText("");
        valopme.setText("");
        valorgrande.setText("");
        valorgigante.setText("");

    }
}