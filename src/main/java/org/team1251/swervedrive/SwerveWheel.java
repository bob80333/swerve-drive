package org.team1251.swervedrive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

/**
 * Created by Eric Engelhart on 3/20/2017.
 */
public class SwerveWheel {

    public static final int ENCODER_TICKS = 4096;
    public static final int ROTATION_RATIO = 4;
    public static final double SPEED_RATIO = 48.0/30.0;
    public static final double MAX_SPEED = 9000.0;
    public static final int CONVERT = ENCODER_TICKS * ROTATION_RATIO;
    private TalonSRX rotation;
    private TalonSRX speed;

    private String name;

    public SwerveWheel(TalonSRX rotation, TalonSRX speed, String name){
        this.rotation  = rotation;
        this.speed = speed;
        this.name = name;
    }

    public void updateSpeed(double newSpeed){
        speed.set(ControlMode.Velocity, newSpeed * MAX_SPEED);
    }

    public void updateRotation(double newAngle){
        System.out.println(" New Angle:  " + newAngle);

        int ticks = (int) (newAngle * CONVERT / 360.0);

        int encoderTicks = (rotation.getSelectedSensorPosition(0)/CONVERT ) * CONVERT;

        int error = encoderTicks - ticks;

        if (error < -CONVERT/2) {
            ticks -= CONVERT;
        } else if (error > CONVERT/2) {
            ticks += CONVERT;
        }

        /*
        double oldAngle = (rotation.getSelectedSensorPosition(0)%(ENCODER_TICKS * ROTATION_RATIO)) / ((double) ENCODER_TICKS * ROTATION_RATIO);
        int n = rotation.getSelectedSensorPosition(0)/(ENCODER_TICKS * ROTATION_RATIO);

        double angle = 0.0;
        if (Math.abs(oldAngle-newAngle) > 180) {
            angle = (n + 1 + newAngle) * ENCODER_TICKS * ROTATION_RATIO;
        } else {
            angle = (n + newAngle) * ENCODER_TICKS * ROTATION_RATIO;
        }

        System.out.println("ANGLE: " + angle);

        rotation.set(ControlMode.Position, angle);*/

        rotation.set(ControlMode.Position, ticks);
        //System.out.println("Ticks was " + ticks);
    }
}
