import enums.PaymentMode;
import factory.PaymentStrategyFactory;
import factory.VehicleFactory;
import model.Address;
import model.Ticket;
import model.Vehicle;
import strategy.IParkingFeeStrategy;
import strategy.IPaymentStrategy;
import strategy.impl.BasicRateStrategy;
import strategy.impl.PremiumRateStrategy;
import utility.ParkingLot;


import java.util.Scanner;

import static enums.DurationType.HOURS;
import static enums.VehicleType.BIKE;
import static enums.VehicleType.CAR;

public class Main {
    public static void main(String[] args) {

        ParkingLot parkingLot = ParkingLot.getInstance(2, 2);

        IParkingFeeStrategy basicRateStrategy = new BasicRateStrategy();
        IParkingFeeStrategy premiumRateStrategy = new PremiumRateStrategy();

        Vehicle car1 = VehicleFactory.createVehicle(CAR, "CAR1", basicRateStrategy);
        Vehicle car2 = VehicleFactory.createVehicle(CAR, "CAR2", premiumRateStrategy);

        Vehicle bike1 = VehicleFactory.createVehicle(BIKE, "BIKE1", basicRateStrategy);
        Vehicle bike2 = VehicleFactory.createVehicle(BIKE, "BIKE2", premiumRateStrategy);

        Address car1Address = parkingLot.parkVehicle(car1);
        Address bike1Address = parkingLot.parkVehicle(bike1);

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter payment mode for " + car1.getLicencePlate());
        String mode = scanner.nextLine();

        Ticket car1Ticket;
        if (car1Address != null) {
            double carFee = car1.getParkingFee(2, HOURS);
            IPaymentStrategy carPaymentStrategy = PaymentStrategyFactory.getPaymentStrategy(PaymentMode.fromString(mode));
            carPaymentStrategy.processPayment(carFee);
            car1Ticket = new Ticket(carFee, car1Address);
            parkingLot.vacate(car1Ticket.getAddress());
        }

        System.out.println("Enter payment mode for " + bike1.getLicencePlate());
        mode = scanner.nextLine();

        Ticket bike1Ticket;
        if (bike1Address != null) {
            double bikeFee = bike2.getParkingFee(10, HOURS);
            IPaymentStrategy bikePaymentStrategy = PaymentStrategyFactory.getPaymentStrategy(PaymentMode.fromString(mode));
            bikePaymentStrategy.processPayment(bikeFee);
            bike1Ticket = new Ticket(bikeFee, bike1Address);
            parkingLot.vacate(bike1Ticket.getAddress());
        }

        Address car2Address = parkingLot.parkVehicle(car2);
        Address bike2Address = parkingLot.parkVehicle(bike2);

        scanner.close();
    }
}