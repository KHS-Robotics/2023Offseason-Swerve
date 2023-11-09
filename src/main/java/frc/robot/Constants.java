/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;

/**
 * constants
 */
public class Constants {
  public static final Transform3d CAMERA_1_POS = new Transform3d(
      new Translation3d(Units.inchesToMeters(14), Units.inchesToMeters(-5.5), Units.inchesToMeters(9.75)),
      new Rotation3d(Math.toRadians(0), Math.toRadians(-15), 0));
  public static final Transform3d LIMELIGHT_POS = new Transform3d(
      new Translation3d(Units.inchesToMeters(0), Units.inchesToMeters(0), Units.inchesToMeters(0)),
      new Rotation3d(Math.toRadians(0), Math.toRadians(0), 0));
  
  public static final double SENS = 0.5;

  public static final double DRIVE_VEL_ENCODER = 0.000637;
  public static final double DRIVE_POS_ENCODER = 0.038318;

  public static final double PIVOT_P = 0.01;
  public static final double PIVOT_I = 0.0;
  public static final double PIVOT_D = 0.0001;

  public static final double DRIVE_P = 0.01;
  public static final double DRIVE_I = 0;
  public static final double DRIVE_D = 0;
  public static final double DRIVE_KS = 0.11408;
  public static final double DRIVE_KV = 3.2717;
  public static final double DRIVE_KA = 0.17904;

  public static final double TARGET_P = 0.008;
  public static final double TARGET_I = 0.0;
  public static final double TARGET_D = 0.001;

  public static final int LED_LENGTH = 20;
}