package org.usfirst.frc.team4215.robot.commands;


import edu.wpi.cscore.AxisCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Command;

import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team4215.robot.Pipeline;

public class ProcessPipelineData extends Command {
	private static final int IMG_WIDTH = 320;
	private static final int IMG_HEIGHT = 240;
	// Sets Camera Image Resolution
	
	private double centerX = 0.0;			//Creates the variable centerX. 
	
	AxisCamera camera;
	Pipeline pipeline;
	public ProcessPipelineData() {
		
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		CameraServer server = CameraServer.getInstance();
		camera = server.addAxisCamera("10.42.15.37");
		camera.setResolution(IMG_WIDTH, IMG_HEIGHT);
		server.startAutomaticCapture();	//Begins getting video from the camera
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		
      if (!pipeline.filterContoursOutput().isEmpty()) {
            Rect r = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
             
            centerX = r.x + (r.width / 2);	                	                
            System.out.println(centerX); 	//if the code is actually working,
            System.out.println("Current Center X variable");          //a number should be displayed
            
        }
      else {
    	  System.out.println("No Contours");
      }
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