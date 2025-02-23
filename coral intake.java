import edu.wpi.first.wpilibj.DigitalInput;  // For IR sensors
import edu.wpi.first.wpilibj.TalonFX;  // Neo brushless motor control
import edu.wpi.first.wpilibj.Timer;  // For controlling speed
import edu.wpi.first.wpilibj.TimedRobot;  // Base class for robot code

public class Robot extends TimedRobot {

    // Define the digital input pins for the IR sensors
    private DigitalInput irSensor1; // IR sensor 1
    private DigitalInput irSensor2; // IR sensor 2
    
    // Define the Neo brushless motor
    private TalonFX neoMotor;
    
    // Define the elevator motors
    private TalonFX elevatorMotor1;
    private TalonFX elevatorMotor2;

    // Control variables
    private double motorSpeed = 0.5; // Default speed for the Neo motor

    @Override
    public void robotInit() {
        // Initialize IR sensors (connect to the correct digital input pins)
        irSensor1 = new DigitalInput(40); // IR sensor 1 on port 40
        irSensor2 = new DigitalInput(41); // IR sensor 2 on port 41

        // Initialize Neo brushless motor
        neoMotor = new TalonFX(11); // Assuming motor is connected to port 0

        // Initialize elevator motors (assuming two motors for elevator control)
        elevatorMotor1 = new TalonFX(9); // Elevator motor 1 on port 9
        elevatorMotor2 = new TalonFX(10); // Elevator motor 2 on port 10
    }

    @Override
    public void teleopPeriodic() {
        // Logic for IR Sensor 1
        if (irSensor1.get()) {  // When Sensor 1 detects an object (beam is broken)
            // Disable the elevator motors
            elevatorMotor1.set(0);
            elevatorMotor2.set(0);

            // Enable the Neo brushless motor to spin at controlled speed
            neoMotor.set(motorSpeed);
        } else {  // When Sensor 1 is clear (beam is not broken)
            // Enable the elevator motors to move (can adjust speed or behavior)
            elevatorMotor1.set(0.5);  // Set desired speed for the elevator
            elevatorMotor2.set(0.5);

            // Stop the Neo brushless motor (as no object is detected)
            neoMotor.set(0);
        }

        // Logic for IR Sensor 2
        if (irSensor2.get()) {  // When Sensor 2 detects an object (beam is broken)
            // Disable the elevator motors
            elevatorMotor1.set(0);
            elevatorMotor2.set(0);

            // Enable the Neo brushless motor to spin at controlled speed
            neoMotor.set(motorSpeed);
        } else {  // When Sensor 2 is clear (beam is not broken)
            // Enable the elevator motors to move
            elevatorMotor1.set(0.5);  // Set desired speed for the elevator
            elevatorMotor2.set(0.5);

            // Stop the Neo brushless motor
            neoMotor.set(0);
        }

        // Optionally, you can adjust the Neo motor speed dynamically
        // using joystick or buttons (for manual control)
        if (isButtonPressed()) {
            motorSpeed = adjustMotorSpeed();  // Dynamically adjust speed
            neoMotor.set(motorSpeed);
        }
    }

    // Function to check if a button is pressed (to control motor speed manually)
    private boolean isButtonPressed() {
        // Implement your logic here, for example, checking a joystick button
        return false;  // Just an example placeholder
    }

    // Function to adjust motor speed (can be used with a joystick for dynamic control)
    private double adjustMotorSpeed() {
        // Example: Adjust speed based on joystick position or button presses
        return 0.75;  // Just an example to set speed dynamically
    }
}
