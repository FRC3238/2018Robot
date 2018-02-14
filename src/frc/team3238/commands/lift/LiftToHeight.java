package frc.team3238.commands.lift;

import edu.wpi.first.wpilibj.command.Command;

import static frc.team3238.Robot.lift;

public class LiftToHeight extends Command
{
    private int target;

    public LiftToHeight(double feet)
    {
        super("Lift To Height: " + feet + " ft");
        requires(lift);

        target = lift.feetToEncPos(feet);
    }

    @Override
    protected void initialize()
    {

    }

    @Override
    protected void execute()
    {
        lift.setPosition(target);
    }

    @Override
    protected boolean isFinished()
    {
        return false; //lift.isOnTarget(target);
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
