package com.example.myapplicationapp.ui.mascota;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplicationapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainAdapter extends FirebaseRecyclerAdapter<MainModel, MainAdapter.classHolder> {

    CheckBox rabiaEdit;
    CheckBox distemperEdit;
    CheckBox poarvoEdit;

    public MainAdapter(@NonNull FirebaseRecyclerOptions<MainModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull classHolder holder, final int position, @NonNull MainModel model) {

        Context mContext = holder.btnDelete.getContext();

        holder.nombre.setText(model.getNombre());


        holder.btndetalle.setOnClickListener(v -> {


            DialogPlus dialogPlus = DialogPlus.newDialog(mContext)
                    .setContentHolder(new ViewHolder(R.layout.detalle_mascota))
                    .setExpanded(true, 1800)
                    .create();

            View view = dialogPlus.getHolderView();

            TextView nombreM = view.findViewById(R.id.nombremascota);
            TextView edadM = view.findViewById(R.id.edadmascota);

            TextView miniM = view.findViewById(R.id.miniM);
            TextView grandeM = view.findViewById(R.id.grandeM);
            TextView medianoM = view.findViewById(R.id.medianoM);
            TextView giganteM = view.findViewById(R.id.giganteM);

            TextView vRabiaM = view.findViewById(R.id.vRabiaM);
            TextView vDistemperM = view.findViewById(R.id.vDistemperM);
            TextView vParvovirusM = view.findViewById(R.id.vParvovirusM);

            nombreM.setText(model.getNombre());
            edadM.setText(model.getTamanio());

            miniM.setText(model.getTmini());
            medianoM.setText(model.getTmediano());
            grandeM.setText(model.getTgrande());
            giganteM.setText(model.getTgigante());

            if (model.getvRabia().equals(mContext.getString(R.string.rage))) {

                vRabiaM.setText(model.getvRabia());
            } else {

                vRabiaM.setVisibility(View.GONE);
            }

            if (model.getvDistemper().equals(mContext.getString(R.string.distemper))) {

                vDistemperM.setText(model.getvDistemper());
            } else {
                vDistemperM.setVisibility(View.GONE);

            }

            if (model.getvParvovirus().equals(mContext.getString(R.string.parvovirus))) {
                vParvovirusM.setText(model.getvParvovirus());

            } else {

                vParvovirusM.setVisibility(View.GONE);
            }
            dialogPlus.show();
        });

        holder.btnEdit.setOnClickListener(v -> {

            DialogPlus dialogPlus = DialogPlus.newDialog(mContext)
                    .setContentHolder(new ViewHolder(R.layout.editar_mascota))
                    .setExpanded(true, 1800)
                    .create();
            dialogPlus.show();

            View view = dialogPlus.getHolderView();
            EditText editNombre = view.findViewById(R.id.nombremascotaEdit);


            rabiaEdit = view.findViewById(R.id.vRabiaEdit);
            distemperEdit = view.findViewById(R.id.vDistemperedit);
            poarvoEdit = view.findViewById(R.id.vParvovirusEdit);

            TextView vrabia = view.findViewById(R.id.vRabiaME);
            TextView vdistemper = view.findViewById(R.id.vDistemperME);
            TextView vparavirus = view.findViewById(R.id.vParvovirusME);
            editNombre.setText(model.getNombre());


            rabiaEdit.setChecked(model.getvRabia().equals("Rabia"));

            distemperEdit.setChecked(model.getvDistemper().equals("Distemper"));

            String parvo = "Parvovirus";
            poarvoEdit.setChecked(model.getvParvovirus().equals(parvo));

            vrabia.setText(model.getvRabia());
            vdistemper.setText(model.getvDistemper());
            vparavirus.setText(model.getvParvovirus());


            view.findViewById(R.id.btnactualizar).setOnClickListener(v1 -> {
                vacunas();
                Map<String, Object> map = new HashMap<>();
                map.put("nombre", editNombre.getText().toString());
                map.put("vRabia", rabiaEdit.getText().toString());
                map.put("vDistemper", distemperEdit.getText().toString());
                map.put("vParvovirus", poarvoEdit.getText().toString());

                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                FirebaseUser user = mAuth.getCurrentUser();

                assert user != null;
                FirebaseDatabase.getInstance().getReference().child("mascotag").child(user.getUid()).
                        child(Objects.requireNonNull(getRef(position).getKey())).updateChildren(map)
                        .addOnSuccessListener(unused -> {
                            Toast.makeText(holder.nombre.getContext(), "Datos actualizados", Toast.LENGTH_SHORT).show();
                            dialogPlus.dismiss();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(holder.nombre.getContext(), "error al mostrar", Toast.LENGTH_SHORT).show();
                            dialogPlus.dismiss();
                        });
            });
        });

        holder.btnDelete.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("Eliminar");
            builder.setMessage("Desa elimiar a su amscota");
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser user = mAuth.getCurrentUser();

            builder.setPositiveButton("Delete", (dialog, which) -> {
                assert user != null;
                FirebaseDatabase.getInstance().getReference().child("mascotag").child(user.getUid())
                        .child(Objects.requireNonNull(getRef(position).getKey())).removeValue();
            });
            builder.setNegativeButton("cancelar", (dialog, which) -> Toast.makeText(mContext, "se cancelo", Toast.LENGTH_SHORT).show());
            builder.show();
        });

    }

    private void vacunas() {

        if (rabiaEdit.isChecked())
            rabiaEdit.setText(R.string.rage);

        else
            rabiaEdit.setText("");

        if (distemperEdit.isChecked())
            distemperEdit.setText(R.string.distemper);
        else
            distemperEdit.setText("");

        if (poarvoEdit.isChecked())
            poarvoEdit.setText(R.string.parvovirus);
        else
            poarvoEdit.setText("");
    }

    @NonNull
    @Override
    public classHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new classHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item, parent, false));
    }

    static class classHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView nombre;
        Button btnEdit;
        Button btnDelete;
        Button btndetalle;

        public classHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgM);
            nombre = itemView.findViewById(R.id.nametext);

            btndetalle = itemView.findViewById(R.id.btnver);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnEdit = itemView.findViewById(R.id.btnEdit);
        }
    }

}
