package frc.team3238.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team3238.commands.lift.LiftToSwitch;

public class LiftSwitchHold extends CommandGroup
{
    public LiftSwitchHold()
    {
        addParallel(new DelayedHold(), 2);
        addSequential(new LiftToSwitch());
    }
}
