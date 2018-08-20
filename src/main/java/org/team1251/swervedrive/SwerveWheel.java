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
    private TalonSRX rotation;
    private TalonSRX speed;

    public SwerveWheel(TalonSRX rotation, TalonSRX speed){
        this.rotation  = rotation;
        this.speed = speed;
    }

    public void updateSpeed(double newSpeed){
        speed.set(ControlMode.Velocity, newSpeed * ENCODER_TICKS * SPEED_RATIO);
    }

    public void updateRotation(double newAngle){
        System.out.println("New Angle " + newAngle);
        System.out.println("Old ticks " + rotation.getSelectedSensorPosition(0));
        if (newAngle < 0) {
            newAngle += 360.0;
        }
        int oldAngle = (rotation.getSelectedSensorPosition(0)/(ENCODER_TICKS*ROTATION_RATIO));
        double angle = (newAngle + oldAngle) * ENCODER_TICKS * ROTATION_RATIO;
        System.out.println("New ticks " + angle);
        rotation.set(ControlMode.Position, angle);
    }
}
