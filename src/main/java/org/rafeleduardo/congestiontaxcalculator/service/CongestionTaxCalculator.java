package org.rafeleduardo.congestiontaxcalculator.service;

import org.rafeleduardo.congestiontaxcalculator.model.Vehicle;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class CongestionTaxCalculator {

    private static final Map<String, Integer> tollFreeVehicles = new HashMap<>();

    static {
        tollFreeVehicles.put("Motorcycle", 0);
        tollFreeVehicles.put("Bus", 1);
        tollFreeVehicles.put("Emergency", 2);
        tollFreeVehicles.put("Diplomat", 3);
        tollFreeVehicles.put("Foreign", 4);
        tollFreeVehicles.put("Military", 5);
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

        int hour = date.getHour();
        int minute = date.getMinute();

        if (hour == 6 && minute <= 29) return 8;
        else if (hour == 6) return 13;
        else if (hour == 7) return 18;
        else if (hour == 8 && minute <= 29) return 13;
        else if (hour >= 8 && hour <= 14) return 8;
        else if (hour == 15 && minute <= 29) return 13;
        else if (hour == 15 || hour == 16) return 18;
        else if (hour == 17) return 13;
        else if (hour == 18 && minute <= 29) return 8;
        else return 0;
    }

    private Boolean isTollFreeDate(LocalDateTime date) {
        int dayOfMonth = date.getDayOfMonth();
        int year = date.getYear();
        int month = date.getMonthValue();
        int dayOfWek = date.getDayOfWeek().getValue();

        if (dayOfWek == 6 || dayOfWek == 7) return true;

        if (year == 2013) {
            return (month == 1 && dayOfMonth == 1) ||
                    (month == 3 && (dayOfMonth == 28 || dayOfMonth == 29)) ||
                    (month == 4 && (dayOfMonth == 1 || dayOfMonth == 30)) ||
                    (month == 5 && (dayOfMonth == 1 || dayOfMonth == 8 || dayOfMonth == 9)) ||
                    (month == 6 && (dayOfMonth == 5 || dayOfMonth == 6 || dayOfMonth == 21)) ||
                    (month == 7) ||
                    (month == 11 && dayOfMonth == 1) ||
                    (month == 12 && (dayOfMonth == 24 || dayOfMonth == 25 || dayOfMonth == 26 || dayOfMonth == 31));
        }
        return false;
    }
}
