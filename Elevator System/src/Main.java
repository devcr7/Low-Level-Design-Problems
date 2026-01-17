//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import controller.ElevatorController;
import enums.Direction;
import enums.SchedulingStrategy;
import model.Building;
import model.Elevator;
import observer.impl.ElevatorDisplay;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Building building = new Building("Office tower", 10, 3);
        ElevatorController controller = building.getElevatorController();

        ElevatorDisplay display = new ElevatorDisplay();
        for (Elevator elevator : controller.getElevators()) {
            elevator.addObserver(display);
        }

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("Elevator system Simulation");
        System.out.println("Building: " + building.getName());
        System.out.println("Floors: " + building.getNumberOfFloors());
        System.out.println("Elevators: " + controller.getElevators().size());

        while (running) {
            System.out.println("nSelect an option:");
            System.out.println("1. Request elevator (external)");
            System.out.println("2. Request floor (internal)");
            System.out.println("3. Simulate next step");
            System.out.println("4. Change scheduling strategy");
            System.out.println("5. Exit simulation");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> {
                    System.out.print("Enter elevator ID: ");
                    int elevatorID = scanner.nextInt();

                    controller.setCurrentElevatorId(elevatorID);

                    System.out.print("Enter floor number: ");
                    int floorNum = scanner.nextInt();
                    System.out.print("Direction (1 for UP, 2 for Down): ");
                    int direction = scanner.nextInt();

                    Direction targetDirection = direction == 1 ? Direction.UP : Direction.DOWN;
                    controller.requestElevator(elevatorID, floorNum, targetDirection);

                }
                case 2 -> {
                    System.out.print("Enter elevator ID: ");
                    int elevatorID = scanner.nextInt();

                    controller.setCurrentElevatorId(elevatorID);

                    System.out.println("Enter floor number: ");
                    int floorNum = scanner.nextInt();

                    controller.requestFloor(elevatorID, floorNum);
                }
                case 3 -> {
                    System.out.println("Simulating next step...");
                    controller.step();
                    displayElevatorStatus(controller.getElevators());
                }
                case 4 -> {
                    System.out.println("Select strategy");
                    System.out.println("1. Scan strategy");
                    System.out.println("2. FCFS strategy");
                    System.out.println("3. Look strategy");
                    int strategy = scanner.nextInt();
                    switch (strategy) {
                        case 1 -> {
                            controller.setSchedulingStrategy(SchedulingStrategy.SCAN);
                            System.out.println("Scan strategy started");
                        }
                        case 2 -> {
                            controller.setSchedulingStrategy(SchedulingStrategy.FCFS);
                            System.out.println("FCFS strategy started");
                        }
                        case 3 -> {
                            controller.setSchedulingStrategy(SchedulingStrategy.LOOK);
                            System.out.println("Look strategy started");
                        }
                        default -> System.out.println("Invalid strategy");
                    }
                }
                case 5 -> running = false;
                default -> System.out.println("Invalid choice");
            }
        }
        scanner.close();
        System.out.println("Simulation finished!");
    }
    private static void displayElevatorStatus(List<Elevator> elevators) {
        System.out.println("nElevator status:");
        for (Elevator elevator: elevators) {
            System.out.println("ELevator " + elevator.getId() + ": Floor "
                    + elevator.getCurrentFloor() + ", Direction "
                    + elevator.getDirection() + ", State " + elevator.getState()
                    + ", Destinations " + elevator.getDestinationFloors());
        }
    }
}