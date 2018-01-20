package frc.team3238;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.team3238.subsystems.Chassis;
import frc.team3238.subsystems.Collector;
import frc.team3238.subsystems.Extender;

public class Robot extends TimedRobot
{

    public static OI oi;

    public static Chassis chassis;
    public static Collector collector;
    public static Extender extender;

    @Override
    public void robotInit()
    {
        setPeriod(RobotMap.ROBOT_PERIOD);

        oi = new OI();
        chassis = new Chassis();
        collector = new Collector();
        extender = new Extender();
    }

    @Override
    public void disabledInit()
    {

    }

    @Override
    public void disabledPeriodic()
    {
        Scheduler.getInstance().run();
    }

    @Override
    public void autonomousInit()
    {

    }

    @Override
    public void autonomousPeriodic()
    {
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopInit()
    {

    }

    @Override
    public void teleopPeriodic()
    {
        Scheduler.getInstance().run();
    }

    @Override
    public void testPeriodic()
    {

    }
}
