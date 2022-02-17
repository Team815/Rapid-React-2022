// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Pickup;

import java.time.Instant;

public class PickUpBall extends CommandBase {

    private final NetworkTableEntry limelightv = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv");
    Drive drive;
    Pickup pickup;
    Instant start;
    int ballOutOfSightFrames;

    /**
     * Creates a new PickUpBall.
     */
    public PickUpBall(Drive drive, Pickup pickup) {
        this.drive = drive;
        this.pickup = pickup;

        addRequirements(drive, pickup);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        pickup.set(0.3);
        start = Instant.now();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        drive.drive(.4, 0);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        pickup.set(0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        boolean isVisible = limelightv.getBoolean(false);
        ballOutOfSightFrames = isVisible ? 0 : ballOutOfSightFrames + 1;
        return ballOutOfSightFrames >= 100;
    }
}
