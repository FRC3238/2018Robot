package frc.team3238.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team3238.commands.chassis.RunMP;
import frc.team3238.commands.collector.Eject;
import frc.team3238.commands.lift.LiftToScale;
import frc.team3238.utils.Path;

public class PlaceScaleAuto extends CommandGroup
{

    public PlaceScaleAuto(Path profile)
    {
        this(profile, false);
    }

    public PlaceScaleAuto(Path profile, boolean flip)
    {
        super("Autonomous to Scale");
        addParallel(new LiftScaleHold());
        addSequential(new RunMP(profile, flip));
        addSequential(new Eject());
    }
}
