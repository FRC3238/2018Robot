/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.team3238;

import edu.wpi.first.wpilibj.Joystick;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI


{
    Joystick robotStick = new Joystick(RobotMap.ROBOT_JOYSTICK_PORT);

    public double getY()
    {
        double y = robotStick.getY();
        if(y < RobotMap.ROBOTICS_DEAD_ZONE)
        {
            y = 0;
        }
        return y;
    }

    public double getTwist()
    {
        double twist = robotStick.getTwist();
        if(twist < RobotMap.ROBOTICS_DEAD_ZONE)
        {
            twist = 0;
        }
        return twist;
    }
}
