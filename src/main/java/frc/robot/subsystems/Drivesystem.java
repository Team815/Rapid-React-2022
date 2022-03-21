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
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.time.Duration;
import java.time.Instant;

public class Drivesystem extends SubsystemBase {

    double accelerationMaxSpeed = 3;
    double accelerationMaxRotation = 3;
    private NetworkTableEntry limelightX = NetworkTableInstance.getDefault().getTable("limelight-balls").getEntry("tx");
    private NetworkTableEntry limelightY = NetworkTableInstance.getDefault().getTable("limelight-balls").getEntry("ty");
    private final DifferentialDrive drive;

    private double speed = 0;
    private double rotation = 0;
    private Instant previous = Instant.now();
    private double speedMultiplier = .8;
    private double rotationMultiplier = .75;
    private final PIDController pidControllerRotation;
    private final PIDController pidControllerDistance;

    /**
     * Creates a new ExampleSubsystem.
     */
    public Drivesystem() {
        pidControllerRotation = new PIDController(-0.15, 0, 0);
        pidControllerDistance = new PIDController(-0.15, 0, 0);
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
        accelerationMaxSpeed = SmartDashboard.getNumber("Max Acceleration Speed", 3);
        accelerationMaxRotation = SmartDashboard.getNumber("Max Acceleration Rotation", 3);
        SmartDashboard.putNumber("Max Acceleration Speed", accelerationMaxSpeed);
        SmartDashboard.putNumber("Max Acceleration Rotation", accelerationMaxRotation);
    }

    public void drive(double speedIn, double rotationIn) {
        var now = Instant.now();
        var delta = Duration.between(previous, now).toNanos() * 1e-9;
        speed = updateValue(speed, speedIn, delta * accelerationMaxSpeed);
        rotation = updateValue(rotation, rotationIn, delta * accelerationMaxRotation);
        drive.arcadeDrive(speed, rotation);
        previous = now;
    }

    private double updateValue(double value, double target, double maxChange) {
        return target > value
                ? Math.min(target, value + maxChange)
                : Math.max(target, value - maxChange);
    }

    public double getSpeedMultiplier() {
        return speedMultiplier;
    }

    public double getRotationMultiplier() {
        return rotationMultiplier;
    }

    public void setSpeedMultiplier(double speedMultiplier) {
        this.speedMultiplier = clamp(speedMultiplier, 0, 1);
        SmartDashboard.putNumber("Max Speed", this.speedMultiplier);
    }

    public void setRotationMultiplier(double rotationMultiplier) {
        this.rotationMultiplier = clamp(rotationMultiplier, 0, 1);
        SmartDashboard.putNumber("Max Rotation", this.rotationMultiplier);
    }

    public double getPidRotation() {
        var measurement = limelightX.getDouble(0);
        var output = pidControllerRotation.calculate(measurement);
        return Math.min(0.35, Math.abs(output)) * Math.signum(output);
    }

    public double getPidDistance(){
        var measurement = limelightY.getDouble(0);
        var output = pidControllerDistance.calculate(measurement);
        return Math.min(0.35, Math.abs(output)) * Math.signum(output);
    }


    public void setEntry(NetworkTableEntry entry) {
        limelightX = entry;
    }
}
