package frc.team3238.utils;

import com.ctre.phoenix.motion.TrajectoryPoint;
import edu.wpi.first.wpilibj.DriverStation;
import frc.team3238.RobotMap;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.TankModifier;

import java.util.ArrayList;

import static frc.team3238.RobotMap.Chassis.*;

public class Path
{
    private ArrayList<TrajectoryPoint> left, right;

    // TODO: delete if not used
    private Trajectory traj;

    private int flip = 1;

    public Path(Waypoint[] points)
    {
        try
        {
            if(points[0].y > points[points.length - 1].y)
            {
                flip = -1;
            }

            Trajectory.Config config =
                    new Trajectory.Config(MP_FIT_METHOD, MP_SAMPLE_RATE, MP_TIMESTEP, MP_MAX_VELOCITY, MP_MAX_ACCEL,
                                          MP_MAX_JERK);
            Trajectory trajectory = Pathfinder.generate(points, config);
            traj = trajectory;

            TankModifier modifier = new TankModifier(trajectory);
            modifier.modify(RobotMap.Chassis.MP_WHEELBASE_WIDTH);

            Trajectory leftTrajectory = modifier.getRightTrajectory();
            Trajectory rightTrajectory = modifier.getLeftTrajectory();

            left = new ArrayList<>();
            right = new ArrayList<>();

            mapTrajectory(leftTrajectory, left);
            mapTrajectory(rightTrajectory, right);

        } catch(Exception e)
        {
            DriverStation.reportError("Pathfinder could not generate profile", false);
        }
    }

    public Trajectory getTrajectory()
    {
        return traj;
    }

    private void mapTrajectory(Trajectory trajectory, ArrayList<TrajectoryPoint> array)
    {
        for(int i = 0; i < trajectory.length(); i++)
        {
            Trajectory.Segment segment = trajectory.get(i);

            TrajectoryPoint point = new TrajectoryPoint();

            point.position = flip * segment.position * RobotMap.Chassis.SENSOR_UNITS_PER_ROTATION /
                             (Math.PI * (RobotMap.Chassis.MP_WHEEL_DIAMETER / 12));
            point.velocity = flip * segment.velocity * RobotMap.Chassis.SENSOR_UNITS_PER_ROTATION /
                             (600 * Math.PI * (RobotMap.Chassis.MP_WHEEL_DIAMETER / 12)); // rpm to units per 100 ms
            point.headingDeg = 0;
            point.profileSlotSelect0 = 0;
            point.profileSlotSelect1 = 0;
            point.timeDur = getTimeDur(segment.dt);
            point.zeroPos = i == 0;
            point.isLastPoint = (i + 1) == trajectory.length();

            array.add(point);
        }
    }

    public ArrayList<TrajectoryPoint> getLeft()
    {
        return left;
    }

    public ArrayList<TrajectoryPoint> getRight()
    {
        return right;
    }

    private TrajectoryPoint.TrajectoryDuration getTimeDur(double timeDur)
    {
        int timeDurMS = (int) (timeDur * 1000);

        TrajectoryPoint.TrajectoryDuration duration = TrajectoryPoint.TrajectoryDuration.Trajectory_Duration_0ms;
        duration = duration.valueOf(timeDurMS);

        if(duration.value != timeDurMS)
        {
            DriverStation.reportError("Trajectory duration not supported", false);
            duration = TrajectoryPoint.TrajectoryDuration.Trajectory_Duration_10ms;
        }

        return duration;
    }
}
