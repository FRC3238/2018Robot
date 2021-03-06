package frc.team3238.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team3238.commands.chassis.RunMP;
import frc.team3238.commands.collector.CollectCube;
import frc.team3238.utils.Path;

public class CollectAuto extends CommandGroup
{

    public CollectAuto(Path profile)
    {
        this(profile, false);
    }

    public CollectAuto(Path profile, boolean flip)
    {
        super("Autonomous CollectCube");
        addParallel(new CollectCube());
        addSequential(new RunMP(profile, flip));
    }
}
