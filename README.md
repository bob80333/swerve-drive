# swerve-drive
Simple swerve drive implementation in java by Eric Engelhart

This was originally created for Team 1251 who can be found at team1251.org

#How to use:

##Step 1:
Create 4 instances of SwerveWheel with 2 PIDControllers for each wheel.

##Step 2:
Create an instance of SwerveDrive using the 4 SwerveWheels intances from Step 1.

##Step 3:
Set the robot's wheelbase and trackwidth using the setWheelbaseTrackwidth() method

##Step 4:
In your main robot loop add a call to the SwerveDrive drive() method and pass your two joysticks in.

That's it!
If your PIDs are tuned correctly, you should now have a working swerve drive.
If not, make sure that the issue is in the library and not due to inaccurate sensors,
or incorrect usage of the library.

If you can confirm that the issue exists in the library, feel free to open an issue.
