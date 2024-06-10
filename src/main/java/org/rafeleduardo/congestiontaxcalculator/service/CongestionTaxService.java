package org.rafeleduardo.congestiontaxcalculator.service;

import org.rafeleduardo.congestiontaxcalculator.model.Vehicle;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CongestionTaxService {

    public int calculateTax(Vehicle vehicle, List<LocalDateTime> dates, String city) throws IOException {
        CongestionTaxCalculator calculator = new CongestionTaxCalculator(city);
        return calculator.getTax(vehicle, dates);
    }
}
