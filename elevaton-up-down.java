import edu.wpi.first.wpilibj.TalonFX;  // Neo brushless motor control
import edu.wpi.first.wpilibj.Joystick;  // For joystick input
import edu.wpi.first.wpilibj.TimedRobot;  // Base class for robot code

public class Robot extends TimedRobot {

    // Define the Neo brushless motors for the elevator
    private TalonFX elevatorMotor1; // Elevator motor 1
    private TalonFX elevatorMotor2; // Elevator motor 2

    // Joystick for controlling the elevator movement
    private Joystick joystick; // Joystick is on port 0 by default

    @Override
    public void robotInit() {
        // Initialize the elevator motors
        elevatorMotor1 = new TalonFX(9); // Elevator motor 1 on port 9
        elevatorMotor2 = new TalonFX(10); // Elevator motor 2 on port 10

        // Initialize the joystick (port 0 by default if no port is specified)
        joystick = new Joystick(0); // Joystick on port 0
    }

    @Override
    public void teleopPeriodic() {
        // Read the joystick Y-axis value (used for elevator control)
        double elevatorSpeed = joystick.getRawAxis(1); // Y-axis controls elevator movement

        // Apply a deadzone to avoid unintended motor movements due to slight joystick drift
        if (Math.abs(elevatorSpeed) < 0.1) {
            elevatorSpeed = 0; // Stop elevator motors if joystick is in the deadzone
        }

        // Set both elevator motors to the joystick input (up or down)
        elevatorMotor1.set(elevatorSpeed); // Set elevator motor 1
        elevatorMotor2.set(elevatorSpeed); // Set elevator motor 2
    }
}
