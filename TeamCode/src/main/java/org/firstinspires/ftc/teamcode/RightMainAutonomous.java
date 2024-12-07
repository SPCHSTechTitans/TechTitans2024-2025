package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.AutonomousMeh.RightAuto;

@Autonomous(name="RightMainAutonomous", group="Linear OpMode")
public class RightMainAutonomous extends LinearOpMode {

    public ElapsedTime runtime = new ElapsedTime();

    
    @Override
    public void runOpMode() throws InterruptedException {

        // Wait for the game to start (driver presses PLAY)
        telemetry.addData("Initialized","Status");
        telemetry.update();
        waitForStart();
        runtime.reset();

        // Initialize hardware
        RightAuto RightAuto = new RightAuto(hardwareMap);

        // Run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            // Call the omniFunction method and pass in the gamepad and telemetry
            RightAuto.start();

            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();
        }
    }
}
