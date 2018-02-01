package frc.team3238;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3238.autonomous.Paths;
import frc.team3238.commands.chassis.Drive;
import frc.team3238.commands.collector.Collect;
import frc.team3238.commands.collector.Eject;
import frc.team3238.commands.extender.Extend;
import frc.team3238.commands.extender.Withdraw;
import frc.team3238.subsystems.Chassis;
import frc.team3238.subsystems.Collector;
import frc.team3238.subsystems.Extender;

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

    private SendableChooser<Integer> posChooser;
    private SendableChooser<Integer> priorityOneChooser;
    private SendableChooser<Integer> priorityTwoChooser;

    private Command autoCommand;

    @Override
    public void robotInit()
    {
        setPeriod(ROBOT_PERIOD);

        UsbCamera camera = CameraServer.getInstance().startAutomaticCapture("Camera", 0);
        camera.setResolution(CAMERA_X_RES, CAMERA_Y_RES);
        camera.setFPS(CAMERA_FPS);

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

        SmartDashboard.putData(Scheduler.getInstance());
        SmartDashboard.putData(new PowerDistributionPanel());

        LiveWindow.add(chassis);
        LiveWindow.add(collector);
        LiveWindow.add(extender);
        LiveWindow.add(chassis);

        LiveWindow.add(new Collect());
        LiveWindow.add(new Drive());
        LiveWindow.add(new Eject());
        LiveWindow.add(new Withdraw());
        LiveWindow.add(new Extend());
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
        // TODO: delete this after testing
        SmartDashboard.putNumber("Throttle Mult", oi.getThrottleMult());
    }

    @Override
    public void disabledInit()
    {

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
        if(autoCommand != null)
        {
            autoCommand.start();
        }
    }

    @Override
    public void autonomousPeriodic()
    {
        Scheduler.getInstance().run();
    }

    @Override
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
