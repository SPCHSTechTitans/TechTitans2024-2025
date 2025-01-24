package org.firstinspires.ftc.teamcode.functions;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;


public class ArmControl {

    public DcMotor slideMotor;
    public DcMotor armMotor;
    public TouchSensor slideSafety;
    public Servo Claw;

    public ArmControl(HardwareMap hardwareMap) {

        slideMotor = hardwareMap.get(DcMotor.class, "slide_motor");
        armMotor = hardwareMap.get(DcMotor.class, "arm_motor");
        slideSafety = hardwareMap.get(TouchSensor.class, "slide_safety");
        slideMotor.setDirection(DcMotor.Direction.FORWARD);
        armMotor.setDirection(DcMotor.Direction.REVERSE);
        //reset encoders
        slideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slideMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //everything brakes at 0
        slideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //'initialize' the Claw
        Claw = hardwareMap.get(Servo.class, "Claw");
    }


    public void SlideControl(Gamepad gamepad2, Telemetry telemetry) {

        double slidePowerConst = 0.9; //max power of slide
        double slidePower = -gamepad2.left_stick_y;
        double armPowerConst = 0.5; // max power of arm
        double armPower = -gamepad2.right_stick_x;

        //get position of slides
        int slidePosition = slideMotor.getCurrentPosition();
        int armPosition = armMotor.getCurrentPosition();

        //slide safety
        // Gleb here, this works completely off of encoder values because the touch sensor is not setup properly yet.
        if ((slidePosition <= -22850) && slidePower > 0) {
            slidePower = 0;
            telemetry.addData("Slide Safety is in action", slideSafety);
        }

        slideMotor.setPower(slidePower * slidePowerConst);
        armMotor.setPower(armPower * armPowerConst);
        //telemetry poo
        telemetry.addData("Slide power", "%4.2f", slidePower);
        telemetry.addData("Slide Position", slidePosition);
        telemetry.addData("Arm Position", armPosition);
    }

    public void ClawControl(Gamepad gamepad2, Telemetry telemetry) {
        boolean ClawOpen = true;
        telemetry.addData("Is claw open?", ClawOpen);

        boolean clawButtonPressed;
        // I set this to 0.9 in the small chance that our controller breaks and no one notices
        // In an ideal world, it should be set to 1
        if (gamepad2.right_trigger >= 0.9) {
            clawButtonPressed = true;
        } else {
            clawButtonPressed = false;
        }

        // This function makes so that the claw is closed by default, opens when the driver pressed the right trigger,
        // and closes when the driver releases the right trigger. These magic numbers were found out through testing.
        if (clawButtonPressed) {
            Claw.setPosition(0.7);
        } else {
            Claw.setPosition(0.25);
        }
        /*
        alina's epic version of claw functions

        boolean ClawOpen;

        if (gamepad2.a) {
            Claw.setPosition(1); //close claw
            ClawOpen = false;
          }
          else if {
            Claw.setPosition(0); // open claw
            ClawOpen = true;
           }

          telemetry.addData("is Claw Open?", ClawOpen);
    }
        */
    }
}
