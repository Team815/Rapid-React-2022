// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
  private final TalonSRX feeder;
  /** Creates a new Shooter. */
  public Shooter(int feederIndex) {
    feeder = new TalonSRX(feederIndex);
  }

  public void shoot() {
    feeder.set(ControlMode.PercentOutput, 0.3);
  }

  public void stop() {
    feeder.set(ControlMode.PercentOutput, 0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
