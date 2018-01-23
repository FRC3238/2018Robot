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
    public static final int TALON_RIGHT_2 = 3;
    public static final int TALON_RIGHT_1 = 2;
    public static final int TALON_lEFT_2 = 1;
    public static final int TALON_LEFT_1 = 0;
    public static final int ROBOT_JOYSTICK_PORT = 0;
    public static final double ROBOTICS_DEAD_ZONE = 0.1;
}
