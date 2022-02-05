// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class BallPickup extends SubsystemBase {

  TalonSRX motor = new TalonSRX(0);

  /** Creates a new BallPickup. */
  public BallPickup() {
  }

  @Override
  public void periodic() {
  }

  public void set(double speed) {
    motor.set(ControlMode.PercentOutput, speed);
  }
}
