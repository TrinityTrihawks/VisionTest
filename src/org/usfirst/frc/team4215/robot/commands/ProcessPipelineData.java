package org.usfirst.frc.team4215.robot.commands;


import edu.wpi.cscore.AxisCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Command;

import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team4215.robot.Pipeline;
import org.usfirst.frc.team4215.robot.Robot;

public class ProcessPipelineData extends Command {

	
	private double centerX = 0.0;			//Creates the variable centerX. 
	
	AxisCamera camera;
	Pipeline pipeline;
	public ProcessPipelineData() {
		requires(Robot.camera);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		System.out.println("Initializing ProcessPipelineData command");
	
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		
		System.out.println("Running ProcessPipelineData command");
	
	
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