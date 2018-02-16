package frc.team3238.commands.extender;

import edu.wpi.first.wpilibj.command.Command;
import frc.team3238.utils.CurrentSwitch;

import static frc.team3238.Robot.extender;
import static frc.team3238.RobotMap.Extender.*;

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
        extender.setExtend(EXTEND_POWER);

        isFinished = currentSwitch.get(extender.getCurrent()) || extender.getForwardLimit();
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
