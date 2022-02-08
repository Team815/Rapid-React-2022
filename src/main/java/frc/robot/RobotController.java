// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.HashMap;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/** Add your docs here. */
public class RobotController extends XboxController {

    enum Button {
        A,
        B,
        X,
        Y,
        BUMPER_LEFT,
        BUMPER_RIGHT,
        SELECT,
        START,
        JOYSTICK_LEFT,
        JOYSTICK_RIGHT,
    }

    private HashMap<Button, JoystickButton> buttons = new HashMap<Button, JoystickButton>();

    public RobotController(int port) {
        super(port);

        buttons.put(Button.A, new JoystickButton(this, Constants.INDEX_BUTTON_A));
        buttons.put(Button.B, new JoystickButton(this, Constants.INDEX_BUTTON_B));
        buttons.put(Button.X, new JoystickButton(this, Constants.INDEX_BUTTON_X));
        buttons.put(Button.Y, new JoystickButton(this, Constants.INDEX_BUTTON_Y));
        buttons.put(Button.BUMPER_LEFT, new JoystickButton(this, Constants.INDEX_BUTTON_BUMPER_LEFT));
        buttons.put(Button.BUMPER_RIGHT, new JoystickButton(this, Constants.INDEX_BUTTON_BUMPER_RIGHT));
        buttons.put(Button.SELECT, new JoystickButton(this, Constants.INDEX_BUTTON_SELECT));
        buttons.put(Button.START, new JoystickButton(this, Constants.INDEX_BUTTON_START)); 
        buttons.put(Button.JOYSTICK_LEFT, new JoystickButton(this, Constants.INDEX_BUTTON_JOYSTICK_LEFT));
        buttons.put(Button.JOYSTICK_RIGHT, new JoystickButton(this, Constants.INDEX_BUTTON_JOYSTICK_RIGHT));
    }

    public JoystickButton getButton(Button button) {
        return buttons.get(button);
    }
}
