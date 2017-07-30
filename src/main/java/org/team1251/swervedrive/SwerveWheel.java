package org.team1251.swervedrive;

import edu.wpi.first.wpilibj.PIDController;

/**
 * Created by Eric Engelhart on 3/20/2017.
 */
public class SwerveWheel {
    private PIDController rotation;
    private PIDController speed;

    public SwerveWheel(PIDController rotation, PIDController speed){
        this.rotation  = rotation;
        this.speed = speed;
    }

    public void updateSpeed(double newSpeed){
        speed.setSetpoint(newSpeed);
    }

    public void updateRotation(double newAngle){
        rotation.setSetpoint(newAngle);
    }

    public void disable() {
        rotation.disable();
        speed.disable();
    }

    public void enable() {
        rotation.enable();
        speed.enable();
    }
}
