package com.cathaybk.test.util;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateTimeUtil {

    public static LocalDate convertBigDecimalToLocalDate(BigDecimal timestamp) {
        long millis = timestamp.longValue();

        Instant instant = Instant.ofEpochMilli(millis);
        return LocalDate.ofInstant(instant, ZoneId.systemDefault());

    }

    }
