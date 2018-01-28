package frc.team3238;

import jaci.pathfinder.Trajectory;

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
        // camera constants
        public static final int CAMERA_X_RES = 640;
        public static final int CAMERA_Y_RES = 480;
        public static final int CAMERA_FPS = 20;
        // misc
        public static final int TALON_MAX_TEMP = 80;
        public static final double TALON_NEUTRAL_DEADBAND = 0.01;
    }

    public static class Chassis
    {
        // talon ids
        public static final int LEFT_DRIVE_TALON_ID = 0;
        public static final int LEFT_DRIVE_SLAVE_TALON_ID = 1;
        public static final int RIGHT_DRIVE_TALON_ID = 2;
        public static final int RIGHT_DRIVE_SLAVE_TALON_ID = 3;
        // motion profiling run constants
        public static final double MOTION_PROFILE_FRAME_PERIOD = 0.005;
        public static final int MP_PIDF_SLOT = 0;
        public static final double MP_P_VAL = 0;
        public static final double MP_I_VAL = 0;
        public static final double MP_D_VAL = 0;
        public static final double MP_F_VAL = 0;
        public static final int MP_MIN_POINTS_IN_TALON = 5;
        // motion profiling path constants
        public static final Trajectory.FitMethod MP_FIT_METHOD = Trajectory.FitMethod.HERMITE_QUINTIC;
        public static final int MP_SAMPLE_RATE = Trajectory.Config.SAMPLES_LOW;
        public static final double MP_TIMESTEP = 0.01; // s
        public static final double MP_MAX_VELOCITY = 30; // ft/s
        public static final double MP_MAX_ACCEL = 60; // ft/s/s
        public static final double MP_MAX_JERK = 900; // ft/s/s/s
        public static final double MP_WHEELBASE_WIDTH = 0.5; // ft, not actual width, calculated.
        public static final double MP_WHEEL_DIAMETER = 0.66667; // ft
        public static final int SENSOR_UNITS_PER_ROTATION = 1440;
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
