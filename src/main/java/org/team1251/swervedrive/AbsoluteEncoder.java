package org.team1251.swervedrive;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 * Created by Eric Engelhart on 5/8/2017.
 * Easily use absolute encoders by just pretending they are potentiometers,
 * because they are just potentiometers without hard stops
 */
public class AbsoluteEncoder implements PIDSource {
    private AnalogPotentiometer encoder;

    public AbsoluteEncoder(int encoderPort){
        encoder = new AnalogPotentiometer(encoderPort, 360.0, 0);
    }

    public double get(){
        return encoder.get();
    }


    // not setup yet
    public void setPIDSourceType(PIDSourceType pidSourceType) {

    }

    public PIDSourceType getPIDSourceType() {
        return PIDSourceType.kDisplacement;
    }

    public double pidGet() {
        return encoder.get();
    }
}
