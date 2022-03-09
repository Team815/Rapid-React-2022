// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Climber extends SubsystemBase {

  private final CANSparkMax climber;

  /** Creates a new Climber. */
  public Climber(int climberIndex) {
    climber = new CANSparkMax(climberIndex, MotorType.kBrushless);
  }

  public void moveUp() {
    climber.set(1.0);
  }

  public void moveDown() {
    climber.set(-1.0);
  }

  public void disable() {
    climber.set(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
