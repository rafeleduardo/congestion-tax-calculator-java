package org.rafeleduardo.congestiontaxcalculator;


import org.junit.jupiter.api.Test;
import org.rafeleduardo.congestiontaxcalculator.model.Car;
import org.rafeleduardo.congestiontaxcalculator.model.Vehicle;
import org.rafeleduardo.congestiontaxcalculator.service.CongestionTaxCalculator;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CongestionTaxCalculatorTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void testGetTax() throws IOException {
		CongestionTaxCalculator calculator = new CongestionTaxCalculator("Gothenburg");
		Vehicle vehicle = new Car();  // Assuming Car implements Vehicle

		List<LocalDateTime> dates = List.of (
				LocalDateTime.parse("2013-01-14T21:00:00"),
				LocalDateTime.parse("2013-01-15T21:00:00"),
				LocalDateTime.parse("2013-02-07T06:23:27"),
				LocalDateTime.parse("2013-02-07T15:27:00"),
				LocalDateTime.parse("2013-02-08T06:27:00"),
				LocalDateTime.parse("2013-02-08T06:20:27"),
				LocalDateTime.parse("2013-02-08T14:35:00"),
				LocalDateTime.parse("2013-02-08T15:29:00"),
				LocalDateTime.parse("2013-02-08T15:47:00"),
				LocalDateTime.parse("2013-02-08T16:01:00"),
				LocalDateTime.parse("2013-02-08T16:48:00"),
				LocalDateTime.parse("2013-02-08T17:49:00"),
				LocalDateTime.parse("2013-02-08T18:29:00"),
				LocalDateTime.parse("2013-02-08T18:35:00"),
				LocalDateTime.parse("2013-03-26T14:25:00"),
				LocalDateTime.parse("2013-03-28T14:07:27")
		);

		int tax = calculator.getTax(vehicle, dates);
		assertEquals(89, tax);  // Example expected value
	}
}
