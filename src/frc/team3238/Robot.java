package frc.team3238;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3238.commands.chassis.Drive;
import frc.team3238.commands.collector.Collect;
import frc.team3238.commands.collector.Eject;
import frc.team3238.commands.extender.Extend;
import frc.team3238.commands.extender.Withdraw;
import frc.team3238.subsystems.Chassis;
import frc.team3238.subsystems.Collector;
import frc.team3238.subsystems.Extender;

import static frc.team3238.RobotMap.Global.ROBOT_PERIOD;

public class Robot extends TimedRobot
{

    public static OI oi;

    public static Chassis chassis;
    public static Collector collector;
    public static Extender extender;

    @Override
    public void robotInit()
    {
        setPeriod(ROBOT_PERIOD);

        oi = new OI();

        chassis = new Chassis();
        collector = new Collector();
        extender = new Extender();

        SmartDashboard.putData(Scheduler.getInstance());
        SmartDashboard.putData(new PowerDistributionPanel());

        LiveWindow.add(chassis);
        LiveWindow.add(collector);
        LiveWindow.add(extender);
        LiveWindow.add(chassis);

        LiveWindow.add(new Collect());
        LiveWindow.add(new Drive());
        LiveWindow.add(new Eject());
        LiveWindow.add(new Withdraw());
        LiveWindow.add(new Extend());
    }

    @Override
    public void robotPeriodic()
    {
        SmartDashboard.putNumber("Throttle Mult", oi.getThrottleMult());
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
