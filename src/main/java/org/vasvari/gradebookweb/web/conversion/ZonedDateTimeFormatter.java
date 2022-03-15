package org.vasvari.gradebookweb.web.conversion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class ZonedDateTimeFormatter implements Formatter<ZonedDateTime> {

    @Autowired
    private MessageSource messageSource;

    @Override
    public ZonedDateTime parse(String text, Locale locale) throws ParseException {
        //SimpleDateFormat dateFormat = createDateFormat(locale);
        //return dateFormat.parse(text);
        DateTimeFormatter dateTimeFormat = createDateTimeFormat(locale);

        return ZonedDateTime.from(dateTimeFormat.parse(text));
    }

    @Override
    public String print(ZonedDateTime zonedDateTime, Locale locale) {
        //SimpleDateFormat dateFormat = createDateTimeFormat(locale);
        //return dateFormat.format(date);
        DateTimeFormatter dateTimeFormat = createDateTimeFormat(locale);

        return dateTimeFormat.format(zonedDateTime);
    }

    private DateTimeFormatter createDateTimeFormat(Locale locale) {
        //SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        //dateFormat.setLenient(false);
        //return dateFormat;

        String format = messageSource.getMessage("date.format", null, locale);

        return DateTimeFormatter.ofPattern(format).withZone(ZoneId.systemDefault()).withLocale(locale);
    }
}
