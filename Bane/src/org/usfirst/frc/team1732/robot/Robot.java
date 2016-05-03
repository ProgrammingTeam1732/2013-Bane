package org.usfirst.frc.team1732.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
    
	Joystick controller = new Joystick(0);
	
	Talon left = new Talon(1);
	Talon right = new Talon(5);
	Talon shooter1 = new Talon(8);
	Talon shooter2 = new Talon(9);

	Solenoid lift = new Solenoid(0);
	Solenoid shoot = new Solenoid(1);
	Solenoid shift = new Solenoid(2);

	boolean prev = false;
	boolean toggle = false;
	
	boolean doLift = false;
	boolean doReverse = false;
	int reverse = 1;
	double flyWheelSpeed = 1;
	
    public void robotInit() {
    	SmartDashboard.putBoolean("Reverse the flywheels?", doReverse);
    	SmartDashboard.putNumber("Flywheel speed:", flyWheelSpeed);
    }
    
    public void teleopPeriodic() {
        setMotors(controller.getRawAxis(1),controller.getRawAxis(5));
        doReverse = SmartDashboard.getBoolean("Reverse the flywheels?");
        flyWheelSpeed = SmartDashboard.getNumber("Flywheel speed:");
        reverse = doReverse ? -1 : 1;
        if(controller.getRawButton(6))setFlyWheels(reverse*flyWheelSpeed);
        else setFlyWheels(0);
        boolean button = (controller.getRawAxis(3) > 0 ? true : false);
        if(button && !prev) {
        	toggle = !toggle;
        }
        lift.set(toggle);
        prev = button;
        
        shoot.set(controller.getRawButton(1));
        shift.set(controller.getRawButton(5));
    }
   
    private void setFlyWheels(double a) {
    	shooter1.set(a);
    	shooter2.set(a);
    }
    
    private void setMotors(double l, double r) {
    	l = Math.abs(l)*l; r = Math.abs(r)*r;
    	left.set(-l);
    	right.set(r);
    }
  
}
