public class ElevatorControl {

    private boolean isElevatorEnabled;
    private boolean isMotorRunning;

    // Simulating IR sensor states
    private DigitalInput irSensor1;
    private DigitalInput irSensor2;

    // Ports for the IR sensors and motors
    private int irSensor1Port;
    private int irSensor2Port;
    private int elevatorMotor1Port;
    private int elevatorMotor2Port;
    private int coralIntakeMotorPort;

    // Placeholder for Rev Max planetary motors (elevator motors) and Neo brushless motor (coral intake)
    private SparkMax elevatorMotor1;
    private SparkMax elevatorMotor2;
    private SparkMax coralIntakeMotor;

    public ElevatorControl(int irSensor1Port, int irSensor2Port, int elevatorMotor1Port, int elevatorMotor2Port, int coralIntakeMotorPort) {
        this.isElevatorEnabled = true; // Elevator starts enabled
        this.isMotorRunning = false;
        this.irSensor1 = new DigitalInput(coralIntakeMotorPort);
        this.irSensor2 = new DigitalInput(coralIntakeMotorPort);

        this.irSensor1Port = irSensor1Port;
        this.irSensor2Port = irSensor2Port;
        this.elevatorMotor1Port = elevatorMotor1Port;
        this.elevatorMotor2Port = elevatorMotor2Port;
        this.coralIntakeMotorPort = coralIntakeMotorPort;

        this.elevatorMotor1 = new SparkMax(elevatorMotor1Port); // Initialize the first elevator motor
        this.elevatorMotor2 = new SparkMax(elevatorMotor2Port); // Initialize the second elevator motor
        this.coralIntakeMotor = new SparkMax(coralIntakeMotorPort); // Initialize the coral intake motor
    }


    // Check sensors and take action
    private void checkSensors() {
        boolean sensor1State = irSensor1.get();
        boolean sensor2State = irSensor2.get();
        if (sensor1State) {
            // First IR sensor is broken, disable the elevator and start the coral intake motor
            isElevatorEnabled = false;
            startCoralIntakeMotor();
            stopElevatorMotors(); // Disable elevator motors
        }

        if (sensor2State && !sensor1State) {
            // Second sensor is broken, keep everything the same
            // Elevator stays disabled, and the coral intake motor continues running
            // No action needed, everything stays the same
        }

        if (!sensor1State && !sensor2State) {
            // Both IR sensors are enabled, re-enable the elevator and stop the coral intake motor
            isElevatorEnabled = true;
            stopCoralIntakeMotor();
            enableElevatorMotors(); // Re-enable the elevator motors
        }
    }

    // Start the coral intake motor (Neo brushless motor)
    private void startCoralIntakeMotor() {
        if (!isMotorRunning) {
            coralIntakeMotor.start();  // Start the coral intake motor
            isMotorRunning = true;
            System.out.println("Coral intake motor started on port " + coralIntakeMotorPort);
        }
    }

    // Stop the coral intake motor
    private void stopCoralIntakeMotor() {
        if (isMotorRunning) {
            coralIntakeMotor.stop();  // Stop the coral intake motor
            isMotorRunning = false;
            System.out.println("Coral intake motor stopped on port " + coralIntakeMotorPort);
        }
    }

    // Disable the elevator motors
    private void stopElevatorMotors() {
        elevatorMotor1.stop();  // Stop the first elevator motor
        elevatorMotor2.stop();  // Stop the second elevator motor
        System.out.println("Elevator motors stopped.");
    }

    // Enable the elevator motors
    private void enableElevatorMotors() {
        elevatorMotor1.start();  // Start the first elevator motor
        elevatorMotor2.start();  // Start the second elevator motor
        System.out.println("Elevator motors enabled.");
    }

    // Method to check if the elevator is enabled
    public boolean isElevatorEnabled() {
        return Math.abs(elevatorMotor1.getSpeed()) > 0;
    }

    public void periodic() {
        this.isElevatorEnabled = elevatorMotor1.getSpeed() > 0 ; // Elevator starts enabled
        this.isMotorRunning = coralIntakeMotor.getSpeed() > 0;
    }

    //Everything below is just placeholders so no errors
    class SparkMax {
        private int motorPort;
        private double speed;


        public SparkMax(int motorPort) {
            this.motorPort = motorPort;
        }

        public double getSpeed() {
            return speed;
        }

        public void setSpeed(double speed) {
            this.speed = speed;
        }

        public void start() {
            setSpeed(1);
        }

        public void stop() {
            setSpeed(0);
        }
    }

    class DigitalInput {

        private int port;

        private boolean state;

        public DigitalInput(int port) {
            this.port = port;
        }

        public boolean get() {
            return state;
        }

    }

}

