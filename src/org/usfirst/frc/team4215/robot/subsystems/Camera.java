package org.usfirst.frc.team4215.robot.subsystems;

import org.usfirst.frc.team4215.robot.commands.ProcessPipelineData;

import edu.wpi.cscore.AxisCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * An example subsystem.  You can replace me with your own Subsystem.
 */
public class Camera extends Subsystem {
	
	private static final int IMG_WIDTH = 320;
	private static final int IMG_HEIGHT = 240;

	
	AxisCamera camera;
	CameraServer server;

	public Camera() {
		server = CameraServer.getInstance();
		camera = server.addAxisCamera("10.42.15.39");
		camera.setResolution(IMG_WIDTH, IMG_HEIGHT);
		server.startAutomaticCapture();	//Begins getting video from the camera	
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
		setDefaultCommand(new ProcessPipelineData());
	}	
}
