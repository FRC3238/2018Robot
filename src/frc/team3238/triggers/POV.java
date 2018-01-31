package frc.team3238.triggers;

import com.sun.istack.internal.NotNull;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.command.Command;

public class POV
{

    public POV(@NotNull Joystick joystick, @NotNull Command up, @NotNull Command down, @NotNull Command left,
               @NotNull Command right)
    {

        Button upPOV = new POVButton(joystick, 0);
        Button upRightPOV = new POVButton(joystick, 45);
        Button rightPOV = new POVButton(joystick, 90);
        Button downRightPOV = new POVButton(joystick, 135);
        Button downPOV = new POVButton(joystick, 180);
        Button downLeftPOV = new POVButton(joystick, 225);
        Button leftPOV = new POVButton(joystick, 270);
        Button upLeftPOV = new POVButton(joystick, 315);

        upPOV.whileHeld(up);

        upRightPOV.whileHeld(up);
        upRightPOV.whileHeld(right);

        rightPOV.whileHeld(right);

        downRightPOV.whileHeld(down);
        downRightPOV.whileHeld(right);

        downPOV.whileHeld(down);

        downLeftPOV.whileHeld(down);
        downLeftPOV.whileHeld(left);

        leftPOV.whileHeld(left);

        upLeftPOV.whileHeld(up);
        upLeftPOV.whileHeld(left);
    }
}
