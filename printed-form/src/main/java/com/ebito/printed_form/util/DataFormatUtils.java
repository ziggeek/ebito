package com.ebito.printed_form.util;

import com.ibm.icu.text.Transliterator;
import lombok.experimental.UtilityClass;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isBlank;

@UtilityClass
public class DataFormatUtils {

    public static final String DATA_FORMAT = "dd.MM.yyyy";
    public static final String TIME_FORMAT = "HH:mm:ss";

    public static final String DECIMAL_FORMAT = "#0.00";
    public static final String CYRILLIC_TO_LATIN = "Russian-Latin/BGN";
    public static final Transliterator LATIN_TRANSLITERATOR = Transliterator.getInstance(CYRILLIC_TO_LATIN);

    public static String getDateInRightFormat(final LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern(DATA_FORMAT));
    }
    public static String getTimeInRightFormat(final LocalTime time) {
        return time.format(DateTimeFormatter.ofPattern(TIME_FORMAT));
    }

    public static String getFullName(final String lastName, final String firstName, final String middleName) {
        final List<String> names = new ArrayList<>(3);
        names.add(lastName);
        names.add(firstName);
        if (middleName != null) {
            names.add(middleName);
        }

        return joinSeveralFieldsWithDelimiter(names, " ");
    }

    public static String joinSeveralFieldsWithDelimiter(final List<String> fields, final String delimiter) {
        return fields.stream()
                .filter(el -> !isBlank(el))
                .collect(Collectors.joining(delimiter));
    }

    public static String translitirationToLatin(final List<String> strings, final String delimiter) {
        return strings.stream()
                .map(LATIN_TRANSLITERATOR::transliterate)
                .collect(Collectors.joining(delimiter));
    }

    public static String formatBigDecimal(@Nullable BigDecimal bigDecimal,
                                          @Nullable Integer scale,
                                          @Nullable Character decimalDelimiter) {
        if(isNull(bigDecimal)) {
            bigDecimal = BigDecimal.ZERO;
        }
        if(nonNull(scale)) {
            bigDecimal = bigDecimal.setScale(scale, RoundingMode.HALF_UP);
        }
        if (isNull(decimalDelimiter)) {
            decimalDelimiter = '.';
        }
        final String formatted = String.valueOf(bigDecimal);

        return decimalDelimiter == '.' ? formatted : formatted.replace('.', decimalDelimiter);
    }
}
