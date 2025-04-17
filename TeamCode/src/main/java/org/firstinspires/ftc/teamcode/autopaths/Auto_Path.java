package org.firstinspires.ftc.teamcode.autopaths;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.MecanumDrive;

@Autonomous(name="Auto_Path")
public final class Auto_Path extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d beginPose = new Pose2d(0, 0, 0);
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(-46, -46, 0));
        Servo servo = hardwareMap.servo.get("left_hand");

        waitForStart();

        Actions.runBlocking(
                drive.actionBuilder(beginPose)
                        .turn(Math.toRadians(-90))
                        .lineToY(46)
                        .turn(Math.toRadians(-90))
                        .lineToX(46)
                        .turn(Math.toRadians(-90))
                        .lineToY(-46)
                        .turn(Math.toRadians(-90))
                        .lineToX(-46)
                        .build());
//                        .turnTo(Math.PI / 2)
//                        .stopAndAdd(new PatientServoAction(servo, 0)) // stop all actions until timer stops
//                        .stopAndAdd(new ServoAction(servo, 0))  // sets servo to spin
//                        .lineToY(96)
//                        .turnTo(0)
//                        .lineToX(96)
//                        .turnTo(Math.PI / -2)
//                        .lineToY(0)
//                        .turnTo(Math.PI)
//                        .lineToX(0)
//                        .build());

    }

    // Simiple class object to use a servo
    public class ServoAction implements Action {
        Servo servo;
        double position;

        public ServoAction(Servo s, double p) {
            this.servo = s;
            this.position = p;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            servo.setPosition(position);
            return false;  // runs this code and then exits this function. true would continue running this could in a loop
        }
    }

    // Simple class object to use a servo with a time limit
    public class PatientServoAction implements Action {
        Servo servo;
        double position;
        ElapsedTime timer;

        public PatientServoAction(Servo s, double p) {
            this.servo = s;
            this.position = p;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if (timer == null) {
                timer = new ElapsedTime();
                servo.setPosition(position);
            }
            // do we need to keep running?
            return timer.seconds() < 3;    // stops running once the timer reaches 3 seconds
        }
    }
}
