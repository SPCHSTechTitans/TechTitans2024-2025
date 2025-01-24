package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.functions.ArmControl;
import org.firstinspires.ftc.teamcode.functions.OmniDrive;

@TeleOp(name="Main", group="Linear OpMode")
public class Main extends LinearOpMode {

    public ElapsedTime runtime = new ElapsedTime();

    
    @Override
    public void runOpMode() throws InterruptedException {

        // Wait for the game to start (driver presses PLAY)
        telemetry.addData("Initialized","Status");
        telemetry.update();
        waitForStart();
        runtime.reset();

        // Initialize hardware
        OmniDrive OmniFunction = new OmniDrive(hardwareMap);
        ArmControl ArmFunction = new ArmControl(hardwareMap);

        // Run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            // Call the omniFunction method and pass in the gamepad and telemetry
            OmniFunction.OmniUpdate(gamepad1,telemetry);
            ArmFunction.SlideControl(gamepad2, telemetry);
            ArmFunction.ClawControl(gamepad2, telemetry);

            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();
        }
    }
}
