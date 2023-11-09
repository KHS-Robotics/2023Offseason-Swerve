// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.drive.CenterSwerveModules;
import frc.robot.commands.drive.DriveSwerveWithXbox;
import frc.robot.commands.drive.HoldAngleWithXbox;
import frc.robot.subsystems.drive.SwerveDrive;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  private static RobotContainer instance;

  public static RobotContainer getInstance() {
    if (instance == null) {
      instance = new RobotContainer();
    }
    return instance;
  }

  public static final AHRS navx = new AHRS(Port.kUSB);

  /**
   * Returns the angle or "yaw" of the robot in degrees. CW positive ranging from
   * [-180, 180].
   */
  public static double getRobotYaw() {
    return navx.getYaw();
  }

  /**
   * Returns the pitch angle of the robot in degrees. This tracks the
   * forwards/backwards tilt of the robot.
   */
  public static double getRobotPitch() {
    return navx.getRoll();
  }

  public static double getRobotRoll() {
    return -navx.getPitch();
  }

  public static final Field2d field = new Field2d();
  public static final PowerDistribution pdp = new PowerDistribution();

  // Human Interface Devices (HIDs)
  public static final CommandXboxController driverController = new CommandXboxController(RobotMap.XBOX_PORT);

  // Subsystems
  public static final SwerveDrive swerveDrive = new SwerveDrive();

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  private RobotContainer() {
    this.configureSubsystemDefaultCommands();
    this.configureBindings();
  }

  /** Configures the subsystem's default commands. */
  private void configureSubsystemDefaultCommands() {
    swerveDrive.setDefaultCommand(new DriveSwerveWithXbox());
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be
   * created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with
   * an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for
   * {@link
   * CommandXboxController
   * Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or
   * {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    this.configureAutomatedBindings();
    this.configureXboxControllerBindings();
    this.configureOperatorBoxBindings();
    this.configureOperatorStickBindings();
  }

  /** Automated bindings that happen without pressing any buttons. */
  private void configureAutomatedBindings() {
    Trigger autoCalibrateTeleop = new Trigger(
        () -> (!swerveDrive.isCalibrated && RobotState.isTeleop() && RobotState.isEnabled()));
    autoCalibrateTeleop.onTrue(new CenterSwerveModules(true));
  }

  /** Binds commands to xbox controller buttons. */
  private void configureXboxControllerBindings() {
    Trigger forceCalibrate = driverController.back();
    forceCalibrate.onTrue(new CenterSwerveModules(true));

    //Trigger resetNavx = driverController.start();
    //resetNavx.onTrue(new InstantCommand(() -> swerveDrive.resetNavx()));

    
    Trigger resetOdometry = driverController.start();
    resetOdometry.onTrue(new InstantCommand(() -> swerveDrive.resetOdometry()));

    Trigger slowDrive = driverController.leftTrigger(0.3);
    slowDrive.onTrue(new InstantCommand(() -> {
      SwerveDrive.kMaxAngularSpeedRadiansPerSecond = 0.75;
      SwerveDrive.kMaxSpeedMetersPerSecond = Math.PI / 2.0;
    }));
    slowDrive.onFalse(new InstantCommand(() -> {
      SwerveDrive.kMaxAngularSpeedRadiansPerSecond = 2 * Math.PI;
      SwerveDrive.kMaxSpeedMetersPerSecond = 3.5;
    }));


    Trigger holdAngleWhileDriving = driverController.rightBumper();
    holdAngleWhileDriving.whileTrue(new HoldAngleWithXbox());

    Trigger cancelAll = driverController.leftBumper();
    cancelAll.onTrue(new InstantCommand(() -> CommandScheduler.getInstance().cancelAll()));
  }

  /** Binds commands to the operator box. */
  private void configureOperatorBoxBindings() {

  }

  /** Binds commands to the operator stick. */
  private void configureOperatorStickBindings() {
  
  }
}
