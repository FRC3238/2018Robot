package frc.team3238.subsystems;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
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

import static frc.team3238.Robot.oi;
import static frc.team3238.RobotMap.Chassis.*;
import static frc.team3238.RobotMap.Global.TALON_NEUTRAL_DEADBAND;
import static frc.team3238.RobotMap.Global.TALON_TIMEOUT;

public class Chassis extends Subsystem
{
    private TalonSRX left, leftSlave, right, rightSlave;

    private MotionProfileStatus status = new MotionProfileStatus();
    private ArrayList<TrajectoryPoint> leftPoints = new ArrayList<>(), rightPoints = new ArrayList<>();

    // TODO: delete if not being used
    private AHRS navX;

    public Chassis()
    {
        super("Chassis");

        // TODO: delete if not being used
        try
        {
            navX = new AHRS(SPI.Port.kMXP);
        } catch(Exception e)
        {
            DriverStation.reportError("Failed to create navX object" + e.getMessage(), false);
        }

        // initialize talons
        left = new TalonSRX(LEFT_DRIVE_TALON_ID);
        leftSlave = new TalonSRX(LEFT_DRIVE_SLAVE_TALON_ID);
        right = new TalonSRX(RIGHT_DRIVE_TALON_ID);
        rightSlave = new TalonSRX(RIGHT_DRIVE_SLAVE_TALON_ID);

        // set right talons as reversed
        left.setInverted(false);
        leftSlave.setInverted(false);
        right.setInverted(true);
        rightSlave.setInverted(true);

        // set slaves as followers
        leftSlave.follow(left);
        rightSlave.follow(right);

        enableVoltageCompensation();
        setBrakeMode();

        left.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, TALON_TIMEOUT);
        right.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, TALON_TIMEOUT);

        left.configOpenloopRamp(OPEN_LOOP_RAMP_RATE, TALON_TIMEOUT);
        right.configOpenloopRamp(OPEN_LOOP_RAMP_RATE, TALON_TIMEOUT);

        left.setSensorPhase(true);
        right.setSensorPhase(true);

        left.configNeutralDeadband(TALON_NEUTRAL_DEADBAND, TALON_TIMEOUT);
        right.configNeutralDeadband(TALON_NEUTRAL_DEADBAND, TALON_TIMEOUT);

        // set PIDF params
        left.config_kP(MP_PIDF_SLOT, MP_P_VAL, TALON_TIMEOUT);
        left.config_kI(MP_PIDF_SLOT, MP_I_VAL, TALON_TIMEOUT);
        left.config_kD(MP_PIDF_SLOT, MP_D_VAL, TALON_TIMEOUT);
        left.config_kF(MP_PIDF_SLOT, MP_F_VAL, TALON_TIMEOUT);
        right.config_kP(MP_PIDF_SLOT, MP_P_VAL, TALON_TIMEOUT);
        right.config_kI(MP_PIDF_SLOT, MP_I_VAL, TALON_TIMEOUT);
        right.config_kD(MP_PIDF_SLOT, MP_D_VAL, TALON_TIMEOUT);
        right.config_kF(MP_PIDF_SLOT, MP_F_VAL, TALON_TIMEOUT);

        // set motion magic params
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

        setFramePeriod((int) (MOTION_PROFILE_FRAME_PERIOD * 1000));

        // set up talon buffer process loop
        Notifier processLoop = new Notifier(this::processMPBuffer);
        processLoop.startPeriodic(MOTION_PROFILE_FRAME_PERIOD / 2);
    }

    public void periodic()
    {
        putSDData();

        fillMPBufferSide(leftPoints, left);
        fillMPBufferSide(rightPoints, right);
    }

    public void initDefaultCommand()
    {
        setDefaultCommand(new Drive());
    }

    // Drive Command
    // -------------
    public void drive(double y, double twist, double scale)
    {
        double lSpeed = y + twist;
        double rSpeed = y - twist;

        lSpeed *= scale;
        rSpeed *= scale;

        left.set(ControlMode.PercentOutput, lSpeed);
        right.set(ControlMode.PercentOutput, rSpeed);
    }

    public void cheesyDrive(double y, double twist, double scale, double cheeziness, double cheezyX, double twistScale,
                            double cheezyScale)
    {
        if(Math.abs(y) < oi.getDeadzone())
        {
            twist *= 1;
        }
        else if(Math.abs(y) < cheezyX)
        {
            twist *= ((twistScale - 1 + cheeziness) / Math.pow(cheezyX - oi.getDeadzone(), 2)) *
                     Math.pow(Math.abs(y) - cheezyX, 2) + 1 -
                     cheeziness;
        }
        else
        {
            twist *= ((cheezyScale - 1 + cheeziness) / Math.pow(1 - cheezyX, 2)) * Math.pow(Math.abs(y) - cheezyX, 2) +
                     1 - cheeziness;
        }

        drive(y, twist, scale);
    }

    // Motion Profile Command
    // ----------------------

    public void runMotionProfile(SetValueMotionProfile setValue)
    {
        left.set(ControlMode.MotionProfile, setValue.value);
        right.set(ControlMode.MotionProfile, setValue.value);
    }

    private void setFramePeriod(int ms)
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
        //        if(!leftPoints.isEmpty())
        //        {
        //            ErrorCode err = left.pushMotionProfileTrajectory(leftPoints.get(0));
        //            if(err.value != ErrorCode.BufferFull.value)
        //            {
        //                leftPoints.remove(0);
        //            }
        //        }
        //        if(!rightPoints.isEmpty())
        //        {
        //            ErrorCode err = right.pushMotionProfileTrajectory(rightPoints.get(0));
        //            if(err.value != ErrorCode.BufferFull.value)
        //            {
        //                rightPoints.remove(0);
        //            }
        //        }

        left.processMotionProfileBuffer();
        right.processMotionProfileBuffer();
    }

    public void fillMPBuffer(ArrayList<TrajectoryPoint> leftTrajPoints, ArrayList<TrajectoryPoint> rightTrajPoints)
    {
        left.clearMotionProfileTrajectories();
        right.clearMotionProfileTrajectories();

        leftPoints = (ArrayList<TrajectoryPoint>) leftTrajPoints.clone();
        rightPoints = (ArrayList<TrajectoryPoint>) rightTrajPoints.clone();

        fillMPBufferSide(leftPoints, left);
        fillMPBufferSide(rightPoints, right);
    }

    private void fillMPBufferSide(ArrayList<TrajectoryPoint> points, TalonSRX talon)
    {
        ErrorCode err;
        while(!points.isEmpty())
        {
            err = talon.pushMotionProfileTrajectory(points.get(0));
            if(err.value != ErrorCode.BufferFull.value)
            {
                points.remove(0);
            }
            else
            {
                break;
            }
        }
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

    // Motion Magic Command
    // --------------------
    public void runMotionMagic(double leftVal, double rightVal)
    {
        left.set(ControlMode.MotionMagic, leftVal);
        right.set(ControlMode.MotionMagic, rightVal);
    }

    // Common
    // ------
    public void resetSetPoint()
    {
    }

    public void setCoastMode()
    {
        left.setNeutralMode(NeutralMode.Coast);
        leftSlave.setNeutralMode(NeutralMode.Coast);
        right.setNeutralMode(NeutralMode.Coast);
        rightSlave.setNeutralMode(NeutralMode.Coast);
    }

    public void setBrakeMode()
    {
        left.setNeutralMode(NeutralMode.Brake);
        leftSlave.setNeutralMode(NeutralMode.Brake);
        right.setNeutralMode(NeutralMode.Brake);
        rightSlave.setNeutralMode(NeutralMode.Brake);
    }

    public double getClosedLoopError()
    {
        return Math.max(left.getClosedLoopError(0), right.getClosedLoopError(0));
    }

    public void resetEncoders()
    {
        left.getSensorCollection().setQuadraturePosition(0, TALON_TIMEOUT);
        right.getSensorCollection().setQuadraturePosition(0, TALON_TIMEOUT);
    }

    public void setTalonPIDSlot(int slot)
    {
        left.selectProfileSlot(slot, 0);
    }

    // TODO: delete if not being used
    public double getAngle()
    {
        return navX.getAngle();
    }

    public void resetAngle()
    {
        navX.reset();
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

