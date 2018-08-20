package org.team1251.swervedrive;

import edu.wpi.first.wpilibj.GyroBase;
import edu.wpi.first.wpilibj.Joystick;
import net.jafama.FastMath;

/**
 * Created by Eric Engelhart on 3/17/2017.
 */
public class SwerveDrive {

    public static final double INSIGNIFICANT_DIFFERENCE = 0.0001;

    private SwerveWheel[] swerveWheels;
    private GyroBase gyro;

    private boolean fieldOrientedDrive;

    private double[] speeds;
    private double[] rotations;

    private double wheelbase;
    private double trackwidth;

    private double oldRotation;
    private double oldX;
    private double oldY;

    public SwerveDrive(SwerveWheel rightFront, SwerveWheel leftFront, SwerveWheel leftBack, SwerveWheel rightBack){
        swerveWheels = new SwerveWheel[4];

        swerveWheels[0] = rightFront;
        swerveWheels[1] = leftFront;
        swerveWheels[2] = leftBack;
        swerveWheels[3] = rightBack;

        fieldOrientedDrive = false;
    }

    public SwerveDrive(SwerveWheel rightFront, SwerveWheel leftFront, SwerveWheel leftBack, SwerveWheel rightBack, GyroBase gyro){
        swerveWheels = new SwerveWheel[4];

        swerveWheels[0] = rightFront;
        swerveWheels[1] = leftFront;
        swerveWheels[2] = leftBack;
        swerveWheels[3] = rightBack;

        this.gyro = gyro;

        fieldOrientedDrive = true;
    }

    /*
    Wheel 1 is rightFront
    Wheel 2 is leftFront
    Wheel 3 is leftBack
    Wheel 4 is rightBack
    It's a counterclockwise order.
     */
    private void calculate(double rotationRad, double movementX, double movementY){
        // calculate x/y components for wheels 1 & 3 as thats all that's needed
        double B = movementX + (rotationRad * (wheelbase  / 2.0));
        double C = movementY - (rotationRad * (trackwidth / 2.0));
        double A = movementX - (rotationRad * (wheelbase  / 2.0));
        double D = movementY + (rotationRad * (trackwidth / 2.0));

        System.out.println("ROTATION RADS " + rotationRad);

        speeds = new double[4];
        rotations = new double[4];

        // calculate speed/rotation for each wheel
        speeds[0]    = FastMath.sqrt(FastMath.pow2(B) + FastMath.pow2(C));
        rotations[0] = FastMath.toDegrees(FastMath.atan2(B, C));

        speeds[1]    = FastMath.sqrt(FastMath.pow2(B) + FastMath.pow2(D));
        rotations[1] = FastMath.toDegrees(FastMath.atan2(B, D));

        speeds[2]    = FastMath.sqrt(FastMath.pow2(A) + FastMath.pow2(D));
        rotations[2] = FastMath.toDegrees(FastMath.atan2(A, D));

        speeds[3]    = FastMath.sqrt(FastMath.pow2(A) + FastMath.pow2(C));
        rotations[3] = FastMath.toDegrees(FastMath.atan2(A, C));

        // normalize speeds to a good speed;
        speeds = normalizeSpeeds(speeds, movementX, movementY);
    }

    public double[] normalizeSpeeds(double[] speeds, double movementX, double movementY){
        double maxSpeed = speeds[0];
        double minSpeed = speeds[0];
        // find min and max speeds
        for (int i = 1; i < speeds.length; i++){
            if (speeds[i] > maxSpeed){
                maxSpeed = speeds[i];
            }
            if (speeds[i] < minSpeed){
                minSpeed = speeds[i];
            }
        }
        // normalize to fastest speed
        if (maxSpeed > 1){
            for (int i = 0; i < speeds.length; i++){
                speeds[i] /= maxSpeed;
            }
        }
        // normalize to fastest speed if negative
        if (minSpeed < -1){
            for (int i = 0; i < speeds.length; i++) {
                speeds[i] /= minSpeed * -1;
            }
        }

        // get joystick magnitude of distance (faster than joystick.getMagnitude b/c FastMath
        double magnitude = FastMath.sqrt(FastMath.pow2(movementX) + FastMath.pow2(movementY));
        // make sure magnitude isn't >1
        // magnitude is now guaranteed to be 0 <= x <= 1 where x is magnitude
        if (magnitude > 1){
            magnitude = 1;
        }
        // normalize to magnitude
        for (int i = 0; i < speeds.length; i++) {
            speeds[i] *= magnitude;
        }
        return speeds;
    }

    public void setWheelbaseTrackwidth(double wheelbase, double trackwidth){
        this.wheelbase = wheelbase;
        this.trackwidth = trackwidth;
    }

    private void updateSwerveWheeels(){
        for (int i = 0; i < swerveWheels.length; i++){
            swerveWheels[i].updateRotation(rotations[i]);
            swerveWheels[i].updateSpeed(speeds[i]);
        }
    }

    private void saveJoystickInputs(double angle, double x, double y){
        oldRotation = angle;
        oldX = x;
        oldY = y;
    }

    private boolean differentFromOld(double angle, double x, double y){
        return significantDiff(oldRotation, angle) || significantDiff(oldX, x) || significantDiff(oldY, y);
    }

    public void drive(double angleRadians, double xDirection, double yDirection) {
        double angle = angleRadians;
        if (this.fieldOrientedDrive) {
            angle = angleRadians - FastMath.toRadians(this.normalizeGyroAngle(this.gyro.getAngle()));
        }

        if (this.differentFromOld(angle, xDirection, yDirection)) {
            // only recalculate and update outputs if inputs are sufficiently different
            this.calculate(angle, xDirection, yDirection);
            this.saveJoystickInputs(angle, xDirection, yDirection);
            this.updateSwerveWheeels();
        }

    }

    public double normalizeGyroAngle(double angle){
        return (angle - (FastMath.floor( angle / 360) * 360) );
    }

    public boolean significantDiff(double d1, double d2){
        // if difference between two doubles is less than 0.0001, it usually isn't significant
        // if it is, you aren't using this to compare the two doubles
        return FastMath.abs(d1 - d2) < INSIGNIFICANT_DIFFERENCE;
    }
}
