package com.example.muf;

import android.app.Application;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.Surface;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends AndroidViewModel {

    // final LiveData<AccelerationInformation> accelerationLiveData;

     public MainViewModel(@NonNull Application application) {
        super(application);
        final LiveData<AccelerationInformation> accelerationLiveData = new AccelerationLiveData(application.getApplicationContext());
        // accelerationLiveData = new AccelerationLiveData(application.getApplicationContext());

        public void setLiveData(AccelerationInformation accelerationLiveData) {
            accelerationLiveData.setValue(AccelerationInformation);
        }

        public AccelerationInformation getLiveData(){
            return accelerationLiveData.getValue();
        }

        public void observeLiveData(LifecycleOwner owner, Observer<accelerationLiveData> observeLiveData) {
            accelerationLiveData.observe(owner, observeLiveData);
        }
     }

    private final static class AccelerationLiveData extends LiveData<AccelerationInformation> {
        private final AccelerationInformation accelerationInformation = new AccelerationInformation();
        private SensorManager sm;
        private Sensor accelerometer;
        private Sensor gravitySensor;
        private float [] gravity;
        private SensorEventListener listener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                switch (sensorEvent.sensor.getType()) {
                    case Sensor.TYPE_ACCELEROMETER:
                        float[] values = removeGravity(gravity, sensorEvent.values);
                        accelerationInformation.setXYZ(values[0], values[1], values[2]);
                        accelerationInformation.setSensor(sensorEvent.sensor);
                        setValue(accelerationInformation);
                        break;
                    case Sensor.TYPE_GRAVITY:
                        gravity = sensorEvent.values;
                        break;
                    default:
                        break; // Ignore this case!
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        AccelerationLiveData(Context context){
            sm = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
            if (sm != null) {
                gravitySensor = sm.getDefaultSensor(Sensor.TYPE_GRAVITY);
                accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            } else {
                throw new RuntimeException("Don't run!");
            }
        }

        @Override
        protected void onActive() {
            super.onActive();
            sm.registerListener(listener, gravitySensor, SensorManager.SENSOR_DELAY_NORMAL);
            sm.registerListener(listener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }

        @Override
        protected void onInactive() {
            super.onInactive();
            sm.unregisterListener(listener);
        }



        private float[] removeGravity(float[] gravity, float[] values) {
            if (gravity == null) {
                return values;
            }
            final float alpha = 0.8f;
            float g[] = new float[3];
            g[0] = alpha * gravity[0] + (1 - alpha) * values[0];
            g[1] = alpha * gravity[1] + (1 - alpha) * values[1];
            g[2] = alpha * gravity[2] + (1 - alpha) * values[2];

            return new float[]{
                    values[0] - g[0],
                    values[1] - g[1],
                    values[2] - g[2]
            };
        }


        private float[] remapToDisplayRotation(float[] inR) {
            int h, v;
            float[] outR = new float[9];
            switch (display.getRotation()) {
                case Surface
                        .ROTATION_90:
                    h = SensorManager.AXIS_Y;
                    v = SensorManager.AXIS_MINUS_X;
                    break;

                case Surface.ROTATION_180:
                    h = SensorManager.AXIS_MINUS_X;
                    v = SensorManager.AXIS_MINUS_Y;
                    break;

                case Surface.ROTATION_270:
                    h = SensorManager.AXIS_MINUS_X;
                    v = SensorManager.AXIS_X;
                    break;

                case Surface.ROTATION_0:
                    default:
                return inR;
            }

            SensorManager.remapCoordinateSystem(inR, h, v, outR);
            return outR;
        }
    }
}
