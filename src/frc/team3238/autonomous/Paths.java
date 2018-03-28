package frc.team3238.autonomous;

import edu.wpi.first.wpilibj.command.Command;
import frc.team3238.commands.auto.*;
import frc.team3238.commands.chassis.RunMP;
import frc.team3238.utils.Path;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

import java.util.Objects;

import static frc.team3238.RobotMap.Auto.*;

public class Paths
{

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
        if(priority.equals(SCALE))
        {
            return 1;
        }
        return 0;
    }

    private static boolean invertSide(String side)
    {
        return (side.equals(LEFT)) == INVERT_LEFT_PROFILES;
    }

    // All constants are in feet
    public static final double ROBOT_WIDTH = 2.833;
    private static final double ROBOT_LENGTH = 3.166;
    private static final double CUBE_WIDTH = 1.083;

    private static final Waypoint CENTER_START = new Waypoint((ROBOT_WIDTH / 2) - 1, ROBOT_LENGTH, Pathfinder.d2r(90));
    private static final double DIST_CENTER_X_TO_SWITCH = 4.254;
    private static final double DIST_WALL_TO_SWITCH = 12;
    private static final double DIST_SWITCH_TO_CUBE_PICKUP = 8;

    private static final double LEFT_START_X = -(11 - (ROBOT_WIDTH / 2));
    private static final Waypoint LEFT_START = new Waypoint(LEFT_START_X, ROBOT_LENGTH, Pathfinder.d2r(90));
    private static final double DIST_SIDE_TO_SWITCH_Y = 16;
    private static final double DIST_SIDE_TO_SWITCH_X = -6.375;
    private static final double DIST_SIDE_TO_SCALE_Y = 24.4;
    private static final double DIST_SIDE_TO_SCALE_X = -6.75;

    // Drive Forward
    // -------------
    private static Path DRIVE_FORWARD;
    // Center Position
    // ---------------
    // Center to left switch
    public static Path CENTER_TO_LEFT_SWITCH;
    // Center to right switch
    private static Path CENTER_TO_RIGHT_SWITCH;
    // Left switch to pile
    // ^Right switch to pile
    private static Path SWITCH_TO_PILE_ONE;
    private static Path SWITCH_TO_PILE_TWO;
    // Pile to left switch
    // ^Pile to right switch
    private static Path PILE_TO_SWITCH_ONE;
    private static Path PILE_TO_SWITCH_TWO;
    // Side Position
    // -------------
    // Left to left switch
    private static Path SIDE_TO_SWITCH;
    // Left to left scale
    private static Path SIDE_TO_SCALE;
    // Left switch to cube
    // Left scale to cube
    private static Path SCALE_TO_CUBE_ONE;
    private static Path SCALE_TO_CUBE_TWO;
    private static Path CUBE_TO_SCALE_ONE;
    private static Path CUBE_TO_SCALE_TWO;
    // Cube to left switch
    // Cube to left scale
    // Left to right switch
    private static Path SIDE_TO_OPP_SWITCH;
    // Left to right scale
    // Right switch to cube
    // Right scale to cube
    // Cube to right switch
    // Cube to right scale

    public static void calcPaths()
    {
        Waypoint scale = new Waypoint(DIST_SIDE_TO_SCALE_X, DIST_SIDE_TO_SCALE_Y, Pathfinder.d2r(90));
        Waypoint scaleTwoMidpoint = new Waypoint(LEFT_START_X, DIST_SIDE_TO_SCALE_Y - 3, Pathfinder.d2r(0));
        Waypoint cube = new Waypoint(-7, 17, Pathfinder.d2r(330));
        SIDE_TO_SCALE = new Path(
                new Waypoint[]{LEFT_START, new Waypoint(LEFT_START_X, DIST_SIDE_TO_SWITCH_Y, Pathfinder.d2r(90)),
                               scale});
        SCALE_TO_CUBE_ONE = new Path(new Waypoint[]{scale, scaleTwoMidpoint}, true);
        SCALE_TO_CUBE_TWO = new Path(new Waypoint[]{scaleTwoMidpoint, cube}, false);
        CUBE_TO_SCALE_ONE = new Path(new Waypoint[]{cube, scaleTwoMidpoint}, true);
        CUBE_TO_SCALE_TWO = new Path(new Waypoint[]{scaleTwoMidpoint, scale}, false);

        SIDE_TO_SWITCH = new Path(new Waypoint[]{LEFT_START, //new Waypoint(DIST_SIDE_TO_SWITCH_X - (ROBOT_LENGTH),
                                                 //                                                            DIST_SIDE_TO_SWITCH_Y - ROBOT_LENGTH, Pathfinder.d2r(30)),
                                                 //                                                 new Waypoint(DIST_SIDE_TO_SWITCH_X, DIST_SIDE_TO_SWITCH_Y, Pathfinder.d2r(0))});
                                                 new Waypoint(DIST_SIDE_TO_SWITCH_X - (ROBOT_WIDTH / 50),
                                                              DIST_SIDE_TO_SWITCH_Y - (ROBOT_LENGTH),
                                                              Pathfinder.d2r(45))});
        PILE_TO_SWITCH_TWO = new Path(new Waypoint[]{new Waypoint(0, 0, Pathfinder.d2r(90)),
                                                     new Waypoint(-DIST_CENTER_X_TO_SWITCH, DIST_SWITCH_TO_CUBE_PICKUP,
                                                                  Pathfinder.d2r(90))});
        PILE_TO_SWITCH_ONE = new Path(new Waypoint[]{new Waypoint(0, 0, Pathfinder.d2r(90)),
                                                     new Waypoint(0, -(DIST_SWITCH_TO_CUBE_PICKUP - (CUBE_WIDTH * 3)),
                                                                  Pathfinder.d2r(90))});
        SWITCH_TO_PILE_ONE = new Path(new Waypoint[]{new Waypoint(0, 0, Pathfinder.d2r(90)),
                                                     new Waypoint(-DIST_CENTER_X_TO_SWITCH, -DIST_SWITCH_TO_CUBE_PICKUP,
                                                                  Pathfinder.d2r(90))});
        SWITCH_TO_PILE_TWO = new Path(new Waypoint[]{new Waypoint(0, 0, Pathfinder.d2r(90)),
                                                     new Waypoint(0, (DIST_SWITCH_TO_CUBE_PICKUP - (CUBE_WIDTH * 3)),
                                                                  Pathfinder.d2r(90))});
        DRIVE_FORWARD = new Path(new Waypoint[]{new Waypoint(0, ROBOT_LENGTH, Pathfinder.d2r(90)),
                                                new Waypoint(0, DIST_WALL_TO_SWITCH, Pathfinder.d2r(90))});


        CENTER_TO_LEFT_SWITCH = new Path(new Waypoint[]{CENTER_START,
                                                        new Waypoint(-DIST_CENTER_X_TO_SWITCH, DIST_WALL_TO_SWITCH,
                                                                     Pathfinder.d2r(90))});
        CENTER_TO_RIGHT_SWITCH = new Path(new Waypoint[]{CENTER_START,
                                                         new Waypoint(DIST_CENTER_X_TO_SWITCH, DIST_WALL_TO_SWITCH,
                                                                      Pathfinder.d2r(90))});

        SIDE_TO_OPP_SWITCH = new Path(new Waypoint[]{LEFT_START,
                                                     new Waypoint(LEFT_START_X + ROBOT_LENGTH, 21.8 - (ROBOT_WIDTH / 2),
                                                                  Pathfinder.d2r(30)),
                                                     new Waypoint(2, 19, Pathfinder.d2r(-30))});
    }

    public static Command getAutoRoutine(String position, String priorityOne, String priorityTwo, String gameString,
                                         double wait)
    {
        if((!Objects.equals(priorityOne, SCALE) && !Objects.equals(priorityOne, SWITCH)) ||
           (!Objects.equals(position, LEFT) && !Objects.equals(position, CENTER) && !Objects.equals(position, RIGHT)))
        {
            return new AutoGroup(wait, new TimedDrive());
        }
        else if(position.equals(CENTER))
        {
            if(gameCharToString(gameString.charAt(0)).equals(LEFT))
            {
                //                 run center left
                if(!priorityTwo.equals(SWITCH))
                {
                    return new AutoGroup(wait, new PlaceSwitchAuto(CENTER_TO_LEFT_SWITCH));
                }
                else
                {
                    return new AutoGroup(wait, new PlaceSwitchAuto(CENTER_TO_LEFT_SWITCH),
                                         new LowerAuto(SWITCH_TO_PILE_ONE, invertSide(LEFT)),
                                         new CollectAuto(SWITCH_TO_PILE_TWO, PILE_TO_SWITCH_ONE),
                                         new PlaceSwitchAuto(PILE_TO_SWITCH_TWO, invertSide(LEFT)));
                }
            }
            else
            {
                if(!priorityTwo.equals(SWITCH))
                {
                    return new AutoGroup(wait, new PlaceSwitchAuto(CENTER_TO_RIGHT_SWITCH));
                }
                else
                {
                    return new AutoGroup(wait, new PlaceSwitchAuto(CENTER_TO_RIGHT_SWITCH),
                                         new LowerAuto(SWITCH_TO_PILE_ONE, invertSide(RIGHT)),
                                         new CollectAuto(SWITCH_TO_PILE_TWO, PILE_TO_SWITCH_ONE),
                                         new PlaceSwitchAuto(PILE_TO_SWITCH_TWO, invertSide(RIGHT)));
                }
            }
        }
        else if(isTargetEasy(position, priorityOne, gameString))
        {
            if(priorityOne.equals(SWITCH))
            {
                //                if(priorityTwo.equals(SWITCH))
                //                {
                //                     position to switch to switch
                //                }
                //                else if(priorityTwo.equals(SCALE))
                //                {
                //                     position to switch to scale
                //                }
                //                else
                //                {
                return new AutoGroup(wait, new PlaceSwitchAuto(SIDE_TO_SWITCH, invertSide(position)));
                //                }
            }
            else if(priorityOne.equals(SCALE))
            {
                //                if(priorityTwo.equals(SWITCH))
                //                {
                // position to scale to switch
                //                }
                //                else if(priorityTwo.equals(SCALE))
                //                {
                // position to scale to scale
                //                }
                //                else
                //                {
                if(priorityTwo.equals(SCALE))
                {
                    return new AutoGroup(wait, new PlaceScaleAuto(SIDE_TO_SCALE, invertSide(position)),
                                         new LowerAuto(SCALE_TO_CUBE_ONE, invertSide(position)),
                                         new CollectAuto(SCALE_TO_CUBE_TWO, CUBE_TO_SCALE_ONE, invertSide(position)),
                                         new PlaceScaleAuto(CUBE_TO_SCALE_TWO, invertSide(position)));
                }
                return new AutoGroup(wait, new PlaceScaleAuto(SIDE_TO_SCALE, invertSide(position)));
                //                }
            }
        }
        else if(isTargetEasy(position, priorityTwo, gameString))
        {
            if(priorityTwo.equals(SWITCH))
            {
                return new AutoGroup(wait, new PlaceSwitchAuto(SIDE_TO_SWITCH, invertSide(position)));
            }
            else if(priorityTwo.equals(SCALE))
            {
                return new AutoGroup(wait, new PlaceScaleAuto(SIDE_TO_SCALE, invertSide(position)));
            }
        }
        else
        {
            if(priorityOne.equals(SWITCH))
            {
                //                if(priorityTwo.equals(SWITCH))
                //                {
                //                     position to switch to switch
                //                }
                //                else if(priorityTwo.equals(SCALE))
                //                {
                //                     position to switch to scale
                //                }
                //                else
                //                {
                return new AutoGroup(wait, new RunMP(SIDE_TO_OPP_SWITCH));
                //                }
            }
            else if(priorityOne.equals(SCALE))
            {
                //                if(priorityTwo.equals(SWITCH))
                //                {
                // position to scale to switch
                //                }
                //                else if(priorityTwo.equals(SCALE))
                //                {
                // position to scale to scale
                //                }
                //                else
                //                {

                //                }
            }
        }

        return new AutoGroup(wait, new TimedDrive());
    }

    private static boolean isTargetEasy(String position, String target, String gameString)
    {
        return position.equals(gameCharToString(gameString.charAt(priorityToCharPos(target))));
    }
}
