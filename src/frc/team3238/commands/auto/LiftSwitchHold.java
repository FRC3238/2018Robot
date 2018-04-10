package frc.team3238.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team3238.commands.extender.Extend;
import frc.team3238.commands.lift.LiftToSwitch;

public class LiftSwitchHold extends CommandGroup
{
    public LiftSwitchHold()
    {
        addParallel(new DelayedHold(), 2);
        addParallel(new Extend());
        addSequential(new LiftToSwitch());
    }
}
