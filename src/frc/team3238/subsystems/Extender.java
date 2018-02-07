package frc.team3238.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static frc.team3238.RobotMap.Extender.EXTENDER_TALON_ID;
import static frc.team3238.RobotMap.Global.TALON_TIMEOUT;

public class Extender extends Subsystem
{
    private TalonSRX extend;

    private ControlMode extendMode = ControlMode.PercentOutput;

    public Extender()
    {
        super("Extender");

        extend = new TalonSRX(EXTENDER_TALON_ID);

        extend.enableVoltageCompensation(true);

        extend.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen,
                                              TALON_TIMEOUT);
        extend.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen,
                                              TALON_TIMEOUT);
        extend.overrideLimitSwitchesEnable(true);
    }

    @Override
    public void periodic()
    {
        SmartDashboard.putNumber("Extend current", getCurrent());
    }

    public void setExtend(double power)
    {
        extend.set(extendMode, power);
    }

    public void stopMotor()
    {
        extend.set(extendMode, 0);
    }

    public boolean getForwardLimit()
    {
        return extend.getSensorCollection().isFwdLimitSwitchClosed();
    }

    public boolean getReverseLimit()
    {
        return extend.getSensorCollection().isRevLimitSwitchClosed();
    }

    public double getCurrent()
    {
        return extend.getOutputCurrent();
    }

    public void initDefaultCommand()
    {

    }
}

