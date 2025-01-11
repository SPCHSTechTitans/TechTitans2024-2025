package org.firstinspires.ftc.teamcode.functions;

import java.util.concurrent.TimeUnit;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;


public class SlideFunctionsAndClawFunction {

    public DcMotor rightSlideMotor;
    public DcMotor leftSlideMotor;
    public TouchSensor slideSafety;
    public Servo Claw;
    public Servo Wrist;

    public String wristPosition = "down";
    boolean canChangeWristPosition = true;

    public SlideFunctionsAndClawFunction(HardwareMap hardwareMap) {

        rightSlideMotor = hardwareMap.get(DcMotor.class, "right_slide_motor");
        leftSlideMotor = hardwareMap.get(DcMotor.class, "left_slide_motor");
        slideSafety = hardwareMap.get(TouchSensor.class,"slide_safety");
        rightSlideMotor.setDirection(DcMotor.Direction.FORWARD);
        leftSlideMotor.setDirection(DcMotor.Direction.REVERSE);
        //reset encoders
        rightSlideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftSlideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightSlideMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftSlideMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //everything brakes at 0
        rightSlideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftSlideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //'initialize' the Claw
        Claw = hardwareMap.get(Servo.class, "Claw");
        //'initialise' the Wrist
        Wrist = hardwareMap.get(Servo.class, "Wrist");
    }


    public void SlideControl(Gamepad gamepad2, Telemetry telemetry){

        double slidePowerConst = 0.9; //max power of slide
        double slidePower = -gamepad2.left_stick_y;

        //get position of slides
        int rightSlidePosition = rightSlideMotor.getCurrentPosition();
        int leftSlidePosition = leftSlideMotor.getCurrentPosition();

        //slide safety
        // Gleb here, this works compeltely off of encoder values because the touch sensor is not setup properly yet.
        if ((rightSlidePosition <= -6000) && slidePower > 0 || (slideSafety.isPressed()) && slidePower < 0) {
            slidePower = 0;
            telemetry.addData("Slide Safety is working", slideSafety);
        }

        rightSlideMotor.setPower(slidePower * slidePowerConst);
        leftSlideMotor.setPower(slidePower * slidePowerConst);
        //telemetry poo
        telemetry.addData("Slide power","%4.2f", slidePower);
        telemetry.addData("Right Slide Position", rightSlidePosition);
        telemetry.addData("Left Slide Position", leftSlidePosition);
    }

    public void ClawControl(Gamepad gamepad2, Telemetry telemetry) {
        boolean ClawOpen = true;
        telemetry.addData("Is claw open?", ClawOpen);

        boolean clawButtonPressed;
        // I set this to 0.9 in the small chance that our controller breaks and no one notices
        // In an ideal world, it should be set to 1
        if (gamepad2.right_trigger >= 0.9 && (Wrist.getPosition() <= 0.4)) { // Open
            telemetry.addData("Here's the line for right trigger", gamepad2.right_trigger);
            Claw.setPosition(0.35);
        }
        else if (gamepad2.right_trigger < 0.9) { // Closed
            Claw.setPosition(0.8);
        }

    }

    public void WristControl(Gamepad gamepad2, Telemetry telemetry) {
        telemetry.addData("Wrist Position", Wrist.getPosition());
        telemetry.addData("Wrist String", wristPosition);

        if (gamepad2.y) {
            Wrist.setPosition(1);
        }
        if (gamepad2.x) {
            Wrist.setPosition(0.3);
        }
        if (gamepad2.a) {
            Wrist.setPosition(0);
        }

        /* if (gamepad2.left_trigger > 0) {
            Wrist.setPosition(0);
        } else if (gamepad2.left_trigger <= 0) {
            Wrist.setPosition(0.9);
        } */
    }

}
