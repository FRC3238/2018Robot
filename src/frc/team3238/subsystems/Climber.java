package frc.team3238.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;

import static frc.team3238.RobotMap.Climber.CLIMB_SLAVE_TALON_ID;
import static frc.team3238.RobotMap.Climber.CLIMB_TALON_ID;

public class Climber extends Subsystem
{
    private TalonSRX climb, slave;

    public Climber()
    {
        super("Climber");

        climb = new TalonSRX(CLIMB_TALON_ID);
        slave = new TalonSRX(CLIMB_SLAVE_TALON_ID);

        slave.follow(climb);

        // TODO: set inverted if needed
    }

    public void set(double power)
    {
        climb.set(ControlMode.PercentOutput, power);
    }

    public void initDefaultCommand()
    {

    }
}

