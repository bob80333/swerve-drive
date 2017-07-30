# swerve-drive
Simple swerve drive implementation in java by Eric Engelhart

This was originally created for Team 1251 who can be found at [team1251.org](www.team1251.org)

# How to use:

## Step 1:
Create 4 instances of SwerveWheel with 2 PIDControllers for each wheel.
```java
SwerveWheel frontRight = new SwerveWheel(frontRightRotation, frontRightSpeed);
```

## Step 2:
Create an instance of SwerveDrive using the 4 SwerveWheels intances from Step 1.
```java
SwerveDrive driveTrain = new SwerveDrive(frontRight, frontLeft, backLeft, backRight);
```
Alternatively, add a gyro to the constructor for a field-oriented swerve drive.
```java
SwerveDrive driveTrain = new SwerveDrive(frontRight, frontLeft, backLeft, backRight, horizontalGyro);
```

## Step 3:
Set the robot's wheelbase and trackwidth using the setWheelbaseTrackwidth() method
```java
driveTrain.setWheelbaseTrackwidth(ROBOT_WHEELBASE, ROBOT_TRACKWIDTH);
```

## Step 4:
In your main robot loop add a call to the SwerveDrive drive() method and pass your two joysticks in.
```java
driveTrain.drive(directionController, rotationController);
```

That's it!
If your PIDs are tuned correctly, you should now have a working swerve drive.
If not, make sure that the issue is in the library and not due to inaccurate sensors,
or incorrect usage of the library.

If you can confirm that the issue exists in the library, feel free to open an issue.
