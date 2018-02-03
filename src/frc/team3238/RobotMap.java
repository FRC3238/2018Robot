package frc.team3238;

import jaci.pathfinder.Trajectory;

public class RobotMap
{
    public static class Global
    {
        // timing
        public static final double ROBOT_PERIOD = 0.01;
        public static final int TALON_TIMEOUT = 0;
        // joysticks
        public static final int MAIN_JOYSTICK_PORT = 0;
        // camera constants
        public static final int CAMERA_X_RES = 640;
        public static final int CAMERA_Y_RES = 480;
        public static final int CAMERA_FPS = 20;
        // misc
        public static final double TALON_NEUTRAL_DEADBAND = 0.01;
    }

    public static class Auto
    {
        public static final boolean INVERT_LEFT_PROFILES = false;

        public static final String CENTER = "Center";
        public static final String LEFT = "Left";
        public static final String RIGHT = "Right";
        public static final String NONE = "None";
        public static final String SWITCH = "Switch";
        public static final String SCALE = "Scale";

        public static final String[] POSITIONS = new String[]{CENTER, LEFT, RIGHT};
        public static final String[] PRIORITIES = new String[]{NONE, SWITCH, SCALE};
    }

    public static class Chassis
    {
        // talon ids
        public static final int LEFT_DRIVE_TALON_ID = 0;
        public static final int LEFT_DRIVE_SLAVE_TALON_ID = 1;
        public static final int RIGHT_DRIVE_TALON_ID = 2;
        public static final int RIGHT_DRIVE_SLAVE_TALON_ID = 3;
        // talon constants
        public static final double OPEN_LOOP_RAMP_RATE = 1; // seconds from neutral to full
        public static final double ALLOWED_ERROR = 100;
        // motion profiling run constants
        public static final double MOTION_PROFILE_FRAME_PERIOD = 0.005;
        public static final int MP_PIDF_SLOT = 0;
        public static final double MP_P_VAL = 0.075;
        public static final double MP_I_VAL = 0;
        public static final double MP_D_VAL = 0;
        public static final double MP_F_VAL = 2;
        public static final int MP_MIN_POINTS_IN_TALON = 5;
        // motion profiling path constants
        public static final Trajectory.FitMethod MP_FIT_METHOD = Trajectory.FitMethod.HERMITE_QUINTIC;
        public static final int MP_SAMPLE_RATE = Trajectory.Config.SAMPLES_LOW;
        public static final double MP_TIMESTEP = 0.01; // s
        public static final double MP_MAX_VELOCITY = 15; // ft/s
        public static final double MP_MAX_ACCEL = 60; // ft/s/s
        public static final double MP_MAX_JERK = 600; // ft/s/s/s
        public static final double MP_WHEELBASE_WIDTH = 2.488; // ft, not actual width, calculated.
        public static final double MP_WHEEL_DIAMETER = 8; // in
        public static final int SENSOR_UNITS_PER_ROTATION = 1440;
    }

    public static class Collector
    {
        // talon ids
        public static final int LEFT_COLLECT_TALON_ID = 4;
        public static final int RIGHT_COLLECT_TALON_ID = 5;
        // power
        public static final double COLLECT_POWER = 0.75;
        public static final double EJECT_POWER = -0.5;
        // timing
        public static final double EJECT_TIME = 2;
        // sensing
        public static final double CURRENT_THRESHOLD = 25;
        public static final int CURRENT_MIN_DURATION = 10;
    }

    public static class Extender
    {
        // talon ids
        public static final int EXTENDER_TALON_ID = 6;
        // power
        public static final double EXTEND_POWER = -0.5;
        public static final double WITHDRAW_POWER = -EXTEND_POWER;
        // sensing
        public static final double CURRENT_THRESHOLD = 20;
        public static final int CURRENT_MIN_DURATION = 10;
    }

    public static class Lift
    {
        // talon ids
        public static final int LIFT_TALON_ID = 7;
        public static final int LIFT_SLAVE_TALON_ID = 8;
        // power
        public static final double UP_POWER = 0.5;
        public static final double DOWN_POWER = -0.5;
        // encoder positions
        public static final int UPPER_SOFT_LIMIT = 10000; // encoder clicks
        public static final double SWITCH_HEIGHT = 2;
        public static final double SCALE_HEIGHT = 7;
        public static final int ENCODER_CLICKS_PER_FOOT = 4096;
        // PID values
        public static final int LIFT_PID_SLOT = 0;
        public static final double LIFT_P_VAL = 0;
        public static final double LIFT_I_VAL = 0;
        public static final double LIFT_D_VAL = 0;
        public static final double LIFT_F_VAL = 0;
        public static final int ALLOWED_ERROR = 50;

        public static final double NOMINAL_FORWARD_OUTPUT = 0;
        public static final double NOMINAL_REVERSE_OUTPUT = 0;
        public static final double PEAK_FORWARD_OUTPUT = 1;
        public static final double PEAK_REVERSE_OUTPUT = 0.5;
    }
}
