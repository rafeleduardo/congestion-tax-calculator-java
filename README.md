# Congestion Tax Calculator

## Overview
This application calculates congestion tax fees for vehicles in the Gothenburg area based on predefined rules. It supports tax calculations for different time intervals and exempt vehicle types.

## Requirements
- Java 17 or higher
- Gradle

## Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/rafeleduardo/congestion-tax-calculator-java.git
   ```
2. Navigate to the project directory:
   ```bash
   cd congestion-tax-calculator-java
   ```
   
3. Build the project using Gradle:
   ```bash
   ./gradlew build
   ```

## Usage
1. Run the application:
   ```bash
   ./gradlew bootRun
   ```
   If you're on Windows, use gradlew.bat instead:

   ```bash
   gradlew.bat bootRun
   ```

2. The application will load the tax rates configuration from the tax_rates.json file located in the src/main/resources directory.
3. Use the application to calculate congestion tax fees for vehicles by providing the vehicle type and entry dates.
   
## Configuration
The tax rates and exempt vehicle types are configured in the tax_rates.json file. Edit this file to modify the tax rates for different time intervals.

## Testing
Run the unit tests using Maven:
   ```bash
   ./gradlew test
   ```

### Using Postman
1. Open Postman.
2. Create a new request with the following details:
* Method: POST
* URL: http://localhost:8080/api/v1/taxes/calculate?city=Gothenburg
* Body: JSON
```json
{
    "vehicleType": "Car",
    "dates": [
        "2013-01-14 21:00:00",
        "2013-01-15 21:00:00",
        "2013-02-07 06:23:27",
        "2013-02-07 15:27:00",
        "2013-02-08 06:27:00",
        "2013-02-08 06:20:27",
        "2013-02-08 14:35:00",
        "2013-02-08 15:29:00"
    ]
}
```
3. Send the request.

### Using cURL
You can also use cURL to make a request to the API:

```bash
curl -X POST http://localhost:8080/api/v1/taxes/calculate?city=Gothenburg \
-H "Content-Type: application/json" \
-d '{
    "vehicleType": "Car",
    "dates": [
        "2013-01-14 21:00:00",
        "2013-01-15 21:00:00",
        "2013-02-07 06:23:27",
        "2013-02-07 15:27:00",
        "2013-02-08 06:27:00",
        "2013-02-08 06:20:27",
        "2013-02-08 14:35:00",
        "2013-02-08 15:29:00"
    ]
}'
```

