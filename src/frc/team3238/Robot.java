package frc.team3238;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3238.autonomous.Paths;
import frc.team3238.subsystems.Chassis;
import frc.team3238.subsystems.Climber;
import frc.team3238.subsystems.Collector;
import frc.team3238.subsystems.Extender;
import frc.team3238.subsystems.Lift;
import frc.team3238.utils.DriverConfig;

import static frc.team3238.RobotMap.Auto.POSITIONS;
import static frc.team3238.RobotMap.Auto.PRIORITIES;
import static frc.team3238.RobotMap.Global.CAMERA_FPS;
import static frc.team3238.RobotMap.Global.CAMERA_X_RES;
import static frc.team3238.RobotMap.Global.CAMERA_Y_RES;
import static frc.team3238.RobotMap.Global.ROBOT_PERIOD;


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
    // TODO: remove before competition
    private SendableChooser<DriverConfig> driverChooser;

    private Command autoCommand;

    @Override
    public void robotInit()
    {
        setPeriod(ROBOT_PERIOD);

        UsbCamera camera = CameraServer.getInstance().startAutomaticCapture("Camera", 0);
        camera.setResolution(CAMERA_X_RES, CAMERA_Y_RES);
        //        camera.setFPS(CAMERA_FPS);

        oi = new OI();

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

        driverChooser = new SendableChooser<>();
        sendDriverOptions(DriverConfig.configs, driverChooser);
        SmartDashboard.putData("Driver Selection", driverChooser);

        lift.resetEncoder();

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

    private void sendDriverOptions(DriverConfig[] configs, SendableChooser chooser)
    {
        for(int i = 0; i < configs.length; i++)
        {
            if(i == 0)
            {
                chooser.addDefault(configs[i].getClass().getSimpleName(), configs[i]);
            }
            else
            {
                chooser.addObject(configs[i].getClass().getSimpleName(), configs[i]);
            }
        }
    }

    @Override
    public void robotPeriodic()
    {
        // TODO: delete this after testing
        SmartDashboard.putNumber("Throttle Mult", oi.getThrottleMult());
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
        autoCommand =
                Paths.getAutoRoutine(POSITIONS[posChooser.getSelected()], PRIORITIES[priorityOneChooser.getSelected()],
                                     PRIORITIES[priorityTwoChooser.getSelected()],
                                     DriverStation.getInstance().getGameSpecificMessage(),
                                     SmartDashboard.getNumber("Auto Wait", 0));
        DriverStation.reportWarning("Starting command " + autoCommand.getName(), false);
        autoCommand.start();
    }

    @Override
    public void autonomousPeriodic()
    {
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopInit()
    {
        oi.setDriver(driverChooser.getSelected());

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

    Joystick stick = new Joystick(0);

    @Override
    public void testPeriodic()
    {
        SmartDashboard.putNumber("Chassis feed forward", chassis.calcFeedForward(stick.getY()));
    }
}
