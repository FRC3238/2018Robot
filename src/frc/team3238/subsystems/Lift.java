package frc.team3238.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static frc.team3238.RobotMap.Global.ROBOT_PERIOD;
import static frc.team3238.RobotMap.Global.TALON_TIMEOUT;
import static frc.team3238.RobotMap.Lift.*;

public class Lift extends Subsystem
{
    private TalonSRX lift, liftSlave;

    private DigitalInput limit;

    // Convert from per second to per loop, calc slope of line
    private final double rampRateMult =
            ((UP_RAMP_RATE * ROBOT_PERIOD) - (DOWN_RAMP_RATE * ROBOT_PERIOD)) / UPPER_SOFT_LIMIT;

    public Lift()
    {
        super("Lift");

        lift = new TalonSRX(LIFT_TALON_ID);
        liftSlave = new TalonSRX(LIFT_SLAVE_TALON_ID);

        limit = new DigitalInput(0);

        lift.setInverted(true);
        liftSlave.setInverted(true);

        lift.setSensorPhase(true);

        lift.setNeutralMode(NeutralMode.Brake);
        liftSlave.setNeutralMode(NeutralMode.Brake);

        liftSlave.follow(lift);

        // TODO: ensure that this should be reverse
        //        lift.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyClosed,
        //                                            TALON_TIMEOUT);
        //        lift.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, TALON_TIMEOUT);
        lift.overrideLimitSwitchesEnable(false);

        lift.configForwardSoftLimitThreshold(UPPER_SOFT_LIMIT, TALON_TIMEOUT);
        lift.configForwardSoftLimitEnable(true, TALON_TIMEOUT);
        lift.configReverseSoftLimitThreshold(LOWER_SOFT_LIMIT, TALON_TIMEOUT);
        lift.configReverseSoftLimitEnable(false, TALON_TIMEOUT);

        lift.config_kP(LIFT_PID_SLOT, LIFT_P_VAL, TALON_TIMEOUT);
        lift.config_kI(LIFT_PID_SLOT, LIFT_I_VAL, TALON_TIMEOUT);
        lift.config_kD(LIFT_PID_SLOT, LIFT_D_VAL, TALON_TIMEOUT);
        lift.config_kF(LIFT_PID_SLOT, LIFT_F_VAL, TALON_TIMEOUT);
        lift.config_IntegralZone(LIFT_PID_SLOT, LIFT_I_ZONE, TALON_TIMEOUT);

        //        lift.configMotionCruiseVelocity((int) MM_MAX_VEL * ENCODER_CLICKS_PER_FOOT * 10, TALON_TIMEOUT);
        //        lift.configMotionAcceleration((int) MM_MAX_ACCCEL * ENCODER_CLICKS_PER_FOOT * 10, TALON_TIMEOUT);

        lift.configNominalOutputForward(NOMINAL_FORWARD_OUTPUT, TALON_TIMEOUT);
        lift.configNominalOutputReverse(NOMINAL_REVERSE_OUTPUT, TALON_TIMEOUT);
        lift.configPeakOutputForward(PEAK_FORWARD_OUTPUT, TALON_TIMEOUT);
        lift.configPeakOutputReverse(PEAK_REVERSE_OUTPUT, TALON_TIMEOUT);
    }

    @Override
    public void periodic()
    {
        SmartDashboard.putNumber("Lift enc", getPosition());
        SmartDashboard.putNumber("Lift output", lift.getMotorOutputPercent());
        SmartDashboard.putNumber("Lift current", lift.getOutputCurrent());
        SmartDashboard.putBoolean("Lift limit", isLimit());

        if(getPosition() < 0 || isLimit())
        {
            resetEncoder();
        }
    }

    // Setters
    public void setLift(double power)
    {
        if(!isLimit() || power > 0)
        {
            lift.set(ControlMode.PercentOutput, power);
        }
    }

    public int getPosition()
    {
        return lift.getSelectedSensorPosition(0);
    }

    // Common
    public int feetToEncPos(double ft)
    {
        return (int) (ft * ENCODER_CLICKS_PER_FOOT);
    }

    public void setPosition(int pos)
    {
        if(!isLimit() || pos > getPosition())
        {
            lift.set(ControlMode.Position, pos);
        }
    }

    private boolean isLimit()
    {
        return limit.get();
    }

    public void stopMotors()
    {
        lift.set(ControlMode.PercentOutput, 0.0);
    }

    public void resetEncoder()
    {
        setEncoder(0);
    }

    public void setEncoder(int pos)
    {
        lift.setSelectedSensorPosition(pos, 0, TALON_TIMEOUT);
    }

    public boolean isOnTarget(int target, int error)
    {
        return Math.abs(getPosition() - target) < error;
    }

    public boolean isOnTarget(int target)
    {
        return isOnTarget(target, ALLOWED_ERROR);
    }

    public double calcFeedForward(double speed)
    {
        setLift(speed);

        double retVal = 0;
        if(lift.getSelectedSensorVelocity(0) != 0)
        {
            retVal = speed * 1023 / lift.getSelectedSensorVelocity(0);
        }

        return retVal;
    }

    // Internal
    @Override
    public void initDefaultCommand()
    {

    }

    // External
    public double getChassisAccel()
    {
        return rampRateMult * getPosition() + (DOWN_RAMP_RATE * ROBOT_PERIOD);
    }
}

