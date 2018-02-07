package frc.team3238.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static frc.team3238.RobotMap.Collector.LEFT_COLLECT_TALON_ID;
import static frc.team3238.RobotMap.Collector.RIGHT_COLLECT_TALON_ID;
import static frc.team3238.RobotMap.Global.TALON_TIMEOUT;

public class Collector extends Subsystem
{
    private TalonSRX left, right;

    public Collector()
    {
        super("Collector");

        left = new TalonSRX(LEFT_COLLECT_TALON_ID);
        right = new TalonSRX(RIGHT_COLLECT_TALON_ID);

        left.setInverted(false);
        right.setInverted(true);

        left.enableVoltageCompensation(true);
        right.enableVoltageCompensation(true);

        left.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen,
                                            TALON_TIMEOUT);
        left.overrideLimitSwitchesEnable(true);
    }

    @Override
    public void periodic()
    {
        SmartDashboard.putNumber("Collect max current", getMaxCurrent());
    }

    public void setCollector(double power)
    {
        left.set(ControlMode.PercentOutput, power);
        right.set(ControlMode.PercentOutput, power);
    }

    public void spinCollector(double leftPower, double rightPower)
    {
        left.set(ControlMode.PercentOutput, leftPower);
        right.set(ControlMode.PercentOutput, rightPower);
    }

    public void stopMotors()
    {
        left.set(ControlMode.PercentOutput, 0);
        right.set(ControlMode.PercentOutput, 0);
    }

    public boolean getLimitSwitch()
    {
        return left.getSensorCollection().isFwdLimitSwitchClosed();
    }

    public double getMinCurrent()
    {
        return Math.min(left.getOutputCurrent(), right.getOutputCurrent());
    }

    public double getMaxCurrent()
    {
        return Math.max(left.getOutputCurrent(), right.getOutputCurrent());
    }

    public void initDefaultCommand()
    {

    }
}

