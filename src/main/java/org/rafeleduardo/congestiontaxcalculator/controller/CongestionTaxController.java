package org.rafeleduardo.congestiontaxcalculator.controller;

import org.rafeleduardo.congestiontaxcalculator.model.Car;
import org.rafeleduardo.congestiontaxcalculator.model.CongestionTaxRequest;
import org.rafeleduardo.congestiontaxcalculator.model.Motorcycle;
import org.rafeleduardo.congestiontaxcalculator.model.Vehicle;
import org.rafeleduardo.congestiontaxcalculator.service.CongestionTaxService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/taxes")
public class CongestionTaxController {
    private final CongestionTaxService congestionTaxService;

    public CongestionTaxController(CongestionTaxService congestionTaxService) {
        this.congestionTaxService = congestionTaxService;
    }

    @PostMapping("/calculate")
    public ResponseEntity<Integer> calculateTax(@RequestBody CongestionTaxRequest request) {
        Vehicle vehicle = createVehicle(request.vehicleType());
        int tax = congestionTaxService.calculateTax(vehicle, request.dates());
        return ResponseEntity.ok(tax);
    }

    private Vehicle createVehicle(String vehicleType) {
        return switch (vehicleType) {
            case "Car" -> new Car();
            case "Motorbike" -> new Motorcycle();
            default -> throw new IllegalArgumentException("Unknown vehicle type: " + vehicleType);
        };
    }
}
