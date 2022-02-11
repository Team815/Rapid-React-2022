// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.Button;

enum Pov {
    UP(0),
    UP_RIGHT(45),
    RIGHT(90),
    DOWN_RIGHT(135),
    DOWN(180),
    DOWN_LEFT(225),
    LEFT(270),
    UP_LEFT(315);

    private final int angle;

    Pov(int angle) {
        this.angle = angle;
    }

    public int getAngle() {
        return angle;
    }
}

/**
 * Add your docs here.
 */
public class PovButton extends Button {
    public PovButton(XboxController controller, Pov pov) {
        super(() -> controller.getPOV() == pov.getAngle());
    }
}
