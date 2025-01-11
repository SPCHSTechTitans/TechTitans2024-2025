package org.firstinspires.ftc.teamcode.AutonomousMeh;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class LeftAuto {


    private final DcMotor leftFrontDrive;
    private final DcMotor leftBackDrive;
    private final DcMotor rightFrontDrive;
    private final DcMotor rightBackDrive;

    public LeftAuto(HardwareMap hardwareMap) {
        leftFrontDrive = hardwareMap.get(DcMotor.class, "leftFront");
        leftBackDrive = hardwareMap.get(DcMotor.class, "leftBack");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "rightFront");
        rightBackDrive = hardwareMap.get(DcMotor.class, "rightBack");

        leftFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        rightBackDrive.setDirection(DcMotor.Direction.REVERSE);

        }

        public void start() {
            //Go forward
            leftFrontDrive.setPower(1);
            rightFrontDrive.setPower(1);
            leftBackDrive.setPower(1);
            rightBackDrive.setPower(1);

            //Sleep goes here
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            //Go right
            leftFrontDrive.setPower(1);
            rightFrontDrive.setPower(-1);
            leftBackDrive.setPower(-1);
            rightBackDrive.setPower(1);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            //Stop
            leftFrontDrive.setPower(0);
            rightFrontDrive.setPower(0);
            leftBackDrive.setPower(0);
            rightBackDrive.setPower(0);
        }


}
