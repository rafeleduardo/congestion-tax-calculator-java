package org.rafeleduardo.congestiontaxcalculator.service;

import org.rafeleduardo.congestiontaxcalculator.config.ConfigService;
import org.rafeleduardo.congestiontaxcalculator.dto.CityConfig;
import org.rafeleduardo.congestiontaxcalculator.dto.TaxConfig;
import org.rafeleduardo.congestiontaxcalculator.dto.TimeRange;
import org.rafeleduardo.congestiontaxcalculator.model.Vehicle;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class CongestionTaxCalculator {

    private final ConfigService configService;
    private final CityConfig cityConfig;

    private static final Map<String, Integer> tollFreeVehicles = new HashMap<>();

    static {
        tollFreeVehicles.put("Motorcycle", 0);
        tollFreeVehicles.put("Bus", 1);
        tollFreeVehicles.put("Emergency", 2);
        tollFreeVehicles.put("Diplomat", 3);
        tollFreeVehicles.put("Foreign", 4);
        tollFreeVehicles.put("Military", 5);
    }

    public CongestionTaxCalculator(String city) throws IOException {
        this.configService = new ConfigService();
        TaxConfig taxConfig = configService.getConfig();
        this.cityConfig = taxConfig.getCities().get(city);
    }

    public int getTax(Vehicle vehicle, List<LocalDateTime> dates) {
        if (isTollFreeVehicle(vehicle) || dates == null || dates.isEmpty())
            return 0;

        SortedMap<LocalDate, List<LocalDateTime>> datesGroupedByDay = dates.stream()
                .collect(Collectors.groupingBy(LocalDateTime::toLocalDate, TreeMap::new, Collectors.collectingAndThen(
                        Collectors.toList(),
                        list -> list.stream()
                                .sorted()
                                .collect(Collectors.toList())
                )));

        int totalFee = 0;

        for (List<LocalDateTime> dayEntries : datesGroupedByDay.values()) {
            LocalDateTime intervalStart = null;
            int dailyFee = 0;


            for (LocalDateTime date : dayEntries) {
                if (intervalStart == null) intervalStart = date;

                int nextFee = getTollFee(date, vehicle);
                int tempFee = getTollFee(intervalStart, vehicle);

                long diffMinutes = java.time.Duration.between(intervalStart, date).toMinutes();

                if (diffMinutes <= 60) {
                    if (dailyFee > 0) dailyFee -= tempFee;
                    if (nextFee >= tempFee) tempFee = nextFee;
                    dailyFee += tempFee;
                } else {
                    dailyFee += nextFee;
                    intervalStart = date;
                }
            }

            if (dailyFee > 60) dailyFee = 60;
            totalFee += dailyFee;
        }

        return totalFee;
    }

    private boolean isTollFreeVehicle(Vehicle vehicle) {
        if (vehicle == null) return false;
        String vehicleType = vehicle.getVehicleType();
        return tollFreeVehicles.containsKey(vehicleType);
    }

    public int getTollFee(LocalDateTime date, Vehicle vehicle) {
        if (isTollFreeDate(date) || isTollFreeVehicle(vehicle)) return 0;

        LocalTime time = date.toLocalTime();
        int hour = time.getHour();
        int minute = time.getMinute();

        for (TimeRange timeRange : cityConfig.getTaxRates()) {
            if (isInTimeRange(hour, minute, timeRange.getStartTime(), timeRange.getEndTime())) {
                return timeRange.getFee();
            }
        }

        return 0;
    }

    private Boolean isTollFreeDate(LocalDateTime date) {
        if (date.getMonthValue() == 7) return true;

        if (date.getDayOfWeek().getValue() == 6 || date.getDayOfWeek().getValue() == 7) return true;

        return cityConfig.getHolidays().contains(date.toLocalDate().toString());
    }

    private boolean isInTimeRange(int hour, int minute, LocalTime startTime, LocalTime endTime) {
        LocalTime currentTime = LocalTime.of(hour, minute);
        return !currentTime.isBefore(startTime) && currentTime.isBefore(endTime);
    }
}
