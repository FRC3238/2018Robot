package frc.team3238.utils;

/**
 * DriverConfig class
 * Allows adding profiles for drivers, and allows modification of button ids and drive constants.
 * <p>
 * To add a driver profile, copy the programmer profile and change Programmer to another name.
 * Add the name of the class to the configs array, similar to how the programmer class is listed.
 * You can modify the values in the constructor, or remove them to keep them at their defaults, if they have one.
 **/
public class DriverConfig
{
    // joystick mapping
    public int collectID;
    public int ejectID;
    public int extendID;
    public int withdrawID;
    public int cancelID;
    public int upID;
    public int downID;

    // drive constants
    public double deadzone;
    public double twistDeadzone;
    public double drivePower;
    public double driveTwistPower;
    // TODO: remove scale before competition
    public double scale = 1;

    // cheezy drive parameters
    public double cheeziness = 0.7; // total cheeziness, higher number = more, 0 = normal drive
    public double cheezyX = 0.25; // x value of lowest twist value
    public double twistScale = 0.8; // max value of twist with no forward movement, should be at least 1 - cheeziness
    public double cheezyScale = 1; // probably shouldn't be changed, max val of twist with full forward movement

    // TODO: update this once driver is chosen. Main driver should be first.
    public static DriverConfig[] configs = new DriverConfig[]{new Programmer()};

    public static class Programmer extends DriverConfig
    {
        public Programmer()
        {
            super.collectID = 2;
            super.ejectID = 1;
            super.extendID = 5;
            super.withdrawID = 3;
            super.cancelID = 3;
            super.upID = 6;
            super.downID = 4;

            super.deadzone = 0.1;
            super.twistDeadzone = 0.25;
            super.drivePower = 2;
            super.driveTwistPower = 2;
            super.scale = 0.75;

            super.cheeziness = 0.75;
            super.twistScale = 0.6;
        }
    }
}
