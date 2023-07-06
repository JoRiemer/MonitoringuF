package com.example.muf;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class MainActivity extends AppCompatActivity {
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())
        ).get(MainViewModel.class);

        final TextView vendor = findViewById(R.id.vendor);
        final TextView name = findViewById(R.id.name);
        final TextView version = findViewById(R.id.version);
        final TextView resolution = findViewById(R.id.resolution);
        final TextView maxrange = findViewById(R.id.maxrange);
        final TextView xyz = findViewById(R.id.xyz);
        final TextView power = findViewById(R.id.power);


        mainViewModel.accelerationLiveData.observe(this, (accelerationInformation -> {
            vendor.setText("Vendor" + accelerationInformation.getSensor().getVendor());
            name.setText("Name" + accelerationInformation.getSensor().getName());
            version.setText("Version" + accelerationInformation.getSensor().getVersion());
            resolution.setText("Resolution" + accelerationInformation.getSensor().getResolution());
            maxrange.setText("maxRange" + accelerationInformation.getSensor().getMaximumRange());
            power.setText("Power [mA]" + accelerationInformation.getSensor().getPower());
            xyz.setText("x-axis: " + accelerationInformation.getX()
                    + "y-axis: " + accelerationInformation.getY()
                    + "z-axis: " + accelerationInformation.getZ());
                })
        );
    }
}
