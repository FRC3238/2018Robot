package frc.team3238.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static frc.team3238.RobotMap.Global.TALON_TIMEOUT;
import static frc.team3238.RobotMap.Lift.*;

public class Lift extends Subsystem
{
    private TalonSRX lift, liftSlave;

    public Lift()
    {
        super("Lift");

        lift = new TalonSRX(LIFT_TALON_ID);
        liftSlave = new TalonSRX(LIFT_SLAVE_TALON_ID);

        // TODO: add setInverted and/or setSensorPhase if needed
        lift.setInverted(false);
        liftSlave.setInverted(false);

        lift.setSensorPhase(false);

        lift.setNeutralMode(NeutralMode.Brake);
        liftSlave.setNeutralMode(NeutralMode.Brake);

        liftSlave.follow(lift);

        // TODO: ensure that this should be reverse
        lift.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen,
                                            TALON_TIMEOUT);
        lift.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, TALON_TIMEOUT);

        lift.configForwardSoftLimitThreshold(UPPER_SOFT_LIMIT, TALON_TIMEOUT);
        lift.configForwardSoftLimitEnable(true, TALON_TIMEOUT);
        lift.configReverseSoftLimitThreshold(LOWER_SOFT_LIMIT, TALON_TIMEOUT);
        lift.configReverseSoftLimitEnable(true, TALON_TIMEOUT);

        lift.config_kP(LIFT_PID_SLOT, LIFT_P_VAL, TALON_TIMEOUT);
        lift.config_kI(LIFT_PID_SLOT, LIFT_I_VAL, TALON_TIMEOUT);
        lift.config_kD(LIFT_PID_SLOT, LIFT_D_VAL, TALON_TIMEOUT);
        lift.config_kF(LIFT_PID_SLOT, LIFT_F_VAL, TALON_TIMEOUT);
        lift.config_IntegralZone(LIFT_PID_SLOT, LIFT_I_ZONE, TALON_TIMEOUT);

        lift.configMotionCruiseVelocity((int) MM_MAX_VEL * ENCODER_CLICKS_PER_FOOT * 10, TALON_TIMEOUT);
        lift.configMotionAcceleration((int) MM_MAX_ACCCEL * ENCODER_CLICKS_PER_FOOT * 10, TALON_TIMEOUT);

        lift.configNominalOutputForward(NOMINAL_FORWARD_OUTPUT, TALON_TIMEOUT);
        lift.configNominalOutputReverse(NOMINAL_REVERSE_OUTPUT, TALON_TIMEOUT);
        lift.configPeakOutputForward(PEAK_FORWARD_OUTPUT, TALON_TIMEOUT);
        lift.configPeakOutputReverse(PEAK_REVERSE_OUTPUT, TALON_TIMEOUT);

        // set position to match absolute encoder position
        //        int absPos = lift.getSensorCollection().getPulseWidthPosition();
        //        absPos &= 0xFFF;
        // TODO: flip absPos if talon is inverted OR sensor is inverted, not both
        //        lift.setSelectedSensorPosition(absPos, 0, TALON_TIMEOUT);
    }

    @Override
    public void periodic()
    {
        SmartDashboard.putNumber("Lift enc", lift.getSelectedSensorPosition(0));
        SmartDashboard.putNumber("Lift abs enc", lift.getSensorCollection().getPulseWidthPosition());
        SmartDashboard.putNumber("Lift output", lift.getMotorOutputPercent());
        SmartDashboard.putNumber("Lift current", lift.getOutputCurrent());
    }

    // Manual methods
    public void setLift(double power)
    {
        lift.set(ControlMode.PercentOutput, power);
    }

    public void stopMotors()
    {
        lift.set(ControlMode.PercentOutput, 0.0);
    }

    public int feetToEncPos(double ft)
    {
        return (int) (ft * ENCODER_CLICKS_PER_FOOT);
    }

    public void setPosition(int pos)
    {
        lift.set(ControlMode.Position, pos); // position
        // lift.set(ControlMode.MotionMagic, pos); // motion magic
    }

    public void resetEncoder()
    {
        lift.setSelectedSensorPosition(0, 0, TALON_TIMEOUT);
        DriverStation.reportError("Resetting lift encoders", false);
    }

    public void setEncoder(int pos)
    {
        lift.setSelectedSensorPosition(pos, 0, TALON_TIMEOUT);
        DriverStation.reportError("Setting lift encoder to " + pos, false);
    }

    public boolean isOnTarget(int target)
    {
        return Math.abs(lift.getSelectedSensorPosition(0) - target) < ALLOWED_ERROR;
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

    @Override
    public void initDefaultCommand()
    {

    }
}

