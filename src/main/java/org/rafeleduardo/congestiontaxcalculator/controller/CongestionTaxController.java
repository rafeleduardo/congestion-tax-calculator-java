package org.rafeleduardo.congestiontaxcalculator.controller;

import org.rafeleduardo.congestiontaxcalculator.model.*;
import org.rafeleduardo.congestiontaxcalculator.service.CongestionTaxService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            case "Motorcycle" -> new Motorcycle();
            case "Bus" -> new Bus();
            case "Emergency" -> new Emergency();
            case "Diplomat" -> new Diplomat();
            case "Foreign" -> new Foreign();
            case "Military" -> new Military();
            default -> throw new IllegalArgumentException("Unknown vehicle type: " + vehicleType);
        };
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }
}
