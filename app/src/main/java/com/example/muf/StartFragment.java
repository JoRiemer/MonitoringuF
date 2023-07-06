package com.example.muf;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class StartFragment extends Fragment {

    private MainViewModel mainViewModel;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_startfragment, container, false);
        final EditText
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        final NavController controller = Navigation.findNavController(view);
        view.findViewById(R.id.button).setOnClickListener(button -> {
            controller.navigate(
                    StartFragmentDirections.actionStartFragmentToDashboardFragment("Dashboard Title"));
        });
    }
}

