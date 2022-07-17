package model.services;

import model.entities.CarRental;
import model.entities.Invoice;

public class RentalService {

    private Double pricePerDay;
    private Double pricePerHour;

    private TaxService taxService;

    public RentalService() {}

    public RentalService(Double pricePerDay, Double pricePerHour, TaxService taxService) {
        this.pricePerDay = pricePerDay;
        this.pricePerHour = pricePerHour;
        this.taxService = taxService;
    }

    public void processInvoice(CarRental carRental) {
         Long t1 = carRental.getStart().getTime();
         Long t2 = carRental.getFinish().getTime();
         Double hours = (double)(t2 -t1) / 1000 / 60 / 60;

         Double basicPayment;

         if (hours <= 12) {
             basicPayment = Math.ceil(hours) * pricePerHour;
         } else {
             basicPayment = Math.ceil(hours / 24) * pricePerDay;
         }

         Double tax = taxService.tax(basicPayment);

         carRental.setInvoice(new Invoice(basicPayment, tax));
    }
}
