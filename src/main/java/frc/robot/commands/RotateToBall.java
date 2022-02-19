// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;

/**
 * An example command that uses an example subsystem.
 */
public class RotateToBall extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Drive drive;

    /**
     * Creates a new ExampleCommand.
     *
     * @param drive The drive used by this command.
     */
    public RotateToBall(Drive drive) {
        this.drive = drive;
        addRequirements(drive);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        drive.enable();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        drive.disable();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        double pidMeasurement = drive.getMeasurement() - 8;
        System.out.println("PID Measurement: " + pidMeasurement);
        boolean isFinished = Math.abs(pidMeasurement) < 0.1;
        if (isFinished)
            System.out.println("Ready to drive");
        return isFinished;
        //return false;
    }
}
