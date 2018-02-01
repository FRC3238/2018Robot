package frc.team3238.commands.auto;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class AutoGroup extends CommandGroup
{

    public AutoGroup(double wait, Command... commands)
    {
        addSequential(new WaitCommand(wait));
        for(Command command : commands)
        {
            addSequential(command);
        }
    }
}
