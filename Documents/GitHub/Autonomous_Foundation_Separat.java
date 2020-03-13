package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


@Autonomous(name="Autonomous_Foundation_Separat", group="Active")
public class Autonomous_Foundation_Separat extends LinearOpMode{
        private DcMotor frontLeftMotor = null;
        private DcMotor backLeftMotor = null;
        private DcMotor frontRightMotor = null;
        private DcMotor backRightMotor = null;
        private DcMotor leftIntake = null;
        private DcMotor rightIntake = null;
//        private DcMotor slides = null;
        Servo founL = null;
        Servo founR = null;
//        Servo outL = null;
//        Servo outR = null;
//        Servo strL = null;
//        Servo strR = null;

        int intakeState = 1; //1 per un sentit, -1 per l'altre.
        double slidesPower = 0.7; //1 per un sentit, -1 per l'altre.

    static final double TicksPercm = 537.6/(10*3.141592654);
    long first_millis = 0;
    int ticksParellX = 0;
    int ticksParellY = 0;
    double powerParellX=0;
    double powerParellY=0;

    int targetedPosBL=0;
    int targetedPosBR=0;
    int targetedPosFL=0;
    int targetedPosFR=0;
	

    public void runOpMode() {

        
        //Motor config canviat
		frontLeftMotor = hardwareMap.get(DcMotor.class, "front_left_drive");
		backLeftMotor = hardwareMap.get(DcMotor.class, "back_left_drive");
		frontRightMotor = hardwareMap.get(DcMotor.class, "front_right_drive");
		backRightMotor = hardwareMap.get(DcMotor.class, "back_right_drive");

        frontLeftMotor.setDirection(DcMotor.Direction.FORWARD);
        frontRightMotor.setDirection(DcMotor.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotor.Direction.FORWARD);
        backRightMotor.setDirection(DcMotor.Direction.REVERSE);

        frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftIntake = hardwareMap.get(DcMotor.class, "left_intake");
        rightIntake = hardwareMap.get(DcMotor.class, "right_intake");
//        slides = hardwareMap.get(DcMotor.class, "slides");
        founL = hardwareMap.get(Servo.class, "foun_l");
        founR = hardwareMap.get(Servo.class, "foun_r");
//        outL = hardwareMap.get(Servo.class, "out_l");
//        outR = hardwareMap.get(Servo.class, "out_r");
//        strL = hardwareMap.get(Servo.class, "str_l");
//        strR = hardwareMap.get(Servo.class, "str_r");

        leftIntake.setDirection(DcMotor.Direction.REVERSE);
        rightIntake.setDirection(DcMotor.Direction.FORWARD);

		
		//CAMARA TRET
		
		waitForStart();
		//PROGRAM ROUTES
        if (opModeIsActive()) {
            move("f", 96, 0.6, false);
            foundation(true);
            rotate(true, 1, 0.6);
            move("f", 20, 0.6, false);
            foundation(false);
            move("b", 20, 0.7, false);
            move("r", 70, 0.7, false);
            move("b", 75, 0.7, false);
        }
    }
	
	
	//______________________________FUNCIÓ MOVE
	//______________________________FUNCIÓ MOVE
	public void move (String direction, double distance, double power, boolean intake) {
        if (direction == "fl") {
            ticksParellX = (int) (distance * TicksPercm);
            ticksParellY = 0;
            powerParellX = power;
            powerParellY = 0.1;
        } else if (direction == "f") {
            ticksParellX = (int) (distance * TicksPercm);
            ticksParellY = (int) (distance * TicksPercm);
            powerParellX = power;
            powerParellY = power;
        } else if (direction == "fr") {
            ticksParellX = 0;
            ticksParellY = (int) (distance * TicksPercm);powerParellX = power;
            powerParellX = 0.1;
            powerParellY = power;
        } else if (direction == "bl") {
            ticksParellX = 0;
            ticksParellY = (int) (distance * TicksPercm)*-1;
            powerParellX = 0.1;
            powerParellY = -power;
        } else if (direction == "b") {
            ticksParellX = (int) (distance * TicksPercm)*-1;
            ticksParellY = (int) (distance * TicksPercm)*-1;
            powerParellX = -power;
            powerParellY = -power;
        } else if (direction == "br") {
            ticksParellX = (int) (distance * TicksPercm)*-1;
            ticksParellY = 0;
            powerParellX = -power;
            powerParellY = 0.1;
        } else if (direction == "l") {
            ticksParellX = (int) (distance * TicksPercm);
            ticksParellY = (int) (distance * TicksPercm)*-1;
            powerParellX = power;
            powerParellY = -power;
        } else if (direction == "r") {
            ticksParellX = (int) (distance * TicksPercm)*-1;
            ticksParellY = (int) (distance * TicksPercm);
            powerParellX = -power;
            powerParellY = power;
        }

        if (opModeIsActive()) {
            targetedPosBL=backLeftMotor.getCurrentPosition()+ticksParellX;
            targetedPosBR=backRightMotor.getCurrentPosition()+ticksParellY;
            targetedPosFL=frontLeftMotor.getCurrentPosition()+ticksParellY;
            targetedPosFR=frontRightMotor.getCurrentPosition()+ticksParellX;
            backLeftMotor.setTargetPosition(targetedPosBL);
            frontRightMotor.setTargetPosition(targetedPosFR);
            backRightMotor.setTargetPosition(targetedPosBR);
            frontLeftMotor.setTargetPosition(targetedPosFL);

            frontLeftMotor.setPower(power);
            frontRightMotor.setPower(power);//0
            backRightMotor.setPower(power);
            backLeftMotor.setPower(power); //0

            frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            while (opModeIsActive() && frontLeftMotor.isBusy() || frontRightMotor.isBusy() || backLeftMotor.isBusy() || backRightMotor.isBusy()){
                if(intake){
                    leftIntake.setPower(intakeState);
                    rightIntake.setPower(intakeState);
                }
                if( backLeftMotor.getCurrentPosition() >= targetedPosBL-40 && backLeftMotor.getCurrentPosition() < targetedPosBL+40 &&
                        backRightMotor.getCurrentPosition() >= targetedPosBR-40 && backRightMotor.getCurrentPosition() < targetedPosBR+40 &&
                        frontLeftMotor.getCurrentPosition() >= targetedPosFL-40 && frontLeftMotor.getCurrentPosition() < targetedPosFL+40 &&
                        frontRightMotor.getCurrentPosition() >= targetedPosFR-40 && frontRightMotor.getCurrentPosition() < targetedPosFR+40){
                    telemetry.addData("State",  "CALIBRANDOOO");
                    break;
            }
                else telemetry.addData("State",  "MOVIENDOO");

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


            }

            // Turn off RUN_TO_POSITION
            frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            frontLeftMotor.setPower(0);
            backLeftMotor.setPower(0);
            frontRightMotor.setPower(0);
            backRightMotor.setPower(0);
        }
    }
    public void rotate(boolean clockwise, int quadrants, double power){
        int ticksParellRight = 0;
        int ticksParellLeft = 0;

        if(clockwise == true){
            ticksParellRight = (int) (quadrants * (3.141592654 * TicksPercm) * -1);
            ticksParellLeft = (int) (quadrants * (3.141592654 * TicksPercm));
        }
        else if(clockwise == true){
            ticksParellRight = (int) (quadrants * (3.141592654 * TicksPercm));
            ticksParellLeft = (int) (quadrants * (3.141592654 * TicksPercm)*-1);
        }
        if (opModeIsActive()) {
            targetedPosBL=backLeftMotor.getCurrentPosition()+ticksParellLeft;
            targetedPosBR=backRightMotor.getCurrentPosition()+ticksParellRight;
            targetedPosFL=frontLeftMotor.getCurrentPosition()+ticksParellLeft;
            targetedPosFR=frontRightMotor.getCurrentPosition()+ticksParellRight;
            backLeftMotor.setTargetPosition(targetedPosBL);
            frontRightMotor.setTargetPosition(targetedPosFR);
            backRightMotor.setTargetPosition(targetedPosBR);
            frontLeftMotor.setTargetPosition(targetedPosFL);

            frontLeftMotor.setPower(power);
            frontRightMotor.setPower(power);//0
            backRightMotor.setPower(power);
            backLeftMotor.setPower(power); //0

            frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            while (opModeIsActive() && frontLeftMotor.isBusy() || frontRightMotor.isBusy() || backLeftMotor.isBusy() || backRightMotor.isBusy()){

                if( backLeftMotor.getCurrentPosition() >= targetedPosBL-40 && backLeftMotor.getCurrentPosition() < targetedPosBL+40 &&
                        backRightMotor.getCurrentPosition() >= targetedPosBR-40 && backRightMotor.getCurrentPosition() < targetedPosBR+40 &&
                        frontLeftMotor.getCurrentPosition() >= targetedPosFL-40 && frontLeftMotor.getCurrentPosition() < targetedPosFL+40 &&
                        frontRightMotor.getCurrentPosition() >= targetedPosFR-40 && frontRightMotor.getCurrentPosition() < targetedPosFR+40){
                    telemetry.addData("State",  "CALIBRANDOOO");
                    break;
                }
                else telemetry.addData("State",  "MOVIENDOO");

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


            }

            // Turn off RUN_TO_POSITION
            frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            frontLeftMotor.setPower(0);
            backLeftMotor.setPower(0);
            frontRightMotor.setPower(0);
            backRightMotor.setPower(0);
        }

    }
    public void foundation(boolean state){
        if(state == false){   //minimum
            founL.setPosition(0);
            founR.setPosition(1);
        }
        else if (state == true){   //minimum
            founL.setPosition(1);
            founR.setPosition(0);
        }
    }

}