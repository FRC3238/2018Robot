package frc.team3238.commands.collector;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.team3238.commands.extender.Withdraw;

import static frc.team3238.Robot.collector;
import static frc.team3238.Robot.oi;
import static frc.team3238.RobotMap.Collector.EJECT_TIME;

public class Eject extends Command
{
    Timer timer;

    public Eject()
    {
        super("Eject");
        requires(collector);

        timer = new Timer();
    }

    @Override
    protected void initialize()
    {
        timer.reset();
        timer.start();
    }

    @Override
    protected void execute()
    {
        double throttle = oi.getThrottleMult();

        collector.setCollector(-throttle);
    }

    @Override
    protected boolean isFinished()
    {
        return timer.get() > EJECT_TIME && !oi.getEjectHeld();
    }

    private void endCommon()
    {
        collector.stopMotors();
        timer.stop();
    }

    @Override
    protected void end()
    {
        endCommon();
        Scheduler.getInstance().add(new Withdraw());
    }

    @Override
    protected void interrupted()
    {
        endCommon();
    }
}
