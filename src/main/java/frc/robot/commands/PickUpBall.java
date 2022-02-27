// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Pickup;
import frc.robot.subsystems.Storage;

import java.time.Duration;
import java.time.Instant;

public class PickUpBall extends CommandBase {

    Drive drive;
    Limelight limelight;
    Pickup pickup;
    Storage storage;
    Instant start;
    int ballOutOfSightFrames;

    /**
     * Creates a new PickUpBall.
     */
    public PickUpBall(Drive drive, Pickup pickup, Storage storage, Limelight limelight) {
        this.drive = drive;
        this.pickup = pickup;
        this.storage = storage;
        this.limelight = limelight;

        addRequirements(drive, pickup, storage, limelight);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        pickup.set(0.4);
        storage.set(0.4);
        drive.enable();
        start = Instant.now();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        drive.drive(.5, drive.getPidRotation());
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        pickup.set(0);
        storage.set(0);
        drive.disable();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        ballOutOfSightFrames = limelight.bottomLimelight.seesTarget() ? 0 : ballOutOfSightFrames + 1;
        //return ballOutOfSightFrames >= 10;
        return Duration.between(start, Instant.now()).toMillis() >= 1700;
    }
}
