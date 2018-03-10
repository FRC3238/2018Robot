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
        public static final int CAMERA_X_RES = 480;
        public static final int CAMERA_Y_RES = 320;
        public static final int CAMERA_FPS = 20;
        // misc
        public static final double TALON_NEUTRAL_DEADBAND = 0.01;
    }

    public static class Driver
    {
        // buttons
        public static final int COLLECT_ID = 2;
        public static final int EJECT_ID = 1;
        public static final int EXTEND_ID = 5;
        public static final int WITHDRAW_ID = 3;
        public static final int CANCEL_ID = 3;
        public static final int SCALE_ID = 7;
        public static final int SWITCH_ID = 9;
        public static final int LOWER_ID = 11;
        public static final int PORTAL_ID = 12;
        public static final int UP_ID = 6;
        public static final int DOWN_ID = 4;
        public static final int CLIMB_ID = 10;
        // driving
        public static final double TWIST_SCALE = 0.7;
        // driving constants
        public static final double DEADZONE = 0.1;
        public static final double DRIVE_POWER = 2;
        public static final double TWIST_DEADZONE = 0.2;
        public static final double DRIVE_TWIST_POWER = 2;
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
        public static final double OPEN_LOOP_RAMP_RATE = 0; // seconds from neutral to full
        public static final double ALLOWED_ERROR = 100;
        // motion profiling run constants
        public static final double MOTION_PROFILE_FRAME_PERIOD = 0.005;
        public static final int MP_PIDF_SLOT = 0;
        public static final double MP_P_VAL = 2.2;
        public static final double MP_I_VAL = 0.0005;
        public static final double MP_D_VAL = 8;
        public static final double MP_F_VAL = 1.2;
        public static final int MP_MIN_POINTS_IN_TALON = 5;
        // motion profiling path constants
        public static final Trajectory.FitMethod MP_FIT_METHOD = Trajectory.FitMethod.HERMITE_QUINTIC;
        public static final int MP_SAMPLE_RATE = Trajectory.Config.SAMPLES_LOW;
        public static final double MP_TIMESTEP = 0.01; // s
        public static final double MP_MAX_VELOCITY = 7; // ft/s
        public static final double MP_MAX_ACCEL = 10; // ft/s/s
        public static final double MP_MAX_JERK = 400; // ft/s/s/s
        public static final double MP_WHEELBASE_WIDTH = 2.488; // ft, not actual width, calculated.
        public static final double MP_WHEEL_DIAMETER = 7.35; // in
        public static final int SENSOR_UNITS_PER_ROTATION = 1440;
    }

    public static class Collector
    {
        // talon ids
        public static final int LEFT_COLLECT_TALON_ID = 4;
        public static final int RIGHT_COLLECT_TALON_ID = 5;
        // ports
        public static final int LIMIT_SENSOR_PORT = 0;
        // power
        public static final double COLLECT_POWER = 0.55;
        public static final double EJECT_POWER = -0.5;
        public static final double HOLD_POWER = 0.4;
        // timing
        public static final double EJECT_TIME = 1;
        public static final double SHAKE_TIME = 0.5;
        public static final double HOLD_TIMEOUT = 2;
        // sensing
        public static final int IR_CUBE_VAL = 2000;
        public static final double HIGH_CURRENT_THRESHOLD = 40;
        public static final double LOW_CURRENT_THRESHOLD = 15;
        public static final double HOLD_CURRENT_THRESHOLD = 10;
        public static final int CURRENT_MIN_DURATION = 10;
    }

    public static class Extender
    {
        // talon ids
        public static final int EXTENDER_TALON_ID = 6;
        // power
        public static final double EXTEND_POWER = 0.75;
        public static final double WITHDRAW_POWER = -EXTEND_POWER;
        // sensing
        public static final double CURRENT_THRESHOLD = 6;
        public static final int CURRENT_MIN_DURATION = 5;
    }

    public static class Lift
    {
        // talon ids
        public static final int LIFT_TALON_ID = 7;
        public static final int LIFT_SLAVE_TALON_ID = 8;
        // power
        public static final double UP_POWER = 0.5;
        public static final double DOWN_POWER = -0.5;
        // chassis ramp rate params
        public static final double DOWN_RAMP_RATE = 10; // percent per second
        public static final double UP_RAMP_RATE = 0.5; // percent per second
        // encoder positions
        public static final int UPPER_SOFT_LIMIT = 31900; // encoder clicks
        public static final int LOWER_SOFT_LIMIT = 100; // encoder clicks
        public static final double HOLD_HEIGHT = 0.9; // feet
        public static final double PORTAL_HEIGHT = 1.8;
        public static final double SWITCH_HEIGHT = 2;
        public static final double SCALE_HEIGHT = 6;
        public static final double MAX_LIFT_HEIGHT = 6.25;
        public static final int ENCODER_CLICKS_PER_FOOT = (int) (UPPER_SOFT_LIMIT / MAX_LIFT_HEIGHT);
        // PID values
        public static final int LIFT_PID_SLOT = 0;
        public static final double LIFT_P_VAL = 0.5;
        public static final double LIFT_I_VAL = 0.0;
        public static final double LIFT_D_VAL = 0.0;
        public static final double LIFT_F_VAL = 0;
        public static final int LIFT_I_ZONE = 50;
        public static final int ALLOWED_ERROR = 100;

        public static final double MM_MAX_VEL = 3; // ft/s
        public static final double MM_MAX_ACCCEL = 9; // ft/s/s

        public static final double NOMINAL_FORWARD_OUTPUT = 0;
        public static final double NOMINAL_REVERSE_OUTPUT = 0;
        public static final double PEAK_FORWARD_OUTPUT = 1;
        public static final double PEAK_REVERSE_OUTPUT = -0.65;
    }

    public static class Climber
    {
        // talon ids
        public static final int CLIMB_TALON_ID = 9;
        public static final int CLIMB_SLAVE_TALON_ID = 10;
        // power
        public static final double UP_POWER = 0.75;
        public static final double DOWN_POWER = -0.25;
    }
}
