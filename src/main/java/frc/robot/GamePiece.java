// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

public enum GamePiece {
    RED_BALL(0), 
    BLUE_BALL(1), 
    SHOOTING_TARGET(0);

    private final int value;
    private GamePiece(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
