package com.example.myapplicationapp.ui.dispensador;

import android.app.TimePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplicationapp.R;

import com.example.myapplicationapp.ui.utilities.constants;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;


public class MainAdapterSchedule extends FirebaseRecyclerAdapter<ModelDispensador, MainAdapterSchedule.ScheduleViewHolder> {

    int t1H;
    int t1M;
    int t2H;
    int t2M;
    int t3H;
    int t3M;
    int t4H;
    int t4M;


    public MainAdapterSchedule(@NonNull FirebaseRecyclerOptions<ModelDispensador> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ScheduleViewHolder holder, int position, @NonNull ModelDispensador model) {

        Context mContext = holder.mAutomatic.getContext();

        holder.mWater.setText(model.getCantidadAgua());
        holder.textMeal.setText(model.getCantidadComida());

        if (model.getAutomatico().equals("1")) {
            holder.mAutomatic.setText(R.string.automatic_on);
        } else {
            holder.mAutomatic.setText(R.string.automatic_off);
        }
        if (model.getProgramable().equals("1")) {
            holder.mProgrammable.setText(R.string.programmable_on);
        } else {
            holder.mProgrammable.setText(R.string.programmable_off);
        }

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        DatabaseReference mReference = FirebaseDatabase.getInstance().getReference().child("dispensador");

        assert user != null;

        holder.btnProgramable.setOnClickListener(v -> {

            final DialogPlus dialogPlus = DialogPlus.newDialog(holder.mAutomatic.getContext())
                    .setContentHolder(new ViewHolder(R.layout.detalle_programable))
                    .setExpanded(true, 1700)
                    .create();

            View view = dialogPlus.getHolderView();

            TextView hora1 = view.findViewById(R.id.tv_timer1);
            TextView hora2 = view.findViewById(R.id.tv_timer2);
            TextView hora3 = view.findViewById(R.id.tv_timer3);
            TextView hora4 = view.findViewById(R.id.tv_timer4);
            Button btnProg = view.findViewById(R.id.btnModConfigura);

            hora1.setOnClickListener(v1 -> {
                TimePickerDialog.OnTimeSetListener onTimeSetListener = (view1, selectHour, sletedMinute) -> {
                    t1H = selectHour;
                    t1M = sletedMinute;
                    hora1.setText(String.format(Locale.getDefault(), constants.time_format, t1H, t1M));
                };


                TimePickerDialog timePickerDialog = new TimePickerDialog(hora1.getContext(), constants.THEME_HOLO, onTimeSetListener, t1H, t1M, false);
                timePickerDialog.setTitle(R.string.select_time);
                timePickerDialog.show();
            });

            hora2.setOnClickListener(v12 -> {
                TimePickerDialog.OnTimeSetListener onTimeSetListener = (view12, selectHour, sletedMinute) -> {
                    t2H = selectHour;
                    t2M = sletedMinute;
                    hora2.setText(String.format(Locale.getDefault(), constants.time_format, t2H, t2M));
                };

                TimePickerDialog timePickerDialog = new TimePickerDialog(hora2.getContext(), constants.THEME_HOLO, onTimeSetListener, t1H, t1M, false);
                timePickerDialog.setTitle(R.string.select_time);
                timePickerDialog.show();
            });

            hora3.setOnClickListener(v13 -> {
                TimePickerDialog.OnTimeSetListener onTimeSetListener = (view13, selectHour, sletedMinute) -> {
                    t3H = selectHour;
                    t3M = sletedMinute;
                    hora3.setText(String.format(Locale.getDefault(), constants.time_format, t3H, t3M));
                };

                TimePickerDialog timePickerDialog = new TimePickerDialog(hora3.getContext(), constants.THEME_HOLO, onTimeSetListener, t1H, t1M, false);
                timePickerDialog.setTitle(R.string.select_time);
                timePickerDialog.show();
            });
            hora4.setOnClickListener(v14 -> {
                TimePickerDialog.OnTimeSetListener onTimeSetListener = (view14, selectHour, sletedMinute) -> {
                    t4H = selectHour;
                    t4M = sletedMinute;
                    hora4.setText(String.format(Locale.getDefault(), constants.time_format, t4H, t4M));
                };

                TimePickerDialog timePickerDialog = new TimePickerDialog(hora4.getContext(), constants.THEME_HOLO, onTimeSetListener, t1H, t1M, false);
                timePickerDialog.setTitle(R.string.select_time);
                timePickerDialog.show();
            });

            hora1.setText(model.getHorario1());
            hora2.setText(model.getHorario2());
            hora3.setText(model.getHorario3());
            hora4.setText(model.getHorario4());
            dialogPlus.show();

            btnProg.setOnClickListener(v15 -> {

                Map<String, Object> map = new HashMap<>();
                map.put("horario1", hora1.getText().toString());
                map.put("horario2", hora2.getText().toString());
                map.put("horario3", hora3.getText().toString());
                map.put("horario4", hora4.getText().toString());
                map.put("automatico", "0");
                map.put("programable", "1");

                mReference.child(user.getUid()).
                        child(Objects.requireNonNull(getRef(position).getKey())).updateChildren(map)
                        .addOnSuccessListener(unused -> {
                            Toast.makeText(mContext, mContext.getString(R.string.programmable_activated), Toast.LENGTH_SHORT).show();
                            dialogPlus.dismiss();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(mContext, mContext.getString(R.string.display_error), Toast.LENGTH_SHORT).show();
                            dialogPlus.dismiss();
                        });

            });
        });

        holder.btnAut.setOnClickListener(v -> {
            final DialogPlus dialogPlus = DialogPlus.newDialog(holder.mAutomatic.getContext())
                    .setContentHolder(new ViewHolder(R.layout.dettalle_automatico))
                    .setExpanded(true, 1300)
                    .create();

            View view = dialogPlus.getHolderView();
            TextView automa = view.findViewById(R.id.Modautomatico);
            Button btnac = view.findViewById(R.id.btnModAuto);
            automa.setText("1");
            dialogPlus.show();

            btnac.setOnClickListener(v16 -> {
                Map<String, Object> map = new HashMap<>();
                map.put("Automatico", automa.getText().toString());
                map.put("Programable", "0");

                mReference.child(user.getUid()).
                        child(Objects.requireNonNull(getRef(position).getKey())).updateChildren(map)
                        .addOnSuccessListener(unused -> {
                            Toast.makeText(mContext, mContext.getString(R.string.automatic_activated), Toast.LENGTH_SHORT).show();
                            dialogPlus.dismiss();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(mContext, mContext.getString(R.string.display_error), Toast.LENGTH_SHORT).show();
                            dialogPlus.dismiss();
                        });
            });
        });
    }

    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horario_item, parent, false);
        return new ScheduleViewHolder(view);
    }

    static class ScheduleViewHolder extends RecyclerView.ViewHolder {
        TextView mWater;
        TextView textMeal;
        TextView mAutomatic;
        TextView mProgrammable;
        Button btnAut;
        Button btnProgramable;

        public ScheduleViewHolder(@NonNull View itemView) {
            super(itemView);

            mWater = itemView.findViewById(R.id.CAgua);
            textMeal = itemView.findViewById(R.id.Ccomida);
            mAutomatic = itemView.findViewById(R.id.Automatico);
            mProgrammable = itemView.findViewById(R.id.Programable);
            btnAut = itemView.findViewById(R.id.btnAutomatico);
            btnProgramable = itemView.findViewById(R.id.btnProgramable);

        }
    }
}
