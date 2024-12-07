package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.functions.SlideFunctionsAndClawFunction;
import org.firstinspires.ftc.teamcode.AutonomousMeh.LeftAuto;

@Autonomous(name="LeftMainAutonomous", group="Linear OpMode")
public class LeftMainAutonomous extends LinearOpMode {

    public ElapsedTime runtime = new ElapsedTime();

    
    @Override
    public void runOpMode() throws InterruptedException {

        // Wait for the game to start (driver presses PLAY)
        telemetry.addData("Initialized","Status");
        telemetry.update();
        waitForStart();
        runtime.reset();

        // Initialize hardware
        LeftAuto LeftAuto = new LeftAuto(hardwareMap);
        SlideFunctionsAndClawFunction SlidesAndClaw = new SlideFunctionsAndClawFunction(hardwareMap);

        // Run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            // Call the omniFunction method and pass in the gamepad and telemetry
            LeftAuto.start();

            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();
        }
    }
}
