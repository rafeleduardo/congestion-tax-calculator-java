package org.rafeleduardo.congestiontaxcalculator.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class TaxConfig {
    private Map<String, CityConfig> cities;
}
