package frc.team3238.commands.routines;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team3238.commands.collector.Eject;
import frc.team3238.commands.extender.Withdraw;

public class EjectCube extends CommandGroup
{

    public EjectCube()
    {
        addSequential(new Eject());
        addSequential(new Withdraw());
    }
}
