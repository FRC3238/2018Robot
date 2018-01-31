package frc.team3238.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team3238.commands.chassis.RunMP;
import frc.team3238.commands.collector.Eject;
import frc.team3238.utils.Path;

public class PlaceCubeSwitch extends CommandGroup
{

    public PlaceCubeSwitch(Path profile)
    {
        this(profile, false);
    }

    public PlaceCubeSwitch(Path profile, boolean reverse)
    {
        addParallel(new RunMP(profile, reverse));
        // addParrallel(new LiftToSwitch());
        addSequential(new Eject());
    }
}
