// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class StartShooter extends CommandBase {
  private final Shooter shooter;
  private final double targetSpeed;

  /** Creates a new StartShooter. */
  public StartShooter(Shooter shooter, double targetSpeed) {
    this.shooter = shooter;
    this.targetSpeed = targetSpeed;
    addRequirements(shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    shooter.shoot(this.targetSpeed);  }
}
