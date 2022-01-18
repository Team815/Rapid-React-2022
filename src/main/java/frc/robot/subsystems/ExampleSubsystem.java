// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ExampleSubsystem extends SubsystemBase {

  TalonSRX motor1 = new TalonSRX(1);
  TalonSRX motor2 = new TalonSRX(2);

  /** Creates a new ExampleSubsystem. */
  public ExampleSubsystem() {
    motor1.set(ControlMode.PercentOutput, 20);
    motor2.set(ControlMode.PercentOutput, 20);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
