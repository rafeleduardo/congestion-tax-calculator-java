package org.rafeleduardo.congestiontaxcalculator.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class CityConfig {
    private List<TimeRange> taxRates;
    private Set<String> holidays;
}
