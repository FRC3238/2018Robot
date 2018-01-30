package frc.team3238.subsystems;

import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3238.commands.chassis.Drive;

import java.util.ArrayList;

import static frc.team3238.RobotMap.Chassis.*;
import static frc.team3238.RobotMap.Global.*;

public class Chassis extends Subsystem
{
    private TalonSRX left, leftSlave, right, rightSlave;

    private MotionProfileStatus status = new MotionProfileStatus();

    private AHRS navX;

    public Chassis()
    {
        try
        {
            navX = new AHRS(SPI.Port.kMXP);
        } catch(Exception e)
        {
            DriverStation.reportError("Failed to create navX object" + e.getMessage(), false);
        }

        left = new TalonSRX(LEFT_DRIVE_TALON_ID);
        leftSlave = new TalonSRX(LEFT_DRIVE_SLAVE_TALON_ID);
        right = new TalonSRX(RIGHT_DRIVE_TALON_ID);
        rightSlave = new TalonSRX(RIGHT_DRIVE_SLAVE_TALON_ID);

        left.setInverted(false);
        leftSlave.setInverted(false);
        right.setInverted(true);
        rightSlave.setInverted(true);

        leftSlave.follow(left);
        rightSlave.follow(right);

        enableVoltageCompensation();

        left.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, TALON_TIMEOUT);
        right.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, TALON_TIMEOUT);

        left.configOpenloopRamp(OPEN_LOOP_RAMP_RATE, TALON_TIMEOUT);
        right.configOpenloopRamp(OPEN_LOOP_RAMP_RATE, TALON_TIMEOUT);

        left.setSensorPhase(true);
        right.setSensorPhase(true);

        left.configNeutralDeadband(TALON_NEUTRAL_DEADBAND, TALON_TIMEOUT);
        right.configNeutralDeadband(TALON_NEUTRAL_DEADBAND, TALON_TIMEOUT);

        left.config_kP(MP_PIDF_SLOT, MP_P_VAL, TALON_TIMEOUT);
        left.config_kI(MP_PIDF_SLOT, MP_I_VAL, TALON_TIMEOUT);
        left.config_kD(MP_PIDF_SLOT, MP_D_VAL, TALON_TIMEOUT);
        left.config_kF(MP_PIDF_SLOT, MP_F_VAL, TALON_TIMEOUT);
        right.config_kP(MP_PIDF_SLOT, MP_P_VAL, TALON_TIMEOUT);
        right.config_kI(MP_PIDF_SLOT, MP_I_VAL, TALON_TIMEOUT);
        right.config_kD(MP_PIDF_SLOT, MP_D_VAL, TALON_TIMEOUT);
        right.config_kF(MP_PIDF_SLOT, MP_F_VAL, TALON_TIMEOUT);

        left.configMotionCruiseVelocity(
                (int) (MP_MAX_VELOCITY * 12 * SENSOR_UNITS_PER_ROTATION / (MP_WHEEL_DIAMETER * Math.PI)),
                TALON_TIMEOUT);
        right.configMotionCruiseVelocity(
                (int) (MP_MAX_VELOCITY * 12 * SENSOR_UNITS_PER_ROTATION / (MP_WHEEL_DIAMETER * Math.PI)),
                TALON_TIMEOUT);
        left.configMotionAcceleration(
                (int) (MP_MAX_ACCEL * 12 * SENSOR_UNITS_PER_ROTATION / (MP_WHEEL_DIAMETER * Math.PI)), TALON_TIMEOUT);
        right.configMotionAcceleration(
                (int) (MP_MAX_ACCEL * 12 * SENSOR_UNITS_PER_ROTATION / (MP_WHEEL_DIAMETER * Math.PI)), TALON_TIMEOUT);

        setMPFramePeriod((int) (MOTION_PROFILE_FRAME_PERIOD * 1000));

        Notifier processLoop = new Notifier(this::processMPBuffer);
        processLoop.startPeriodic(MOTION_PROFILE_FRAME_PERIOD / 2);
    }

    public void periodic()
    {
        monitor();
        putSDData();
    }

    public void initDefaultCommand()
    {
        setDefaultCommand(new Drive());
    }

    public void drive(double y, double twist, double scale)
    {
        double lSpeed = y + twist;
        double rSpeed = y - twist;

        lSpeed *= scale;
        rSpeed *= scale;

        left.set(ControlMode.PercentOutput, lSpeed);
        right.set(ControlMode.PercentOutput, rSpeed);
    }

    public void cheesyDrive(double y, double twist, double scale, double cheeziness)
    {
        twist *= (4 * cheeziness * (Math.abs(y) - Math.sqrt(Math.abs(y)))) + 1;

        drive(y, twist, scale);
    }

    public void runMotionMagic(double leftVal, double rightVal)
    {
        left.set(ControlMode.MotionMagic, leftVal);
        right.set(ControlMode.MotionMagic, rightVal);
    }

    public double getMinClosedLoopError()
    {
        return Math.min(left.getClosedLoopError(0), right.getClosedLoopError(0));
    }

    public void runMotionProfile(SetValueMotionProfile setValue)
    {
        left.set(ControlMode.MotionProfile, setValue.value);
        right.set(ControlMode.MotionProfile, setValue.value);
    }

    private void setMPFramePeriod(int ms)
    {
        left.changeMotionControlFramePeriod(ms);
        left.configMotionProfileTrajectoryPeriod(ms, TALON_TIMEOUT);
        right.changeMotionControlFramePeriod(ms);
        right.configMotionProfileTrajectoryPeriod(ms, TALON_TIMEOUT);

        left.setStatusFramePeriod(StatusFrame.Status_10_MotionMagic, ms, TALON_TIMEOUT);
        right.setStatusFramePeriod(StatusFrame.Status_10_MotionMagic, ms, TALON_TIMEOUT);
        left.setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, ms, TALON_TIMEOUT);
        right.setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, ms, TALON_TIMEOUT);
    }

    private void processMPBuffer()
    {
        left.processMotionProfileBuffer();
        right.processMotionProfileBuffer();
    }

    public void fillMPBuffer(ArrayList<TrajectoryPoint> leftPoints, ArrayList<TrajectoryPoint> rightPoints)
    {
        left.clearMotionProfileTrajectories();
        right.clearMotionProfileTrajectories();

        for(TrajectoryPoint point : leftPoints)
        {
            left.pushMotionProfileTrajectory(point);
        }
        for(TrajectoryPoint point : rightPoints)
        {
            right.pushMotionProfileTrajectory(point);
        }
    }

    public void setTalonPIDSlot(int slot)
    {
        left.selectProfileSlot(slot, 0);
    }

    public void resetEncoders()
    {
        left.getSensorCollection().setQuadraturePosition(0, TALON_TIMEOUT);
        right.getSensorCollection().setQuadraturePosition(0, TALON_TIMEOUT);
    }

    public MotionProfileStatus getLeftStatus()
    {
        left.getMotionProfileStatus(status);
        return status;
    }

    public MotionProfileStatus getRightStatus()
    {
        right.getMotionProfileStatus(status);
        return status;
    }

    public double getAngle()
    {
        return navX.getAngle();
    }

    public void resetAngle()
    {
        navX.reset();
    }

    private void monitor()
    {
        if(left.getTemperature() > TALON_MAX_TEMP)
        {
            DriverStation.reportError("Left Talon is hot!!!", false);
        }
        if(leftSlave.getTemperature() > TALON_MAX_TEMP)
        {
            DriverStation.reportError("Left Slave Talon is hot!!!", false);
        }
        if(right.getTemperature() > TALON_MAX_TEMP)
        {
            DriverStation.reportError("Right Talon is hot!!!", false);
        }
        if(rightSlave.getTemperature() > TALON_MAX_TEMP)
        {
            DriverStation.reportError("Right Slave Talon is hot!!!", false);
        }
    }

    private void enableVoltageCompensation()
    {
        left.enableVoltageCompensation(true);
        right.enableVoltageCompensation(true);
    }

    public void putSDData()
    {
        SmartDashboard.putNumber("Left Encoder", left.getSelectedSensorPosition(0));
        SmartDashboard.putNumber("Right Encoder", right.getSelectedSensorPosition(0));
        SmartDashboard.putNumber("Left output", left.getMotorOutputPercent());
        SmartDashboard.putNumber("Right output", right.getMotorOutputPercent());
    }
}

