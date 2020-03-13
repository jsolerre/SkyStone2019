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

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.Servo;


/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. W60hen an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="MecanumBasicPOV_modified", group="Active")
//@Disabled
public class MecanumBasicPOV_modified extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor frontLeftMotor = null;
    private DcMotor backLeftMotor = null;
    private DcMotor frontRightMotor = null;
    private DcMotor backRightMotor = null;
    private DcMotor leftIntake = null;
    private DcMotor rightIntake = null;
    private DcMotor slides = null;
        Servo founL = null;
        Servo founR = null;
        Servo outL = null;
        Servo outR = null;
        Servo strL = null;
        Servo strR = null;



    // operational constants
    double joyScale = 0.65; //Constant per suabitzar moviments joyestick
    //double motorMax = 0.6; // Limit motor power to this value for Andymark RUN_USING_ENCODER mode
    boolean firstUp = true;

    boolean outtakeState = true; //false: minimum / true: maximum      //in the beginning, they won't move. When pressed, it'll be true, so it willmove
    boolean strState = true;
    boolean founState = true;
    boolean intakeState = true;
    boolean again = false;


    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        //get movement motors
        frontLeftMotor = hardwareMap.get(DcMotor.class, "front_left_drive");
        backLeftMotor = hardwareMap.get(DcMotor.class, "back_left_drive");
        frontRightMotor = hardwareMap.get(DcMotor.class, "front_right_drive");
        backRightMotor = hardwareMap.get(DcMotor.class, "back_right_drive");
        //directions
         frontLeftMotor.setDirection(DcMotor.Direction.FORWARD);
         backLeftMotor.setDirection(DcMotor.Direction.FORWARD);
         frontRightMotor.setDirection(DcMotor.Direction.REVERSE);
         backRightMotor.setDirection(DcMotor.Direction.REVERSE);

         //get other motors and servos         
         leftIntake = hardwareMap.get(DcMotor.class, "left_intake");
         rightIntake = hardwareMap.get(DcMotor.class, "right_intake");
         slides = hardwareMap.get(DcMotor.class, "slides");
        founL = hardwareMap.get(Servo.class, "foun_l");
        founR = hardwareMap.get(Servo.class, "foun_r");
        outL = hardwareMap.get(Servo.class, "out_l");
        outR = hardwareMap.get(Servo.class, "out_r");
        strL = hardwareMap.get(Servo.class, "str_l");
        strR = hardwareMap.get(Servo.class, "str_r");
        leftIntake.setDirection(DcMotor.Direction.REVERSE);
        rightIntake.setDirection(DcMotor.Direction.FORWARD);
        slides.setDirection(DcMotor.Direction.FORWARD);

        slides.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slides.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //START/PLAY
        waitForStart();
        runtime.reset();

        //run until STOP is pressed
        while (opModeIsActive()) {
            //INTAKE
            if(gamepad1.a){
                if(intakeState==false && !again){
                    leftIntake.setPower(0);
                    rightIntake.setPower(0);
                }
                else if (intakeState==true && !again){
                    leftIntake.setPower(-0.6);
                    rightIntake.setPower(-0.6);
                }
                intakeState = !intakeState;
                sleep(150);
                again = true;
            } else{
                again = false;
            }

            // SLIDES PROGRAMMING
            //pot ser que falti un setMode RUN_WITHOUT_ENCODER per tots els motors
            //no ho he posat perque els motors de moviment no ho tenien i funcionen be

            if((gamepad1.left_trigger != 0 || gamepad1.right_trigger != 0) && slides.getMode()==DcMotor.RunMode.RUN_TO_POSITION){
                slides.setTargetPosition(slides.getCurrentPosition());
                slides.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }

            if(gamepad1.right_trigger > 0 && gamepad1.left_trigger == 0){
                slides.setPower(gamepad1.right_trigger);
                sleep(100);
            }
            else if(gamepad1.left_trigger > 0 && gamepad1.right_trigger == 0){
                if(slides.getCurrentPosition() > 150){
                    firstUp=false;
                }
                if(slides.getCurrentPosition() <= 150 && firstUp==false ){
                    slides.setPower(0);
                }
                if(gamepad1.left_trigger == 0 && gamepad1.right_trigger == 0){
                    slides.setTargetPosition(slides.getCurrentPosition());
                    slides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                }
                else{
                    slides.setPower(gamepad1.left_trigger * -1);
                }
                sleep(300);
            }
            else{
                slides.setPower(0);
            }
            //BINARIES: OUTTAKE, STR, FOUN
            //OUTTAKE
            if(gamepad1.y){  //minimo
                if(outtakeState == false){ //minimum
                    outL.setPosition(0.44);
                    outR.setPosition(0.41);
                }
                else if (outtakeState == true){ //maximum
                    outL.setPosition(0.77);
                    outR.setPosition(0.12);
                }
                outtakeState = !outtakeState;
                sleep(150);
            }
            // //STRENGTHEN
            if(gamepad1.b){
                if(strState == false){ //minimum
                    strL.setPosition(0);
                    strR.setPosition(1);
                }
                else if (strState == true){ //maximum
                    strL.setPosition(0.45);
                    strR.setPosition(0.55);
                }
                strState = !strState;
                sleep(150);
            }
            //FOUNDATION
            if(gamepad1.x){
                if(founState == false){ //minimum
                    founL.setPosition(0);
                    founR.setPosition(1);
                }
                else if (founState == true){ //maximum
                    founL.setPosition(1);
                    founR.setPosition(0);
                }
                founState = !founState;
                sleep(150);
            }


            //MOVEMENT
            // Setup a variable for each drive wheel to save power level for telemetry
            double y = -gamepad1.left_stick_y * joyScale;
            double x = -gamepad1.left_stick_x * joyScale;
            double rx = gamepad1.right_stick_x * joyScale;
            double frontLeftPower = y - x + rx; //Desplaçament a la dreta
            double backRightPower = y - x - rx; //Desplaçament a l'esquerra

            double backLeftPower = y + x + rx;
            double frontRightPower = y + x - rx;

            telemetry.addData("Motors", "X (%.2f), Y (%.2f)", x,y );


            if (Math.abs(frontLeftPower) > 1 || Math.abs(backLeftPower) > 1 ||
                    Math.abs(frontRightPower) > 1 || Math.abs(backRightPower) > 1 ) {
                // Find the largest power
                double max = 0;
                max = Math.max(Math.abs(frontLeftPower), Math.abs(backLeftPower));
                max = Math.max(Math.abs(frontRightPower), max);
                max = Math.max(Math.abs(backRightPower), max);

                // Divide everything by max (it's positive so we don't need to worry
                // about signs)
                frontLeftPower /= max;
                backLeftPower /= max;
                frontRightPower /= max;
                backRightPower /= max;
            }

            frontLeftMotor.setPower(frontLeftPower);
            backLeftMotor.setPower(backLeftPower);
            frontRightMotor.setPower(frontRightPower);
            backRightMotor.setPower(backRightPower);

            // Send calculated power to wheels
            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Motors", "frontleft (%.2f), backleft (%.2f), frontright (%.2f), backright (%.2f)", frontLeftPower, backLeftPower, frontRightPower, backRightPower );
            telemetry.update();
        }
    }
}
