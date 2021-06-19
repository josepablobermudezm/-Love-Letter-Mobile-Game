package com.example.proyecto.utilidades;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.example.proyecto.modelos.Datos;


public class Rotacion implements SensorEventListener {

    private Sensor mRotacionVectorSensor;
    private SensorManager mSensorManager;
    private final float[] mRotationMatrix = new float[16];
    private float[] remappedRotationMatrix = new float[16];
    private Activity mActivity;

    public Rotacion(Activity mActivity, SensorManager mSensorManager) {
        this.mActivity = mActivity;
        this.mSensorManager = mSensorManager;
        mRotacionVectorSensor = this.mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        mRotationMatrix[0] = 1;
        mRotationMatrix[8] = 1;
        mRotationMatrix[4] = 1;
        mRotationMatrix[12] = 1;
    }

    public void start() {
        //Habiliar el sensor cuando el Activity esta activo
        //Se actualizan los datos del sensor cada 10 ms
        mSensorManager.registerListener(this, mRotacionVectorSensor, 10000);

    }

    public void stop() {
        //Asegurarse que cuando el Activity esta pausado apagar el sensor
        mSensorManager.unregisterListener(this);
    }

    public void onSensorChanged(SensorEvent event) {
        //Se recibe el evento del sensor. Una buena practica es verificar
        //que el evento recibido corresponde al tipo de sensor
        if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            SensorManager.getRotationMatrixFromVector(mRotationMatrix, event.values);
            SensorManager.remapCoordinateSystem(mRotationMatrix,
                    SensorManager.AXIS_X,
                    SensorManager.AXIS_Z,
                    remappedRotationMatrix);
            //Se convierte la orientacion
            float[] orientation = new float[3];
            SensorManager.getOrientation(remappedRotationMatrix, orientation);
            for (int i = 0; i < 3; i++) {
                orientation[i] = (float) (Math.toDegrees(orientation[i]));
            }

            System.out.println("indice 1 "+ orientation[0]);
            System.out.println("indice 2 "+ orientation[1]);
            System.out.println("indice 3 "+ orientation[2]);


            Datos.getInstance().setX(orientation[0]);
            Datos.getInstance().setY(orientation[1]);
            Datos.getInstance().setZ(orientation[2]);

        }
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
