/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import java.lang.Math;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;

/**
 * This file illustrates the concept of driving a path based on encoder counts.
 * It uses the common Pushbot hardware class to define the drive on the robot.
 * The code is structured as a LinearOpMode
 *
 * The code REQUIRES that you DO have encoders on the wheels,
 *   otherwise you would use: PushbotAutoDriveByTime;
 *
 *  This code ALSO requires that the drive Motors have been configured such that a positive
 *  power command moves them forwards, and causes the encoders to count UP.
 *
 *   The desired path in this example is:
 *   - Drive forward for 48 inches
 *   - Spin right for 12 Inches
 *   - Drive Backwards for 24 inches
 *   - Stop and close the claw.
 *
 *  The code is written using a method called: encoderDrive(speed, leftInches, rightInches, timeoutS)
 *  that performs the actual movement.
 *  This methods assumes that each movement is relative to the last stopping place.
 *  There are other ways to perform encoder based moves, but this method is probably the simplest.
 *  This code uses the RUN_TO_POSITION mode to enable the Motor controllers to generate the run profile
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@Autonomous(name="Mecanum Autonomous", group="Pushbot")
//@Disabled
public class MecanumAutonomous extends LinearOpMode {

    /* Declare OpMode members. */
    static final double     COUNTS_PER_MOTOR_REV    = 537.6 ;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_CM   = 10.0 ;     // For figuring circumference
    static final double     COUNTS_PER_CM  = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
                                                      (WHEEL_DIAMETER_CM * 3.1415);
    static final double     DRIVE_SPEED             = 0.6;
    static final double     TURN_SPEED              = 0.5;

    private DcMotor frontLeftMotor = null;
    private DcMotor backLeftMotor = null;
    private DcMotor frontRightMotor = null;
    private DcMotor backRightMotor = null;

    private double drive_SpeedFactor ;

    @Override
    public void runOpMode() {

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Resetting Encoders");    //
        telemetry.update();

        //--> ! 03.01.20 Error en la declaració denoms . Es canviesn

        frontLeftMotor = hardwareMap.get(DcMotor.class, "front_left_drive");
        backLeftMotor = hardwareMap.get(DcMotor.class, "back_left_drive");
        frontRightMotor = hardwareMap.get(DcMotor.class, "front_right_drive");
        backRightMotor = hardwareMap.get(DcMotor.class, "back_right_drive");

        //Resetegem els encoders
        frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Connfigurem  encoders
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery

        frontLeftMotor.setDirection(DcMotor.Direction.FORWARD);
        frontRightMotor.setDirection(DcMotor.Direction.REVERSE);

        backLeftMotor.setDirection(DcMotor.Direction.FORWARD);
        backRightMotor.setDirection(DcMotor.Direction.REVERSE);

        // Send telemetry message to indicate successful Encoder reset
        telemetry.addData("Path0",  "Starting at %7d :%7d",
                frontLeftMotor.getCurrentPosition(),
                frontRightMotor.getCurrentPosition(),

                backLeftMotor.getCurrentPosition(),
                backRightMotor.getCurrentPosition());
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // Step through each leg of the path,
        // Note: Reverse movement is obtained by setting a negative distance (not speed)
        //Reduim la velocitat del moviment


        encoderDrive(DRIVE_SPEED,  50,  45);

        //encoderDrive(DRIVE_SPEED,   -20, 90);

       /* robot.leftClaw.setPosition(1.0);            // S4: Stop and close the claw.
        robot.rightClaw.setPosition(0.0);*/
        sleep(1000);     // pause for servos to move

        telemetry.addData("Path", "Complete");
        telemetry.update();
    }


//     *  Method to perfmorm a relative move, based on encoder counts.
//     *  Encoders are not reset as the move is based on the current position.
//     *  Move will stop if any of three conditions occur:
//     *  1) Move gets to the desired position
//     *  2) Move runs out of time
//     *  3) Driver stops the opmode running.

    public void encoderDrive(double speed,
                             double distance,
                             double angle) {    //timeout eliminated
        int distanceXLTarget,distanceXRTarget;
        int distanceYLTarget,distanceYRTarget;

        double distXMotors;
        double distYMotors;

        double powerXMotors;
        double powerYMotors;

        //--> ! 03.01.20 s'hi suma 45º de les rodes Mecanum
        //angle=angle+45;
        //first quadrant
        distXMotors = distance * Math.cos(Math.toRadians(angle)); //optional: -45
        distYMotors = distance * Math.sin(Math.toRadians(angle)); //optional: -45

        if(angle > 0 && angle <= 90){
            powerXMotors = -1;
            powerYMotors = Math.sin(Math.toRadians(angle)) - Math.cos(Math.toRadians(angle));
            //powerYMotors = Math.sin(Math.toRadians(angle)) + Math.cos(Math.toRadians(angle));

        }
        else if(angle > 90 && angle <= 180){
            powerXMotors = Math.sin(Math.toRadians(angle)) + Math.cos(Math.toRadians(angle));
            powerYMotors = 1;

        }
        else if(angle > 180 && angle <= 270){
            powerXMotors = -1;
            powerYMotors = Math.sin(Math.toRadians(angle)) - Math.cos(Math.toRadians(angle));

        }
        else {
            powerXMotors = Math.sin(Math.toRadians(angle)) - Math.cos(Math.toRadians(angle));
            powerYMotors = -1;

        }


        powerXMotors =powerXMotors*DRIVE_SPEED*DRIVE_SPEED;
        powerYMotors=powerYMotors*DRIVE_SPEED*DRIVE_SPEED;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller

            distanceXLTarget = frontLeftMotor.getCurrentPosition() - (int)(distXMotors * COUNTS_PER_CM);
            distanceXRTarget = backRightMotor.getCurrentPosition() + (int)(distXMotors * COUNTS_PER_CM);
            //--> ! 03.01.20 Error en la declararció distYMotors canviat: distXMotor --> distYMotors
            distanceYLTarget = backLeftMotor.getCurrentPosition() - (int)(distYMotors * COUNTS_PER_CM);
            distanceYRTarget = frontRightMotor.getCurrentPosition() + (int)(distYMotors * COUNTS_PER_CM);

            //frontLeftMotor.setTargetPosition(distanceXTarget);

            frontLeftMotor.setTargetPosition(distanceXLTarget);
            backLeftMotor.setTargetPosition(distanceYLTarget);

            //frontRightMotor.setTargetPosition(distanceXTarget);
            frontRightMotor.setTargetPosition(distanceYRTarget);
            backRightMotor.setTargetPosition(distanceXRTarget);


            // Turn On RUN_TO_POSITION
            frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            //  runtime.reset();
            //--> ! 03.01.20 Error Canvi YYXY per YXXY



            frontLeftMotor.setPower(powerYMotors);
            frontRightMotor.setPower(powerXMotors);//0

            backRightMotor.setPower(powerYMotors);
            backLeftMotor.setPower(powerXMotors); //0



            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() && frontLeftMotor.isBusy() && frontRightMotor.isBusy() && backLeftMotor.isBusy() && backRightMotor.isBusy()){

                // Display it for the driver.
                telemetry.addData("Path1",  "Running to %7d :%7d", distanceXLTarget,  distanceXRTarget);
                telemetry.addData("Path2",  "Running at %7d :%7d",
                        frontLeftMotor.getCurrentPosition(),
                        backRightMotor.getCurrentPosition());
                telemetry.update();
                /*
                //--> ! 03.01.20 Pendent de canviar i posar l'aturada dins del while
                // Stop all motion;
                frontLeftMotor.setPower(0);
                backLeftMotor.setPower(0);
                frontRightMotor.setPower(0);
                backRightMotor.setPower(0); */

                // Turn off RUN_TO_POSITION
                frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


            }  //--> ! 03.01.20 S'inclouen  l'stop all motion dins el while

            //  sleep(250);   // optional pause after each move
        }
    }


    
}
