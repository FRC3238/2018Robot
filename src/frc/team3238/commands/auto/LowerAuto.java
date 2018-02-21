package frc.team3238.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team3238.commands.chassis.RunMP;
import frc.team3238.commands.lift.LowerLift;
import frc.team3238.utils.Path;

public class LowerAuto extends CommandGroup
{

    public LowerAuto(Path profile)
    {
        this(profile, false);
    }

    public LowerAuto(Path profile, boolean flip)
    {
        super("Autonomous Lower Lift");
        addParallel(new LowerLift());
        addSequential(new RunMP(profile, flip));
    }
}
