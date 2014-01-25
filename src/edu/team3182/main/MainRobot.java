/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.team3182.main;


import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class MainRobot extends IterativeRobot {
    // create attributes for joysticks
    private Joystick _leftDriveJoystick;
    private Joystick _rightDriveJoystick;
    private Joystick _buttonsJoystick;
    
    // create attribute for drivetrain
    private RobotDrive _driveTrain;
    
    // create attributes for auxiliary motors
    private Jaguar _upperShooterMotor;
    private Jaguar _lowerShooterMotor;
    private Jaguar _loaderMotor;
    private Jaguar _collectorMotor;
    private Jaguar _turretMotor;
    
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        _leftDriveJoystick = new Joystick(1);  // Joystick 1
        _rightDriveJoystick = new Joystick(2); // Joystick 2
        _buttonsJoystick = new Joystick(3);    // Joystick 3
        
        _driveTrain = new RobotDrive(1, 2);    // PWM 1, 2
        _driveTrain.setSafetyEnabled(false);
        
        _upperShooterMotor = new Jaguar(5);    // PWM 5
        _lowerShooterMotor = new Jaguar(6);    // PWM 6
        _loaderMotor = new Jaguar(9);          // PWM 9
        _collectorMotor = new Jaguar(7);       // PWM 7
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {

    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        
        // drive the robot directly from the joysticks
        _driveTrain.tankDrive(_leftDriveJoystick, _rightDriveJoystick);
        
        // pull useful information from the buttons joystick
        double shooterSpeed = _buttonsJoystick.getThrottle();
        boolean shootBall = _buttonsJoystick.getTrigger();
        boolean loadBall = _buttonsJoystick.getRawButton(2);
        
        // if the driver requests a ball to fire and is not also trying to
        // load a ball
        if (shootBall && !loadBall) {
            
            // set the speed of the shooter to the same as the throttle
            //_upperShooterMotor.set(shooterSpeed);
            //_lowerShooterMotor.set(shooterSpeed);
            
            // set the speed of the shooter to a reasonable value that a little
            // kid can catch... :)
            _upperShooterMotor.set(-.52);
            _lowerShooterMotor.set(-.52);
            
            // run the collector belts
            _collectorMotor.set(-1);
        } else {
            // all stop
            _upperShooterMotor.set(0);
            _lowerShooterMotor.set(0);
        }
        
        // if the driver requests loading a ball and is not also trying to
        // fire
        if (loadBall && !shootBall) {
            // run the collector belts and the loadfer motor so the ball
            // doesn't enter the shoot wheels
            _collectorMotor.set(-1);
            _loaderMotor.set(1);
        } else {
            // stop the loader motor
            _loaderMotor.set(0);
        }
        
        // stop the collector motor only if not shooting
        if (!shootBall) {
            _collectorMotor.set(0);
        }
    }
}
