package frc.team3238.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.team3238.commands.collector.Hold;

public class DelayedHold extends CommandGroup
{

    public DelayedHold()
    {
        addSequential(new WaitCommand(1));
        addSequential(new Hold());
    }
}
