package frc.team3238.Subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team3238.RobotMap;

public class Chassis extends Subsystem
{
    TalonSRX talonLeft1, talonLeft2, talonRight1, talonRight2;

    public Chassis()
    {
        talonLeft1 = new TalonSRX(RobotMap.TALON_LEFT_1);
        talonLeft2 = new TalonSRX(RobotMap.TALON_lEFT_2);
        talonRight1 = new TalonSRX(RobotMap.TALON_RIGHT_1);
        talonRight2 = new TalonSRX(RobotMap.TALON_RIGHT_2);
    }

    public void initDefaultCommand()
    {

    }
}

