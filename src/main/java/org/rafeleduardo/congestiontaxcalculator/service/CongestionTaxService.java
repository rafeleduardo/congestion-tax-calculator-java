package org.rafeleduardo.congestiontaxcalculator.service;

import org.rafeleduardo.congestiontaxcalculator.model.Vehicle;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CongestionTaxService {

    public int calculateTax(Vehicle vehicle, List<LocalDateTime> dates) {
        CongestionTaxCalculator calculator = new CongestionTaxCalculator();
        return calculator.getTax(vehicle, dates);
    }
}
