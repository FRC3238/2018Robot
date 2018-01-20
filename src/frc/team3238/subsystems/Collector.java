package frc.team3238.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team3238.RobotMap;

public class Collector extends Subsystem
{
    private TalonSRX left, right;

    private ControlMode collectMode = ControlMode.PercentOutput;

    public Collector()
    {
        left = new TalonSRX(RobotMap.LEFT_LIFT_TALON_ID);
        right = new TalonSRX(RobotMap.RIGHT_LIFT_TALON_ID);

        left.enableVoltageCompensation(true);
        right.enableVoltageCompensation(true);
    }

    public void setCollector(double power)
    {
        left.set(collectMode, power);
        right.set(collectMode, -power);
    }

    public void stopMotors()
    {
        left.set(collectMode, 0);
        right.set(collectMode, 0);
    }

    public void initDefaultCommand()
    {

    }
}

