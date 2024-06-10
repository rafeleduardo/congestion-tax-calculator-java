package org.rafeleduardo.congestiontaxcalculator.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.rafeleduardo.congestiontaxcalculator.dto.TaxConfig;

import java.io.File;
import java.io.IOException;

public class ConfigService {
    private static final String CONFIGURATION_FILE = "src/main/resources/tax_rates.json";

    public TaxConfig getConfig() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(CONFIGURATION_FILE), TaxConfig.class);
    }

}
