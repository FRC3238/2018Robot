package frc.team3238.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team3238.commands.chassis.RunMP;
import frc.team3238.commands.collector.Eject;
import frc.team3238.commands.lift.LiftToSwitch;
import frc.team3238.utils.Path;

public class PlaceSwitchAuto extends CommandGroup
{

    public PlaceSwitchAuto(Path profile)
    {
        this(profile, false);
    }

    public PlaceSwitchAuto(Path profile, boolean reverse)
    {
        super("Autonomous to Switch");
        addParallel(new LiftToSwitch());
        addSequential(new RunMP(profile, reverse));
        addSequential(new Eject());
    }
}
