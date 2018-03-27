package frc.team3238;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3238.autonomous.Paths;
import frc.team3238.subsystems.*;

import static frc.team3238.RobotMap.Auto.POSITIONS;
import static frc.team3238.RobotMap.Auto.PRIORITIES;
import static frc.team3238.RobotMap.Global.*;

public class Robot extends TimedRobot
{
    public static OI oi;

    public static Chassis chassis = new Chassis();
    public static Collector collector = new Collector();
    public static Extender extender = new Extender();
    public static Lift lift = new Lift();
    public static Climber climber = new Climber();

    private SendableChooser<Integer> posChooser;
    private SendableChooser<Integer> priorityOneChooser;
    private SendableChooser<Integer> priorityTwoChooser;

    private Command autoCommand;

    @Override
    public void robotInit()
    {
        Paths.calcPaths();
        setPeriod(ROBOT_PERIOD);

        UsbCamera camera = CameraServer.getInstance().startAutomaticCapture("Camera", 0);
        camera.setResolution(CAMERA_X_RES, CAMERA_Y_RES);
        //        camera.setFPS(CAMERA_FPS);

        posChooser = new SendableChooser<>();
        priorityOneChooser = new SendableChooser<>();
        priorityTwoChooser = new SendableChooser<>();

        sendAutoOptions(POSITIONS, posChooser);
        sendAutoOptions(PRIORITIES, priorityOneChooser);
        sendAutoOptions(PRIORITIES, priorityTwoChooser);
        if(SmartDashboard.getNumber("Auto Wait", 0) == 0)
        {
            SmartDashboard.putNumber("Auto Wait", 0);
        }

        SmartDashboard.putData("Position", posChooser);
        SmartDashboard.putData("Priority One", priorityOneChooser);
        SmartDashboard.putData("Priority Two", priorityTwoChooser);

        lift.resetEncoder();

        oi = new OI();

        SmartDashboard.putData(new PowerDistributionPanel());
    }

    private void sendAutoOptions(String[] options, SendableChooser chooser)
    {
        for(int i = 0; i < options.length; i++)
        {
            if(i == 0)
            {
                chooser.addDefault(options[i], i);
            }
            else
            {
                chooser.addObject(options[i], i);
            }
        }
    }

    @Override
    public void robotPeriodic()
    {
        SmartDashboard.putData(Scheduler.getInstance());
    }

    @Override
    public void disabledInit()
    {
        chassis.setCoastMode();
    }

    @Override
    public void disabledPeriodic()
    {
        Scheduler.getInstance().run();
    }

    @Override
    public void autonomousInit()
    {
        //        int timeout = 100;
        String gameMessage;
        //        do
        //        {
        gameMessage = DriverStation.getInstance().getGameSpecificMessage();
        //            timeout--;
        //        } while(Objects.equals(gameMessage, "") && timeout > 0);
        autoCommand =
                Paths.getAutoRoutine(POSITIONS[posChooser.getSelected()], PRIORITIES[priorityOneChooser.getSelected()],
                                     PRIORITIES[priorityTwoChooser.getSelected()], gameMessage,
                                     SmartDashboard.getNumber("Auto Wait", 0));
        DriverStation.reportWarning("Starting command " + autoCommand.getName(), false);
        autoCommand.start();
        //        time = Timer.getFPGATimestamp();
    }

    //    public double time;
    @Override
    public void autonomousPeriodic()
    {
        //        DriverStation.reportWarning("In auto periodic");
        Scheduler.getInstance().run();
        //        if(Timer.getFPGATimestamp() - time < 6)
        //        {
        //            chassis.drive(0.2, 0);
        //        }
        //        else
        //        {
        //            chassis.drive(0, 0);
        //        }
    }

    @Override()
    public void teleopInit()
    {
        if(autoCommand != null && autoCommand.isRunning())
        {
            autoCommand.cancel();
        }
    }

    @Override
    public void teleopPeriodic()
    {
        Scheduler.getInstance().run();
    }

    @Override
    public void testPeriodic()
    {

    }
}
