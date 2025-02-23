import edu.wpi.first.wpilibj.TalonFX;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.math.controller.PIDController;

public class Robot extends TimedRobot {
    private TalonFX elevatorMotor1;
    private TalonFX elevatorMotor2;
    private Joystick joystick;
    
    // Constants
    private static final double MINHEIGHT = 0; // inches
    private static final double MAXHEIGHT = 40;
    
    // PID constants //TODO fix values
    private static final double kP = 0.0;
    private static final double kI = 0.0;
    private static final double kD = 0.0;
    
    // PID controller
    private PIDController pidController;
    
    // Tolerance for considering position reached
    private static final double POSITION_TOLERANCE = 0.5; // inches
    
    @Override
    public void robotInit() {
        elevatorMotor1 = new TalonFX(9);
        elevatorMotor2 = new TalonFX(10);
        joystick = new Joystick(0);
        
        // Initialize PID controller
        pidController = new PIDController(kP, kI, kD);
        pidController.setTolerance(POSITION_TOLERANCE);
        
        // Reset encoder positions
        elevatorMotor1.setSelectedSensorPosition(0);
    }
    
    private double getElevatorPosition() {
        // Convert encoder ticks to inches - need to adjust this conversion factor
        double TICKS_PER_INCH = 4096.0/(.184/*link length*/ * 14/*links per rotation*/ * 1.75 /*cascading multiplier*/; //TODO fix these numbers
        return elevatorMotor1.getSelectedSensorPosition() / TICKS_PER_INCH;
    }
    
    public void elevatorControl(double targetPosition) {
        // Clamp target position to valid range
        targetPosition = Math.min(Math.max(targetPosition, MINHEIGHT), MAXHEIGHT);
        
        // Get current position
        double currentPosition = getElevatorPosition();
        
        // Calculate PID output
        double pidOutput = pidController.calculate(currentPosition, targetPosition);
        
        // Apply output to motors if within height limits
        if ((currentPosition <= MINHEIGHT && pidOutput < 0) || 
            (currentPosition >= MAXHEIGHT && pidOutput > 0)) {
            elevatorMotor1.set(0);
            elevatorMotor2.set(0);
        } else {
            elevatorMotor1.set(pidOutput);
            elevatorMotor2.set(-pidOutput);
        }
    }
    
    public void manualControl(double speed) {
        double currentPosition = getElevatorPosition();
        
        // Prevent movement beyond limits
        if ((currentPosition <= MINHEIGHT && speed < 0) || 
            (currentPosition >= MAXHEIGHT && speed > 0)) {
            elevatorMotor1.set(0);
            elevatorMotor2.set(0);
        } else {
            elevatorMotor1.set(speed);
            elevatorMotor2.set(-speed);
        }
    }
    
    @Override
    public void teleopPeriodic() {
        double elevatorSpeed = joystick.getRawAxis(1);
        
        // Apply deadzone
        if (Math.abs(elevatorSpeed) < 0.1) {
            elevatorSpeed = 0;
        }
        
    }
}
