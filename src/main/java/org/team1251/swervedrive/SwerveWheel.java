package org.team1251.swervedrive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

/**
 * Created by Eric Engelhart on 3/20/2017.
 */
public class SwerveWheel {
    private TalonSRX rotation;
    private TalonSRX speed;

    public SwerveWheel(TalonSRX rotation, TalonSRX speed){
        this.rotation  = rotation;
        this.speed = speed;
    }

    public void updateSpeed(double newSpeed){
        speed.set(ControlMode.Velocity, newSpeed);
    }

    public void updateRotation(double newAngle){
        rotation.set(ControlMode.Velocity, newAngle);
    }
}
