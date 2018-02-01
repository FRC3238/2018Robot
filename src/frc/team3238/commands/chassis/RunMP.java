package frc.team3238.commands.chassis;

import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motion.TrajectoryPoint;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3238.utils.Path;
import jaci.pathfinder.Waypoint;

import java.util.ArrayList;

import static frc.team3238.Robot.chassis;
import static frc.team3238.RobotMap.Chassis.MP_MIN_POINTS_IN_TALON;
import static frc.team3238.RobotMap.Chassis.MP_PIDF_SLOT;

public class RunMP extends Command
{
    // Hold profile points
    private ArrayList<TrajectoryPoint> left, right;

    // Hold info on talon status
    private SetValueMotionProfile setVal;
    private MotionProfileStatus leftStatus;
    private MotionProfileStatus rightStatus;

    // Hold state information, for while running and if finished
    private int state = 0;
    private boolean isFinished = false;

    /**
     * Create and run a straight line Motion Profile using an auto-generated set of waypoints based on the desired distance
     *
     * @param distance length to travel, in meters
     */
    public RunMP(double distance)
    {
        this(new Waypoint[]{new Waypoint(0, 0, 0), new Waypoint(0, distance, 0)});
    }

    /**
     * Create and run a profile using the given array of waypoints
     *
     * @param waypoints array of waypoints
     */
    public RunMP(Waypoint[] waypoints)
    {
        this(new Path(waypoints));
    }

    /**
     * Run a pre-generated profile
     *
     * @param profile {@link Path} object containing profile to run
     */
    public RunMP(Path profile)
    {
        this(profile, false);
    }

    /**
     * Run a pre-generated profile, with option to flip left-right
     *
     * @param profile {@link Path} object containing profile to run
     * @param flip    true to flip left and right directions
     */
    public RunMP(Path profile, boolean flip)
    {
        this(flip ? profile.getLeft() : profile.getRight(), flip ? profile.getRight() : profile.getLeft());
    }

    /**
     * Run a pre-generated profile given individual wheel profiles
     *
     * @param leftProfile  {@link ArrayList} of {@link TrajectoryPoint} for left wheels
     * @param rightProfile {@link ArrayList} of {@link TrajectoryPoint} for right wheels
     */
    public RunMP(ArrayList<TrajectoryPoint> leftProfile, ArrayList<TrajectoryPoint> rightProfile)
    {
        requires(chassis);

        left = leftProfile;
        right = rightProfile;
    }

    @Override
    protected void initialize()
    {
        DriverStation.reportWarning("Motion Profile starting", false);

        state = 0;
        setVal = SetValueMotionProfile.Disable;

        chassis.setTalonPIDSlot(MP_PIDF_SLOT);
        chassis.resetEncoders();
        chassis.setBrakeMode();

        if(Math.min(left.size(), right.size()) < MP_MIN_POINTS_IN_TALON)
        {
            isFinished = true;
            DriverStation.reportError("Motion Profile was too small, canceling", false);
            return;
        }
        else
        {
            isFinished = false;

            chassis.fillMPBuffer(left, right);
            DriverStation.reportWarning("Motion profile after filling", false);
        }
    }

    @Override
    protected void execute()
    {
        leftStatus = chassis.getLeftStatus();
        rightStatus = chassis.getRightStatus();

        switch(state)
        {
            case 0:
                DriverStation.reportWarning("In case 0 runMp", false);
                SmartDashboard.putNumber("Left buffer", leftStatus.btmBufferCnt);
                if(Math.min(leftStatus.btmBufferCnt, rightStatus.btmBufferCnt) > MP_MIN_POINTS_IN_TALON)
                {
                    state = 1;
                    setVal = SetValueMotionProfile.Enable;
                }
                break;
            case 1:
                DriverStation.reportWarning("In case 1 runMp", false);
                if(leftStatus.activePointValid && leftStatus.isLast && rightStatus.activePointValid &&
                   rightStatus.isLast)
                {
                    setVal = SetValueMotionProfile.Hold;
                    isFinished = true;
                }
                break;
            default:
                DriverStation.reportError("Default case in RunMP.java", false);
        }

        SmartDashboard.putNumber("MP setval", setVal.value);
        chassis.runMotionProfile(setVal);
    }

    @Override
    protected boolean isFinished()
    {
        return isFinished;
    }

    @Override
    protected void end()
    {

    }

    @Override
    protected void interrupted()
    {
        end();
    }
}
