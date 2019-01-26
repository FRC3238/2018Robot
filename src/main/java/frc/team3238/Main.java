package frc.team3238;

import edu.wpi.first.wpilibj.RobotBase;

/**
 * Main class
 *
 * @author Loren
 */
public class Main {
    public static void main(String[] args) {
        //Provides RoboRIO a way of getting a Robot object,
        //then the robot can start.
        RobotBase.startRobot(Robot::new);
    }
}
