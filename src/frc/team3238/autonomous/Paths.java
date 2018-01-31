package frc.team3238.autonomous;

import edu.wpi.first.wpilibj.command.Command;
import frc.team3238.commands.auto.PlaceCubeSwitch;
import frc.team3238.commands.chassis.RunMP;
import frc.team3238.utils.Path;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

import static frc.team3238.RobotMap.Auto.CENTER;
import static frc.team3238.RobotMap.Auto.LEFT;
import static frc.team3238.RobotMap.Auto.RIGHT;
import static frc.team3238.RobotMap.Auto.SCALE;

public class Paths
{
    public static Command getAutoRoutine(String position, String priorityOne, String priorityTwo, String gameString)
    {
        if(position == CENTER)
        {
            if(gameCharToString(gameString.charAt(0)) == LEFT)
            {
                // run center left
                return new PlaceCubeSwitch(Paths.CENTER_TO_LEFT_SWITCH);
            }
            else
            {
                // run center right
                //                return "Center Right";
            }
        }
        else if(position == gameCharToString(gameString.charAt(priorityToCharPos(priorityOne))))
        {
            // run priority one
            //            return position.concat(" ").concat(priorityOne);
        }
        else if(position == gameCharToString(gameString.charAt(priorityToCharPos(priorityTwo))))
        {
            // run priority two
            // position to priority two
            //            return position.concat(" ").concat(priorityTwo);
        }
        else
        {
            //            return position.concat(" ").concat(position == LEFT ? RIGHT : LEFT).concat(" ").concat(priorityOne);
        }

        return new RunMP(DRIVE_FORWARD);
    }

    private static String gameCharToString(char ch)
    {
        if(ch == 'L')
        {
            return LEFT;
        }
        return RIGHT;
    }

    private static int priorityToCharPos(String priority)
    {
        if(priority == SCALE)
        {
            return 1;
        }
        return 0;
    }

    // Drive Forward
    // -------------
    public static final Path DRIVE_FORWARD =
            new Path(new Waypoint[]{new Waypoint(0, 0, Pathfinder.d2r(90)), new Waypoint(0, 8, Pathfinder.d2r(90))});

    // Center Position
    // ---------------
    // Center to left switch
    public static final Path CENTER_TO_LEFT_SWITCH = new Path(
            new Waypoint[]{new Waypoint(0, 0, Pathfinder.d2r(90)), new Waypoint(-5.333, 11.666, Pathfinder.d2r(90))});
    // Center to right switch
    // Left switch to pile
    // ^Right switch to pile
    // Pile to left switch
    // ^Pile to right switch

    // Left Position
    // -------------
    // Left to left switch
    // Left to left scale
    // Left switch to cube
    // Left scale to cube
    // Cube to left switch
    // Cube to left scale
    // Left to right switch
    // Left to right scale
    // Right switch to cube
    // Right scale to cube
    // Cube to right switch
    // Cube to right scale

    // Right Position
    // --------------
    // reverse above
}
