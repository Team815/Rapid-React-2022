// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Storage extends SubsystemBase {

    private final TalonSRX motor;

    /**
     * Creates a new Storage.
     */
    public Storage(int motorindex) {
        motor = new TalonSRX(motorindex);
        motor.setInverted(true);
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }

    public void set(double speed) {
        motor.set(ControlMode.PercentOutput, speed);
    }
}
