package frc.team3238;

public class RobotMap
{
    public static class Global
    {
        // timing
        public static final double ROBOT_PERIOD = 0.005;
        public static final int TALON_TIMEOUT = 0;
        // joysticks
        public static final int MAIN_JOYSTICK_PORT = 0;
        // buttons - can be set to pov values to use pov
        public static final int COLLECT_BUTTON_ID = 2;
        public static final int EJECT_BUTTON_ID = 1;
        public static final int EXTEND_BUTTON_ID = 6;
        public static final int WITHDRAW_BUTTON_ID = 3;
        public static final int CANCEL_BUTTON_ID = 3;
        // driving constants
        public static final double DEADZONE = 0.1;
        public static final double TWIST_DEADZONE = 0.25;
        public static final double DRIVE_POWER = 2;
        public static final double DRIVE_TWIST_POWER = 2;
    }

    public static class Chassis
    {
        // talon ids
        public static final int LEFT_DRIVE_TALON_ID = 0;
        public static final int LEFT_DRIVE_SLAVE_TALON_ID = 1;
        public static final int RIGHT_DRIVE_TALON_ID = 2;
        public static final int RIGHT_DRIVE_SLAVE_TALON_ID = 3;
    }

    public static class Collector
    {
        // talon ids
        public static final int LEFT_COLLECT_TALON_ID = 4;
        public static final int RIGHT_COLLECT_TALON_ID = 5;
        // timing
        public static final double EJECT_TIME = 2;
    }

    public static class Extender
    {
        // talon ids
        public static final int EXTENDER_TALON_ID = 6;
    }
}
