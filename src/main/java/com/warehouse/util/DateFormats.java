package com.warehouse.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public final class DateFormats {

    /** January 5, 2026 */
    public static final DateTimeFormatter DISPLAY =
            DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH);

    private DateFormats() {}

    public static String format(LocalDate date) {
        return date == null ? "" : date.format(DISPLAY);
    }
}