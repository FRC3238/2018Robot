package frc.team3238.commands.lift;

import edu.wpi.first.wpilibj.command.Command;

import static frc.team3238.Robot.lift;
import static frc.team3238.Robot.oi;

public class LiftDown extends Command
{
    public LiftDown()
    {
        super("Lift Down");
        requires(lift);
    }

    @Override
    protected void initialize()
    {

    }

    @Override
    protected void execute()
    {
        double throttle = oi.getThrottleMult();

        lift.setLift(-throttle);
    }

    @Override
    protected boolean isFinished()
    {
        return false;
    }

    @Override
    protected void end()
    {
        lift.stopMotors();
    }

    @Override
    protected void interrupted()
    {
        end();
    }
}
