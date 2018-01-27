package frc.team3238.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;

import static frc.team3238.RobotMap.Collector.LEFT_COLLECT_TALON_ID;
import static frc.team3238.RobotMap.Collector.RIGHT_COLLECT_TALON_ID;
import static frc.team3238.RobotMap.Global.TALON_TIMEOUT;

public class Collector extends Subsystem
{
    private TalonSRX left, right;

    private ControlMode collectMode = ControlMode.PercentOutput;

    public Collector()
    {
        left = new TalonSRX(LEFT_COLLECT_TALON_ID);
        right = new TalonSRX(RIGHT_COLLECT_TALON_ID);

        left.enableVoltageCompensation(true);
        right.enableVoltageCompensation(true);

        left.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen,
                                            TALON_TIMEOUT);
        left.overrideLimitSwitchesEnable(true);
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

    public boolean getLimitSwitch()
    {
        return left.getSensorCollection().isFwdLimitSwitchClosed();
    }

    public void initDefaultCommand()
    {

    }
}

