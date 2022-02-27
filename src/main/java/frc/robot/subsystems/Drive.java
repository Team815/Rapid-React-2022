// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;

import java.time.Duration;
import java.time.Instant;

public class Drive extends PIDSubsystem {

    final double ACCELERATION_MAX_SPEED = 3;
    final double ACCELERATION_MAX_ROTATION = 3;
    private NetworkTableEntry limelightX = NetworkTableInstance.getDefault().getTable("limelight-balls").getEntry("tx");
    private final DifferentialDrive drive;

    private double speed = 0;
    private double rotation = 0;
    private Instant previous = Instant.now();
    private double speedMultiplier = 1;
    private double rotationMultiplier = 1;
    private double pidRotation = 0;

    /**
     * Creates a new ExampleSubsystem.
     */
    public Drive() {
        super(new PIDController(-0.1, 0, 0));
        MotorControllerGroup leftGroup = new MotorControllerGroup(
                new CANSparkMax(1, MotorType.kBrushless),
                new CANSparkMax(2, MotorType.kBrushless),
                new CANSparkMax(3, MotorType.kBrushless)
        );
        MotorControllerGroup rightGroup = new MotorControllerGroup(
                new CANSparkMax(4, MotorType.kBrushless),
                new CANSparkMax(5, MotorType.kBrushless),
                new CANSparkMax(6, MotorType.kBrushless)
        );
        leftGroup.setInverted(true);
        drive = new DifferentialDrive(leftGroup, rightGroup);
        SmartDashboard.putNumber("Max Speed", speedMultiplier);
        SmartDashboard.putNumber("Max Rotation", rotationMultiplier);
    }

    private static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    @Override
    public void periodic() {
        super.periodic();
        setSpeedMultiplier(SmartDashboard.getNumber("Max Speed", 1));
        setRotationMultiplier(SmartDashboard.getNumber("Max Rotation", 1));
    }

    public void driveTelop(double speed, double rotation) {
        speed *= speedMultiplier;
        rotation *= rotationMultiplier;
        drive(speed, rotation);
    }

    public void drive(double speedIn, double rotationIn) {
        var now = Instant.now();
        var delta = Duration.between(previous, now).toNanos() * 1e-9;
        speed = updateValue(speed, speedIn, delta * ACCELERATION_MAX_SPEED);
        rotation = updateValue(rotation, rotationIn, delta * ACCELERATION_MAX_ROTATION);
        drive.arcadeDrive(speed, rotation);
        previous = now;
    }

    private double updateValue(double value, double target, double maxChange) {
        return target > value
                ? Math.min(target, value + maxChange)
                : Math.max(target, value - maxChange);
    }

    public void setSpeedMultiplier(double speedMultiplier) {
        this.speedMultiplier = clamp(speedMultiplier, 0, 1);
        SmartDashboard.putNumber("Max Speed", this.speedMultiplier);
    }

    public void setRotationMultiplier(double rotationMultiplier) {
        this.rotationMultiplier = clamp(rotationMultiplier, 0, 1);
        SmartDashboard.putNumber("Max Rotation", this.rotationMultiplier);
    }

    @Override
    protected void useOutput(double output, double setpoint) {
        pidRotation = Math.min(0.3, Math.abs(output)) * Math.signum(output);
    }

    @Override
    public double getMeasurement() {
        var measurement = limelightX.getDouble(0);
        System.out.println(measurement);
        return measurement;
    }

    public double getPidRotation() {
        return pidRotation;
    }

    public void setEntry(NetworkTableEntry entry) {
        limelightX = entry;
    }
}
