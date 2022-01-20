// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ExampleSubsystem extends SubsystemBase {

  DifferentialDrive drive;
  final double LIMIT = 0.7;

  /** Creates a new ExampleSubsystem. */
  public ExampleSubsystem() {
    SpeedControllerSRX motor1 = new SpeedControllerSRX(1);
    SpeedControllerSRX motor2 = new SpeedControllerSRX(2);
    motor2.setInverted(true);
    drive = new DifferentialDrive(motor1, motor2);
  }

  public void drive(double speed, double rotation) {
    speed += 0.4 * Math.signum(speed);
    speed = Math.min(speed, LIMIT);
    speed = Math.max(speed, -LIMIT);
    drive.arcadeDrive(speed, rotation);
  }

  @Override
  public void periodic() {
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
