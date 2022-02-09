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
        TRIGGER_LEFT,
        TRIGGER_RIGHT,
        DPAD_UP,
        DPAD_RIGHT,
        DPAD_DOWN,
        DPAD_LEFT
    }

    private final double TRIGGER_THRESHOLD = 0.2;
    private HashMap<Button, edu.wpi.first.wpilibj2.command.button.Button> buttons = new HashMap<Button, edu.wpi.first.wpilibj2.command.button.Button>();

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
        buttons.put(Button.TRIGGER_LEFT, new AxisButton(this, Constants.INDEX_AXIS_TRIGGER_LEFT, TRIGGER_THRESHOLD));
        buttons.put(Button.TRIGGER_RIGHT, new AxisButton(this, Constants.INDEX_AXIS_TRIGGER_RIGHT, TRIGGER_THRESHOLD));
        buttons.put(Button.DPAD_UP, new PovButton(this, Pov.UP));
        buttons.put(Button.DPAD_RIGHT, new PovButton(this, Pov.RIGHT));
        buttons.put(Button.DPAD_DOWN, new PovButton(this, Pov.DOWN));
        buttons.put(Button.DPAD_LEFT, new PovButton(this, Pov.LEFT));
    }

    public edu.wpi.first.wpilibj2.command.button.Button getButton(Button button) {
        return buttons.get(button);
    }
}
