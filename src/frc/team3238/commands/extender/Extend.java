package frc.team3238.commands.extender;

import edu.wpi.first.wpilibj.command.Command;

import static frc.team3238.Robot.extender;
import static frc.team3238.Robot.oi;

public class Extend extends Command
{
    public Extend()
    {
        super("Extend");
        requires(extender);
    }

    @Override
    protected void initialize()
    {

    }

    @Override
    protected void execute()
    {
        double throttle = oi.getThrottleMult();

        extender.setExtend(-throttle);
    }

    @Override
    protected boolean isFinished()
    {
        // TODO: add current sensing in case of bad sensor
        return extender.getReverseLimit();
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
