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
    // TODO: update this once driver is chosen. Main driver should be first.
    public static DriverConfig[] configs = new DriverConfig[]{new Programmer(), new Driver(), new Logan(), new Nick()};
    // joystick mapping
    public int collectID = 2;
    public int ejectID = 3;
    public int extendID = 5;
    public int withdrawID = 3;
    public int cancelID = 3;
    public int scaleID = 7;
    public int switchID = 9;
    public int lowerID = 11;
    public int upID = 6;
    public int downID = 4;

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
    public int climbID = 12;

    public static class Programmer extends DriverConfig
    {
        public Programmer()
        {
            //            super.collectID = 2;
            //            super.ejectID = 1;
            //            super.extendID = 5;
            //            super.withdrawID = 3;
            //            super.cancelID = 3;
            //            super.switchID = 7;
            //            super.scaleID = 9;
            //            super.lowerID = 11;
            //            super.upID = 6;
            //            super.downID = 4;
            //            super.climbID = 12;

            super.deadzone = 0.1;
            super.twistDeadzone = 0.25;
            super.drivePower = 2;
            super.driveTwistPower = 2;
            super.scale = 0.75;

            super.cheeziness = 0.75;
            super.twistScale = 0.6;
        }
    }

    public static class Driver extends DriverConfig
    {
        public Driver()
        {
            //            super.collectID = 2;
            //            super.ejectID = 1;
            //            super.extendID = 5;
            //            super.withdrawID = 3;
            //            super.cancelID = 3;
            //            super.scaleID = 9;
            //            super.switchID = 7;
            //            super.lowerID = 11;
            //            super.upID = 6;
            //            super.downID = 4;
            //            super.climbID = 12;

            super.deadzone = 0.05;
            super.twistDeadzone = 0.15;
            super.drivePower = 2;
            super.driveTwistPower = 2;

            super.cheeziness = 0.75;
            super.twistScale = 0.9;
        }
    }

    public static class Logan extends DriverConfig
    {
        public Logan()
        {
            //            super.collectID = 2;
            //            super.ejectID = 1;
            //            super.extendID = 5;
            //            super.withdrawID = 3;
            //            super.cancelID = 3;
            //            super.scaleID = 9;
            //            super.switchID = 7;
            //            super.lowerID = 11;
            //            super.upID = 6;
            //            super.downID = 4;
            //            super.climbID = 12;

            super.deadzone = 0.05;
            super.twistDeadzone = 0.15;
            super.drivePower = 2;
            super.driveTwistPower = 2;

            super.cheeziness = 0.25;
            super.twistScale = 0.9;
        }
    }

    public static class Nick extends DriverConfig
    {
        public Nick()
        {
            //            super.collectID = 2;
            //            super.ejectID = 1;
            //            super.extendID = 5;
            //            super.withdrawID = 3;
            //            super.cancelID = 3;
            //            super.scaleID = 9;
            //            super.switchID = 7;
            //            super.lowerID = 11;
            //            super.upID = 6;
            //            super.downID = 4;
            //            super.climbID = 12;

            super.deadzone = 0.05;
            super.twistDeadzone = 0.15;
            super.drivePower = 2;
            super.driveTwistPower = 2;

            super.cheeziness = 0.25;
            super.twistScale = 0.9;
        }
    }
}
