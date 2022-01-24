package com.example.myapplicationapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplicationapp.R;
import com.example.myapplicationapp.databinding.FragmentHomeBinding;
import com.example.myapplicationapp.ui.dispensador.DashboardFragment;
import com.example.myapplicationapp.ui.mascota.MascotaFragment;
import com.example.myapplicationapp.ui.medico.MedicoFragment;
import com.example.myapplicationapp.ui.vacun.VacFragment;
import com.example.myapplicationapp.ui.vacunas.VacunasFragment;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Fragment newFragment = new MascotaFragment();
        Fragment fragDispe = new DashboardFragment();
        Fragment fragVac = new VacFragment();
        Fragment fragVacunas = new VacunasFragment();
        Fragment fragMedico = new MedicoFragment();

        ImageView mas = root.findViewById(R.id.imageView2);
        ImageView vac = root.findViewById(R.id.imageView4);
        ImageView medi = root.findViewById(R.id.imageView6);
        ImageView aseo = root.findViewById(R.id.imageView5);
        ImageView disp = root.findViewById(R.id.imageView7);
        ImageView otrsos = root.findViewById(R.id.imageView8);

        mas.setOnClickListener(v -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.homefrm, newFragment).commit());
        vac.setOnClickListener(v -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.homefrm, fragVac).commit());
        medi.setOnClickListener(v -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.homefrm, fragMedico).commit());
        aseo.setOnClickListener(v -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.homefrm, fragVacunas).commit());
        disp.setOnClickListener(v -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.homefrm, fragDispe).commit());
        otrsos.setOnClickListener(v -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.homefrm, fragVacunas).commit());

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}