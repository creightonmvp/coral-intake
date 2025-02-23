public class ElevatorControl {

    private boolean isElevatorEnabled;
    private boolean isMotorRunning;

    // Simulating IR sensor states
    private boolean irSensor1Broken;
    private boolean irSensor2Broken;

    // Ports for the IR sensors and motors
    private int irSensor1Port;
    private int irSensor2Port;
    private int elevatorMotor1Port;
    private int elevatorMotor2Port;
    private int coralIntakeMotorPort;

    // Placeholder for Rev Max planetary motors (elevator motors) and Neo brushless motor (coral intake)
    private RevMaxPlanetaryMotor elevatorMotor1;
    private RevMaxPlanetaryMotor elevatorMotor2;
    private NeoBrushlessMotor coralIntakeMotor;

    public ElevatorControl(int irSensor1Port, int irSensor2Port, int elevatorMotor1Port, int elevatorMotor2Port, int coralIntakeMotorPort) {
        this.isElevatorEnabled = true; // Elevator starts enabled
        this.isMotorRunning = false;
        this.irSensor1Broken = false;
        this.irSensor2Broken = false;

        this.irSensor1Port = irSensor1Port;
        this.irSensor2Port = irSensor2Port;
        this.elevatorMotor1Port = elevatorMotor1Port;
        this.elevatorMotor2Port = elevatorMotor2Port;
        this.coralIntakeMotorPort = coralIntakeMotorPort;

        this.elevatorMotor1 = new RevMaxPlanetaryMotor(elevatorMotor1Port); // Initialize the first elevator motor
        this.elevatorMotor2 = new RevMaxPlanetaryMotor(elevatorMotor2Port); // Initialize the second elevator motor
        this.coralIntakeMotor = new NeoBrushlessMotor(coralIntakeMotorPort); // Initialize the coral intake motor
    }

    // Method to simulate the IR sensor input (using ports)
    public void setIrSensor1Broken(boolean state) {
        irSensor1Broken = state;
        System.out.println("IR Sensor 1 (Port " + irSensor1Port + ") broken: " + state);
        checkSensors();
    }

    public void setIrSensor2Broken(boolean state) {
        irSensor2Broken = state;
        System.out.println("IR Sensor 2 (Port " + irSensor2Port + ") broken: " + state);
        checkSensors();
    }

    // Check sensors and take action
    private void checkSensors() {
        if (irSensor1Broken) {
            // First IR sensor is broken, disable the elevator and start the coral intake motor
            isElevatorEnabled = false;
            startCoralIntakeMotor();
            stopElevatorMotors(); // Disable elevator motors
        }

        if (irSensor2Broken && !irSensor1Broken) {
            // Second sensor is broken, keep everything the same
            // Elevator stays disabled, and the coral intake motor continues running
            // No action needed, everything stays the same
        }

        if (!irSensor1Broken && !irSensor2Broken) {
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
        return isElevatorEnabled;
    }

    // Placeholder Rev Max planetary motor class (replace with actual motor API)
    class RevMaxPlanetaryMotor {
        private int motorPort;

        public RevMaxPlanetaryMotor(int motorPort) {
            this.motorPort = motorPort;
        }

        public void start() {
            // Motor starting logic (interface with actual motor hardware)
            System.out.println("Rev Max motor on port " + motorPort + " is now running.");
        }

        public void stop() {
            // Motor stopping logic
            System.out.println("Rev Max motor on port " + motorPort + " has stopped.");
        }
    }

    // Placeholder Neo brushless motor class (replace with actual motor API)
    class NeoBrushlessMotor {
        private int motorPort;

        public NeoBrushlessMotor(int motorPort) {
            this.motorPort = motorPort;
        }

        public void start() {
            // Motor starting logic (interface with actual motor hardware)
            System.out.println("Neo brushless motor on port " + motorPort + " is now running.");
        }

        public void stop() {
            // Motor stopping logic
            System.out.println("Neo brushless motor on port " + motorPort + " has stopped.");
        }
    }

    public static void main(String[] args) {
        // Specify the ports for the IR sensors and the motors
        int irSensor1Port = 40;  // Port 40 for the first IR sensor
        int irSensor2Port = 41;  // Port 41 for the second IR sensor
        int elevatorMotor1Port = 9;  // Port 9 for the first elevator motor
        int elevatorMotor2Port = 10;  // Port 10 for the second elevator motor
        int coralIntakeMotorPort = 11;  // Port 11 for the coral intake motor

        ElevatorControl elevatorControl = new ElevatorControl(irSensor1Port, irSensor2Port, elevatorMotor1Port, elevatorMotor2Port, coralIntakeMotorPort);

        // Simulate first sensor broken
        elevatorControl.setIrSensor1Broken(true);  // Elevator disabled and coral intake motor starts
        System.out.println("Elevator enabled: " + elevatorControl.isElevatorEnabled());

        // Simulate second sensor broken (no action needed)
        elevatorControl.setIrSensor2Broken(true);
        System.out.println("Elevator enabled: " + elevatorControl.isElevatorEnabled());

        // Simulate both sensors enabled (re-enable elevator and stop coral intake motor)
        elevatorControl.setIrSensor1Broken(false);
        elevatorControl.setIrSensor2Broken(false);
        System.out.println("Elevator enabled: " + elevatorControl.isElevatorEnabled());
    }
}
