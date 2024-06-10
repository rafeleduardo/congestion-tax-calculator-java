package org.rafeleduardo.congestiontaxcalculator.model;

public class Emergency implements Vehicle {
    @Override
    public String getVehicleType() {
        return "Emergency";
    }
}
