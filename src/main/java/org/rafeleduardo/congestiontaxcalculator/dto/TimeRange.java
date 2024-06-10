package org.rafeleduardo.congestiontaxcalculator.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import org.rafeleduardo.congestiontaxcalculator.utils.LocalDateTimeDeserializer;
import org.rafeleduardo.congestiontaxcalculator.utils.LocalTimeDeserializer;

import java.time.LocalTime;

@Getter
@Setter
public class TimeRange {
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    private LocalTime startTime;
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    private LocalTime endTime;
    private int fee;
}
