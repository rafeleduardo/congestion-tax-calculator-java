package org.rafeleduardo.congestiontaxcalculator.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.rafeleduardo.congestiontaxcalculator.utils.LocalDateTimeDeserializer;

import java.time.LocalDateTime;
import java.util.List;

public record CongestionTaxRequest (
        String vehicleType,
        @JsonDeserialize(using = LocalDateTimeDeserializer.class) List<LocalDateTime> dates
) {

}
