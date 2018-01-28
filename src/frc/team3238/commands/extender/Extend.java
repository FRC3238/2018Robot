package frc.team3238.commands.extender;

import edu.wpi.first.wpilibj.command.Command;
import frc.team3238.utils.CurrentSwitch;

import static frc.team3238.Robot.extender;
import static frc.team3238.Robot.oi;
import static frc.team3238.RobotMap.Extender.CURRENT_MIN_DURATION;
import static frc.team3238.RobotMap.Extender.CURRENT_THRESHOLD;

public class Extend extends Command
{
    private boolean isFinished;

    private CurrentSwitch currentSwitch;

    public Extend()
    {
        super("Extend");
        requires(extender);

        currentSwitch = new CurrentSwitch(CURRENT_THRESHOLD, CURRENT_MIN_DURATION);
    }

    @Override
    protected void initialize()
    {
        isFinished = false;
        currentSwitch.reset();
    }

    @Override
    protected void execute()
    {
        double throttle = oi.getThrottleMult();

        extender.setExtend(-throttle);

        isFinished = currentSwitch.get(extender.getCurrent()) || extender.getReverseLimit();
    }

    @Override
    protected boolean isFinished()
    {
        return isFinished;
    }

    @Override
    protected void end()
    {
        extender.stopMotor();
    }

    @Override
    protected void interrupted()
    {
        end();
    }
}
