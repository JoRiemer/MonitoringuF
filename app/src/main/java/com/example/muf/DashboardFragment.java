package com.example.muf;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DashboardFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        TextView dashboardTitleView = view.findViewById(R.id.textView);

        Bundle args = getArguments();
        DashboardFragmentArgs dashboardFragmentArgs = null;
        if (args != null) {
            dashboardFragmentArgs = DashboardFragmentArgs.fromBundle(args);
        }

        if (dashboardFragmentArgs != null) {
            dashboardTitleView.setText(dashboardFragmentArgs.getDashboardTitle());
        }
    }
}

