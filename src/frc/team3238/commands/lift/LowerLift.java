package frc.team3238.commands.lift;

import static frc.team3238.Robot.lift;

public class LowerLift extends LiftToHeight
{
    public LowerLift()
    {
        super(0);
    }

    @Override
    public boolean isFinished()
    {
        return lift.isOnTarget(0);
    }
}
