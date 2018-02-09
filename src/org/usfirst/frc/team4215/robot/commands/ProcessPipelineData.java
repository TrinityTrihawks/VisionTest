package org.usfirst.frc.team4215.robot.commands;


import edu.wpi.cscore.AxisCamera;
import edu.wpi.cscore.CvSink;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.vision.VisionThread;

import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team4215.robot.Pipeline;
import org.usfirst.frc.team4215.robot.Robot;

public class ProcessPipelineData extends Command {

	
	private double centerX = 0.0;			//Creates the variable centerX. 
	private VisionThread visionThread;			//Creates Vision Thread for future use
	
	private final Object imgLock = new Object();
	public Object visionStop;


	public ProcessPipelineData() {
		requires(Robot.camera);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		System.out.println("Initializing ProcessPipelineData command");
		
		visionThread = new VisionThread(Robot.camera.getCamera(), new Pipeline(), pipeline -> {
	    	
		CvSink cvSink = CameraServer.getInstance().getVideo();
		Mat source0 = new Mat();
		Mat output = new Mat();
		
		System.out.println("Running vision thread");
		
			
	    if (!pipeline.filterContoursOutput().isEmpty()) {
	    	Rect r = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
	        synchronized (imgLock) {
                centerX = r.x + (r.width / 2);	                	                
                System.out.println("Current Center X variable:" + centerX);
	        }
	    }
	    else {
	    	System.out.println("No Contours");
	    }
	    });
		visionThread.setDaemon(true);
	
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}