// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;

/** Add your docs here. */
public class TalonSrxAdapter implements MotorController {
    private final TalonSRX motor;

    public TalonSrxAdapter(TalonSRX motor) {
        this.motor = motor;
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
        set(0);
    }

    @Override
    public void stopMotor() {
        set(0);
    }
}
