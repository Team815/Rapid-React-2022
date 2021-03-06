// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.networktables.NetworkTableEntry;
import frc.robot.subsystems.Drivesystem;

/**
 * An example command that uses an example subsystem.
 */
public class TrackTarget extends Drive {
    private final NetworkTableEntry entry;

    /**
     * Creates a new ExampleCommand.
     *
     * @param drivesystem The drive used by this command.
     */
    public TrackTarget(Drivesystem drivesystem, DoubleSupplier speedSupplier, NetworkTableEntry entry) {
        super(drivesystem, speedSupplier, drivesystem::getPidRotation);
        this.entry = entry;
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        drivesystem.setEntry(entry);
    }
}
