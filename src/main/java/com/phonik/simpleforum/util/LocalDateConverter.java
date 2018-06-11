package com.phonik.simpleforum.util;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Date;
import java.time.LocalDate;

/**
 * Class handles conversion between Date type nad LocalDate
 * */

@Converter(autoApply = true)
public class LocalDateConverter implements AttributeConverter<LocalDate, Date> {

    @Override
    public Date convertToDatabaseColumn(LocalDate attribute) {
        if (attribute != null) {
            return Date.valueOf(attribute);
        } else {
            return null;
        }
    }

    @Override
    public LocalDate convertToEntityAttribute(Date dbData) {
        if (dbData != null) {
            return dbData.toLocalDate();
        } else {
            return null;
        }
    }
}
