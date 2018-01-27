package frc.team3238.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team3238.commands.chassis.Drive;

import static frc.team3238.RobotMap.Chassis.LEFT_DRIVE_SLAVE_TALON_ID;
import static frc.team3238.RobotMap.Chassis.LEFT_DRIVE_TALON_ID;
import static frc.team3238.RobotMap.Chassis.RIGHT_DRIVE_SLAVE_TALON_ID;
import static frc.team3238.RobotMap.Chassis.RIGHT_DRIVE_TALON_ID;

public class Chassis extends Subsystem
{
    private TalonSRX left, leftSlave, right, rightSlave;

    private ControlMode driveMode = ControlMode.PercentOutput;

    public Chassis()
    {
        left = new TalonSRX(LEFT_DRIVE_TALON_ID);
        leftSlave = new TalonSRX(LEFT_DRIVE_SLAVE_TALON_ID);
        right = new TalonSRX(RIGHT_DRIVE_TALON_ID);
        rightSlave = new TalonSRX(RIGHT_DRIVE_SLAVE_TALON_ID);

        leftSlave.follow(left);
        rightSlave.follow(right);
    }

    public void initDefaultCommand()
    {
        setDefaultCommand(new Drive());
    }

    public void drive(double y, double twist)
    {
        double lSpeed = y + twist;
        double rSpeed = -y + twist;

        left.set(driveMode, lSpeed);
        right.set(driveMode, rSpeed);
    }
}

