// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;

/** Add your docs here. */
public class SpeedControllerSRX implements MotorController {

    TalonSRX motor;

    public SpeedControllerSRX(int id) {
        motor = new TalonSRX(id);
    }

    @Override
    public void set(double speed) {
        motor.set(ControlMode.PercentOutput, speed);
    }

    @Override
    public double get() {
        return motor.getMotorOutputPercent();
    }

    @Override
    public void setInverted(boolean isInverted) {
        motor.setInverted(isInverted);
    }

    @Override
    public boolean getInverted() {
        return motor.getInverted();
    }

    @Override
    public void disable() {
       this.set(0);
    }

    @Override
    public void stopMotor() {
        this.set(0);
    }
}
