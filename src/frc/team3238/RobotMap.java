/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.team3238;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap 
{
    public static final double ROBOT_PERIOD = 0.005;

    public static final int MAIN_JOYSTICK_PORT = 0;

    public static final int COLLECT_BUTTON_ID = 2;
    public static final int EJECT_BUTTON_ID = 5;
    public static final int EXTEND_BUTTON_ID = 4;
    public static final int WITHDRAW_BUTTON_ID = 6;

    public static final int LEFT_DRIVE_TALON_ID = 0;
    public static final int LEFT_DRIVE_SLAVE_TALON_ID = 1;
    public static final int RIGHT_DRIVE_TALON_ID = 2;
    public static final int RIGHT_DRIVE_SLAVE_TALON_ID = 3;

    public static final double DEADZONE = 0.1;
    public static final double TWIST_DEADZONE = 0.25;
    public static final double DRIVE_POWER = 2;
    public static final double DRIVE_TWIST_POWER = 2;

    public static final int LEFT_LIFT_TALON_ID = 4;
    public static final int RIGHT_LIFT_TALON_ID = 5;
    public static final int EXTENDER_TALON_ID = 6;
}
