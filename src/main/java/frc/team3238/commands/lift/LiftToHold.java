package frc.team3238.commands.lift;

import static frc.team3238.Robot.lift;
import static frc.team3238.RobotMap.Lift.ALLOWED_ERROR;
import static frc.team3238.RobotMap.Lift.HOLD_HEIGHT;

public class LiftToHold extends LiftToHeight
{
    public LiftToHold()
    {
        super(HOLD_HEIGHT);
    }

    @Override
    public boolean isFinished()
    {
        return lift.isOnTarget(lift.feetToEncPos(HOLD_HEIGHT), ALLOWED_ERROR * 2);
    }
}
