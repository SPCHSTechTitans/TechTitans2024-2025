package org.firstinspires.ftc.teamcode.functions;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;


public class SlideFunctionsAndClawFunctionandArmFunction {

    public DcMotor rightSlideMotor;
    public DcMotor leftSlideMotor;
    public TouchSensor slideSafety;
    public Servo claw;
    public DcMotor arm;
    public Servo wristServo;

    public SlideFunctionsAndClawFunctionandArmFunction(HardwareMap hardwareMap) {

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
        claw = hardwareMap.get(Servo.class, "Claw");
        //'initialise' the Wrist
        arm = hardwareMap.get(DcMotor.class, "Arm");
        wristServo = hardwareMap.get(Servo.class, "wrist");
    }


    public void SlideControl(Gamepad gamepad2, Telemetry telemetry){

        double slidePowerConst = 0.5; //max power of slide
        double slidePower = gamepad2.right_stick_y;

        //get position of slides
        int rightSlidePosition = rightSlideMotor.getCurrentPosition();
        int leftSlidePosition = leftSlideMotor.getCurrentPosition();

        //Legacy code. Switch to this if the slide safety button breaks
        /*
        if ((rightSlidePosition <= -6000) && slidePower > 0 || (rightSlidePosition >= -100) && slidePower < 0) {
            slidePower = 0;
            telemetry.addData("Slide Safety is working", slideSafety);
        }
        */

        /* If the right slide is too high and is receiving power,
         * or if the slide safety is pressed and power is negative,
         * set the power to zero.
         *
         * We don't reset the encoder value when the slide touches the safety because that requires
         * us to put the motor into a completely different run mode; "STOP_AND_RESET" (or something
         * along the lines of that). We firstly cannot do this in a loop, and secondly the workaround
         * would be to make the reset a button press on the controller, in which case we don't need the
         * button in the first place.
         * All this button does essentially is provide a more foolproof
         * stop on one end of the slide. It does not provide any sort of protections against an
         * overextension on the other side of the slide.
         */
        if ((rightSlidePosition <= -6000) && slidePower > 0 || slideSafety.isPressed() && slidePower < 0) {
            slidePower = 0;
            telemetry.addData("Slide safety is working", slideSafety);
        }
        telemetry.addData("Slide safety is awesome", rightSlidePosition);

        rightSlideMotor.setPower(slidePower * slidePowerConst);
        leftSlideMotor.setPower(slidePower * slidePowerConst);
        //telemetry poo
        telemetry.addData("Slide power","%4.2f", slidePower);
        telemetry.addData("Right Slide Position", rightSlidePosition);
        telemetry.addData("Left Slide Position", leftSlidePosition);
    }

    public void ClawControl(Gamepad gamepad2, Telemetry telemetry) {

        boolean clawButtonPressed;
        // I set this to 0.9 in the small chance that our controller breaks and no one notices
        // in an ideal world, it should be set to 1
        if (gamepad2.a) {
            clawButtonPressed = true;
            claw.setPosition(1);
            telemetry.addData("Is claw open?",clawButtonPressed);
        }
        else {
            clawButtonPressed = false;
            claw.setPosition(0.5);
            telemetry.addData("Is claw open?", clawButtonPressed);
        }


    }

    public void ArmControl(Gamepad gamepad2, Telemetry telemetry) {
        arm.setPower(gamepad2.left_stick_y/2);
    }
    public void WristControl(Gamepad gamepad2, Telemetry telemetry) {
        boolean LeftDpad = false;
        boolean UpDpad = false;
        boolean RightDpad = false;
        if (gamepad2.dpad_left) {
            wristServo.setPosition(0);
            LeftDpad = true;
        }
        else if (gamepad2.dpad_up) {
            wristServo.setPosition(0.2);
            UpDpad = true;
        }
        else if (gamepad2.dpad_right) {
            wristServo.setPosition(0.5);
            RightDpad = true;
        }
        telemetry.addData("Servo value: ", wristServo.getPosition());
        telemetry.addData("LeftDpad: ", LeftDpad);
        telemetry.addData("UpDpad: ", UpDpad);
        telemetry.addData("RightDpad: ", RightDpad);
    }

}
