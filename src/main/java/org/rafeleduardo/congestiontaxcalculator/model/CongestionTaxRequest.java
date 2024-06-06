package org.rafeleduardo.congestiontaxcalculator.model;

import java.time.LocalDateTime;

public record CongestionTaxRequest (
        String vehicleType,
        LocalDateTime[] dates
) {

}
