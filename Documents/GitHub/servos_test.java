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
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;



@TeleOp(name="servos_test", group="Active")
//@Disabled
public class servos_test extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
        Servo founL = null;
        Servo founR = null;
        Servo outL = null;
        Servo outR = null;
        Servo strL = null;
        Servo strR = null;


    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        founL = hardwareMap.get(Servo.class, "foun_l");
        founR = hardwareMap.get(Servo.class, "foun_r");
        outL = hardwareMap.get(Servo.class, "out_l");
        outR = hardwareMap.get(Servo.class, "out_r");
        strL = hardwareMap.get(Servo.class, "str_l");
        strR = hardwareMap.get(Servo.class, "str_r");

        //START/PLAY
        waitForStart();
        runtime.reset();
        double tinici=0;
        double out=gamepad1.right_stick_x;
        //run until STOP is pressed
        while (opModeIsActive()) {
            strL.setPosition(gamepad1.left_stick_x);
            strR.setPosition(1-gamepad1.left_stick_x);
            outL.setPosition(gamepad1.right_stick_x);
            outR.setPosition(1-gamepad1.right_stick_x);
            founL.setPosition(gamepad1.right_trigger);
            founR.setPosition(1-gamepad1.right_trigger);
            telemetry.addData("out_L ", "%5.2f", (outL.getPosition()));
            telemetry.addData("out_R ", "%5.2f", outR.getPosition());
            telemetry.addData("foun_L ", "%5.2f", founL.getPosition());
            telemetry.addData("foun_R .", "%5.2f", founR.getPosition());
            telemetry.addData("str_L .", "%5.2f", (strL.getPosition()));
            telemetry.addData("str_R .", "%5.2f", (strR.getPosition()));
            telemetry.update();
            sleep(200);
        }
    }
}
