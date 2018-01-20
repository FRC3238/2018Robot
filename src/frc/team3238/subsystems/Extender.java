package frc.team3238.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team3238.RobotMap;

public class Extender extends Subsystem
{
    private TalonSRX extend;

    private ControlMode extendMode = ControlMode.PercentOutput;

    public Extender()
    {
        extend = new TalonSRX(RobotMap.EXTENDER_TALON_ID);

        extend.enableVoltageCompensation(true);
    }

    public void setExtend(double power)
    {
        extend.set(extendMode, power);
    }

    public void stopMotor()
    {
        extend.set(extendMode, 0);
    }

    public void initDefaultCommand()
    {

    }
}

