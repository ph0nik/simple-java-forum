package com.phonik.simpleforum.util;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import java.sql.Date;

import java.sql.Timestamp;
import java.time.LocalDateTime;


/**
 * Class handles automatic conversion between Timestamp type and LocalDateTime
 * */

@Converter(autoApply = true)
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Timestamp> {

    @Override
    public Timestamp convertToDatabaseColumn(LocalDateTime attribute) {
        return (attribute != null) ? Timestamp.valueOf(attribute) : null;
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Timestamp dbData) {
        return (dbData != null) ? dbData.toLocalDateTime() : null;
    }
}
