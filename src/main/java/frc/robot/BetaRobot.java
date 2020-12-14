/*----------------------------------------------------------------------------*/

/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */

/* Open Source Software - may be modified and shared by FRC teams. The code   */

/* must be accompanied by the FIRST BSD license file in the root directory of */

/* the project.                                                               */

/*----------------------------------------------------------------------------*/
package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */

public class Robot extends TimedRobot {

  public static final int can1 = 1;
  public static final int can2 = 2;
  public static final int can3 = 3;
  public static final int can4 = 4;
  public static final int can5 = 5; //This is the left shooter motor
  public static final int can6 = 6; //This is the right shooter motor
  public static final int buttonX = 0;
  public static final int buttonA = 1;
  public static final int buttonB = 2;
  public static final int buttonY = 3;
  public static final int buttonLT = 7;
  public static final int buttonRT = 8;
  public static final int buttonStart = 10;
  public static final int buttonBack = 9;
  public static final int buttonTopLeft = 100; //Please remap the integer value to the correct one. Solenoid
  public static final int buttonTopRight = 200; //Please remap the integer value to the correct one. Solenoid
  public static final int can7 = 7; //Elevator initialization
  public static final int can8 = 8; //Hook Initialization (1)
  public static final int can9 = 9; //Hook Initialization (2)
  public static final int can10 = 10; //Control Panel Manipulation

 
  Solenoid solenoid1 = new Solenoid(0);
  Joystick joy1 = new Joystick(0); //inputs for Joystick
  JoystickButton LowPowerThrow = new JoystickButton(joy1, buttonX); 
  JoystickButton MediumPowerThrow = new JoystickButton(joy1, buttonA);
  JoystickButton MediumHighPowerThrow = new JoystickButton(joy1, buttonB);
  JoystickButton HighPowerThrow = new JoystickButton(joy1, buttonY);
  WPI_VictorSPX leftFrontMotor = new WPI_VictorSPX(can1);
  WPI_VictorSPX rightFrontMotor = new WPI_VictorSPX(can3);
  WPI_VictorSPX leftRearMotor = new WPI_VictorSPX(can2);
  WPI_VictorSPX rightRearMotor = new WPI_VictorSPX(can4);
  WPI_VictorSPX shooter_leftmotor = new WPI_VictorSPX(can5);
  WPI_VictorSPX shooter_rightmotor = new WPI_VictorSPX(can6);
  WPI_VictorSPX elevator = new WPI_VictorSPX(can7);
  WPI_VictorSPX hookmotor1 = new WPI_VictorSPX(can8);
  WPI_VictorSPX hookmotor2 = new WPI_VictorSPX(can9);
  WPI_VictorSPX controlpanelmotor = new WPI_VictorSPX(can10);
  DifferentialDrive _drive = new DifferentialDrive(leftFrontMotor, rightFrontMotor);
  double powerLevel = 0.0;

  //Measurements Values
  double fbSpeed; //front back speed
  double turnSpeed; //turning speed
  double fbSpeedr; //front back speed
  double turnSpeedr; //turning speed
  double elevatorval; //Up/Down of elevator
  double hookmotorval; 
  double controlpanelmotorvalue;

  Timer timeCount;

    /**
     * This function is run when the robot is first started up and should be used
     * for any initialization code.
     */

  @Override
  public void robotInit() {

  }

  @Override
  public void autonomousInit() {

    timeCount = new Timer();
    timeCount.reset();
    timeCount.start();

  }



  @Override
  public void autonomousPeriodic() {

    if (timeCount.get() < 3.0) {
      final double stick1 = 0.5;
      double leftMotorPower = stick1;
      double rightMotorPower = stick1 * -1;
      leftFrontMotor.set(ControlMode.PercentOutput, leftMotorPower);
      leftRearMotor.set(ControlMode.PercentOutput, leftMotorPower);
      rightFrontMotor.set(ControlMode.PercentOutput, rightMotorPower);
      rightRearMotor.set(ControlMode.PercentOutput, rightMotorPower);
    }
    else 
    {
      final double stick1 = 0.0;
      double leftMotorPower = stick1;
      double rightMotorPower = stick1 * -1;
      leftFrontMotor.set(ControlMode.PercentOutput, leftMotorPower);
      leftRearMotor.set(ControlMode.PercentOutput, leftMotorPower);
      rightFrontMotor.set(ControlMode.PercentOutput, rightMotorPower);
      rightRearMotor.set(ControlMode.PercentOutput, rightMotorPower); 
    }
  }



  @Override
  public void teleopInit() {
  /* factory default values */
  leftFrontMotor.configFactoryDefault();
  rightFrontMotor.configFactoryDefault();
  elevator.configFactoryDefault();
  hookmotor1.configFactoryDefault();
  hookmotor2.configFactoryDefault();
  controlpanelmotor.configFactoryDefault();

  /* flip values so robot moves forward when stick-forward/LEDs-green */
  leftFrontMotor.setInverted(false); // <<<<<< Adjust this
  rightFrontMotor.setInverted(true); // <<<<<< Adjust this

  /*
   * WPI drivetrain classes defaultly assume left and right are opposite. call
   * this so we can apply + to both sides when moving forward. DO NOT CHANGE
   */
  _drive.setRightSideInverted(false);
  }

  @Override
  public void teleopPeriodic() {
    double xSpeed = joy1.getRawAxis(1); // make forward stick positive
    double zRotation = joy1.getRawAxis(2); // WPI Drivetrain uses positive=> right
    //int Elevatordirection = joy1.getPOV(0);
    boolean lowPowerThrow = joy1.getRawButton(buttonX);
    boolean MediumPowerThrow = joy1.getRawButton(buttonA);
    boolean MediumHighPowerThrow = joy1.getRawButton(buttonB);
    boolean HighPowerThrow = joy1.getRawButton(buttonY);

    _drive.arcadeDrive(xSpeed, zRotation);

    /* hold down btn1 to print stick values */
    if (joy1.getRawButton(1)) {
      System.out.println("xSpeed:" + xSpeed + "    zRotation:" + zRotation);
    }

    if(lowPowerThrow) {
      // Run shooter motors at low speed
      powerLevel = 0.25;
        if(joy1.getRawButton(buttonBack)) {
          timeCount = new Timer();
          timeCount.reset();
          timeCount.start();
          powerLevel = 0.0;
            if(timeCount.get() < 3.0) {
          powerLevel = -0.25;
            }
        }
    } else if(MediumPowerThrow) {
      // Run shooter motors at medium speed
      powerLevel = 0.5;
        if(joy1.getRawButton(buttonBack)) {
          timeCount = new Timer();
          timeCount.reset();
          timeCount.start();
          powerLevel = 0.0;
            if(timeCount.get() < 3.0) {
              powerLevel = -0.5;
            }
        }
      } else if(MediumHighPowerThrow) {
      // Run shooter motors at medium-high speed
      powerLevel = 0.75;
        if(joy1.getRawButton(buttonBack)) {
          timeCount = new Timer();
          timeCount.reset();
          timeCount.start();
          powerLevel = 0.0;
            if(timeCount.get() < 3.0) {
          powerLevel = -0.75;
            }
        }
      } else if(HighPowerThrow) {
      // Run shooter motors at high speed
       powerLevel = 1.0; 
         if(joy1.getRawButton(buttonBack)) {
          timeCount = new Timer();
          timeCount.reset();
          timeCount.start();
          powerLevel = 0.0;
            if(timeCount.get() < 3.0) {
             powerLevel = -1.0;
            }
          }
      }

    double shooter_leftmotor_power = powerLevel;
    double shooter_rightmotor_power = powerLevel * -1;
    shooter_leftmotor.set(ControlMode.PercentOutput, shooter_leftmotor_power);
    shooter_rightmotor.set(ControlMode.PercentOutput, shooter_rightmotor_power);

    if(joy1.getPOV(0) > 0) {
      elevator.set(ControlMode.PercentOutput, 1.0);
    } else if(joy1.getPOV(4) > 0) {
      elevator.set(ControlMode.PercentOutput, -1.0);
    } else {
      elevator.set(ControlMode.PercentOutput, 0.0);
    }
    if(joy1.getRawButton(buttonLT)) {
      hookmotor1.set(ControlMode.PercentOutput, 0.5);
      hookmotor2.set(ControlMode.PercentOutput, 0.5);
    } else if(joy1.getRawButton(buttonRT)) {
      hookmotor1.set(ControlMode.PercentOutput, -0.3);
      hookmotor2.set(ControlMode.PercentOutput, -0.3);
    } else {
      hookmotor1.set(ControlMode.PercentOutput, 0.0);
      hookmotor2.set(ControlMode.PercentOutput, 0.0);
    }
    if(joy1.getRawButton(buttonStart)) {
      controlpanelmotor.set(ControlMode.PercentOutput, 0.5);
    }
    if(joy1.getRawButton(buttonTopLeft)) {
      boolean solon = true;
      solenoid1.set(solon);
    } if(joy1.getRawButton(buttonTopRight)) {
      boolean soloff = false;
      solenoid1.set(soloff);
    }
  }
  
  
  
  @Override
  public void testInit() {
  }



  @Override
  public void testPeriodic() {
  }

}
