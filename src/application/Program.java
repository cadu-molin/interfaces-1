package application;

import model.entities.CarRental;
import model.entities.Vehicle;
import model.services.BrazilTaxService;
import model.services.RentalService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) throws ParseException {
        Locale.setDefault(Locale.US);
        Scanner scan = new Scanner(System.in);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:ss");

        System.out.println("Enter rental data");
        System.out.println("Car model: ");
        String carModel = scan.nextLine();
        System.out.println("Pickup (dd/MM/yy hh:ss): ");
        Date start = sdf.parse(scan.nextLine());
        System.out.println("Return (dd/MM/yy hh:ss): ");
        Date finish = sdf.parse(scan.nextLine());

        CarRental cr = new CarRental(start, finish, new Vehicle(carModel));

        System.out.println("Enter price per hour: ");
        Double pricePerHour = scan.nextDouble();
        System.out.println("Enter price per day: ");
        Double pricePerDay = scan.nextDouble();

        RentalService rentalService = new RentalService(pricePerHour, pricePerDay, new BrazilTaxService());

        rentalService.processInvoice(cr);

        System.out.println("INVOICE: ");
        System.out.println("Basic payment: " + String.format("%.2f", cr.getInvoice().getBasicPayment()));
        System.out.println("tax: " + String.format("%.2f", cr.getInvoice().getTax()));
        System.out.println("Total payment: " + String.format("%.2f", cr.getInvoice().getTotalPayment()));

        scan.close();
    }
}
