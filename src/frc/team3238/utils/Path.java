package frc.team3238.utils;

import com.ctre.phoenix.motion.TrajectoryPoint;
import edu.wpi.first.wpilibj.DriverStation;
import frc.team3238.RobotMap;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.TankModifier;

import java.util.ArrayList;

public class Path
{
    private ArrayList<TrajectoryPoint> left, right;

    public Path(Waypoint[] points)
    {
        try
        {
            Trajectory.Config config =
                    new Trajectory.Config(RobotMap.Chassis.MP_FIT_METHOD, RobotMap.Chassis.MP_SAMPLE_RATE,
                                          RobotMap.Chassis.MP_TIMESTEP, RobotMap.Chassis.MP_MAX_VELOCITY,
                                          RobotMap.Chassis.MP_MAX_ACCEL, RobotMap.Chassis.MP_MAX_JERK);
            Trajectory trajectory = Pathfinder.generate(points, config);

            TankModifier modifier = new TankModifier(trajectory);
            modifier.modify(RobotMap.Chassis.MP_WHEELBASE_WIDTH);

            Trajectory leftTrajectory = modifier.getLeftTrajectory();
            Trajectory rightTrajectory = modifier.getRightTrajectory();

            left = new ArrayList<>();
            right = new ArrayList<>();

            for(int i = 0; i < leftTrajectory.length(); i++)
            {
                Trajectory.Segment segment = leftTrajectory.get(i);

                TrajectoryPoint point = new TrajectoryPoint();
                point.position = segment.position * RobotMap.Chassis.SENSOR_UNITS_PER_ROTATION;
                point.velocity =
                        segment.velocity * RobotMap.Chassis.SENSOR_UNITS_PER_ROTATION / 600; // rpm to units per 100 ms
                point.headingDeg = 0;
                point.profileSlotSelect0 = 0;
                point.profileSlotSelect1 = 0;
                point.timeDur = getTimeDur(segment.dt);
                point.zeroPos = i == 0;
                point.isLastPoint = (i + 1) == leftTrajectory.length();

                left.add(point);
            }
            for(int i = 0; i < rightTrajectory.length(); i++)
            {
                Trajectory.Segment segment = rightTrajectory.get(i);

                TrajectoryPoint point = new TrajectoryPoint();
                point.position = segment.position * RobotMap.Chassis.SENSOR_UNITS_PER_ROTATION /
                                 (Math.PI * RobotMap.Chassis.MP_WHEEL_DIAMETER);
                point.velocity = segment.velocity * RobotMap.Chassis.SENSOR_UNITS_PER_ROTATION /
                                 (600 * Math.PI * RobotMap.Chassis.MP_WHEEL_DIAMETER); // rpm to units per 100 ms
                point.headingDeg = 0;
                point.profileSlotSelect0 = 0;
                point.profileSlotSelect1 = 0;
                point.timeDur = getTimeDur(segment.dt);
                point.zeroPos = i == 0;
                point.isLastPoint = (i + 1) == rightTrajectory.length();

                right.add(point);
            }
        } catch(Exception e)
        {
            DriverStation.reportError("Pathfinder could not generate profile", false);
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
