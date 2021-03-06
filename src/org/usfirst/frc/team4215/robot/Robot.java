/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4215.robot;

import edu.wpi.cscore.AxisCamera;
import edu.wpi.cscore.CvSink;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.VisionThread;

import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team4215.robot.commands.ExampleCommand;
import org.usfirst.frc.team4215.robot.commands.ProcessPipelineData;
import org.usfirst.frc.team4215.robot.subsystems.Camera;
import org.usfirst.frc.team4215.robot.subsystems.ExampleSubsystem;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {
	public static final ExampleSubsystem kExampleSubsystem = new ExampleSubsystem();
	//public static final ProcessPipelineData processPipelineData = new ProcessPipelineData();

	//public static final Camera camera = new Camera();
	
	public static OI m_oi;

	/*
	private double centerX = 0.0;			//Creates the variable centerX. 
	private VisionThread visionThread;			//Creates Vision Thread for future use
	
	private final Object imgLock = new Object();
	public Object visionStop;
	*/
	
	final int IMG_WIDTH = 320;
	final int IMG_HEIGHT = 240;
	
	
	CameraPID visionPID;
	VisionThread visionThread;
	AxisCamera cameraFront;

	
	
	
	
	Command m_autonomousCommand;
	SendableChooser<Command> m_chooser = new SendableChooser<>();

	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		m_oi = new OI();
		m_chooser.addDefault("Default Auto", new ExampleCommand());
		// chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", m_chooser);
		
		cameraFront = CameraServer.getInstance().addAxisCamera("Front", "10.42.15.39");
		cameraFront.setResolution(IMG_WIDTH, IMG_HEIGHT);
		
		visionPID = new CameraPID();
	    visionThread = new VisionThread(cameraFront, new Pipeline(), visionPID);
	    System.out.println("VisonThread initialized properly");
	     
	    visionThread.setDaemon(false);
	    System.out.println("Daemon set properly");
	     
		visionThread.start();
		System.out.println("VisonThread started without a hitch");
		
		
		/*
		visionThread = new VisionThread(Robot.camera.getCamera(), new Pipeline(), pipeline -> {
			
			System.out.println("Running vision thread");

	    	
			CvSink cvSink = CameraServer.getInstance().getVideo();
			Mat source0 = new Mat();
			Mat output = new Mat();
			
			
				
		    if (!pipeline.filterContoursOutput().isEmpty()) {
		    	Rect r = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
		        synchronized (imgLock) {
	                centerX = r.x + (r.width / 2);	                	                
	                System.out.println("Current Center X variable: " + centerX);
		        }
		    }
		    else {
		    	System.out.println("No Contours");
		    }
		});
		System.out.println("Initialized vision thread");
		
		visionThread.setDaemon(true);
	
		visionThread.start();
		System.out.println("Started vision thread");
		*/
		
		
				
		System.out.println("Got through robotInit");
		
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		m_autonomousCommand = m_chooser.getSelected();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (m_autonomousCommand != null) {
			m_autonomousCommand.start();
		}
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
		
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
