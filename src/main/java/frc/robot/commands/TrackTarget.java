// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;

/**
 * An example command that uses an example subsystem.
 */
public class TrackTarget extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    protected final Drive drive;
    private final DoubleSupplier speedSupplier;
    private final NetworkTableEntry entry;

    /**
     * Creates a new ExampleCommand.
     *
     * @param drive The drive used by this command.
     */
    public TrackTarget(Drive drive, DoubleSupplier speedSupplier, NetworkTableEntry entry) {
        this.drive = drive;
        this.speedSupplier = speedSupplier;
        this.entry = entry;
        addRequirements(drive);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        drive.setEntry(entry);
        drive.enable();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        drive.drive(speedSupplier.getAsDouble(), drive.getPidRotation());
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        drive.disable();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
