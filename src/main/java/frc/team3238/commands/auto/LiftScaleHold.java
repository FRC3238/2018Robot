package frc.team3238.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team3238.commands.lift.LiftToScale;

public class LiftScaleHold extends CommandGroup
{
    public LiftScaleHold()
    {
        addParallel(new DelayedHold(), 2);
        addSequential(new LiftToScale());
    }
}
