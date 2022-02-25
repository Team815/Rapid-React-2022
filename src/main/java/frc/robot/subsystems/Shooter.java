// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.TalonSrxAdapter;

import java.util.function.DoubleSupplier;

public class Shooter extends SubsystemBase {

  private final MotorControllerGroup motors;
  private final DoubleSupplier speedSupplier;
  private double speed;

  /** Creates a new Shooter. */
  public Shooter(int motor1Index, int motor2Index) {
    var motor1 = new TalonSRX(motor1Index);
    var motor2 = new TalonSRX(motor2Index);
    motor2.setInverted(true);
    speedSupplier = () -> -motor2.getSelectedSensorVelocity();
    motors = new MotorControllerGroup(
            new TalonSrxAdapter(motor1),
            new TalonSrxAdapter(motor2));
  }

  public void shoot() {
    motors.set(speed);
  }

  public void stop() {
    motors.set(0);
  }

  public boolean atSpeed() {
    final int speedThreshold = 24000;
    return speedSupplier.getAsDouble() >= speedThreshold;
  }

  @Override
  public void periodic() {
    speed = SmartDashboard.getNumber("Shooter Speed", 0);
    SmartDashboard.putNumber("Shooter Speed", speed);
  }
}
