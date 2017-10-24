package com.nextBook.database.util;

import javax.persistence.AttributeConverter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListConverter implements AttributeConverter<List<String>, String> {


    @Override
    public String convertToDatabaseColumn(List<String> strings) {
        if (strings != null) {
            return String.join(",", strings);
        }
        return null;
    }

    @Override
    public List<String> convertToEntityAttribute(String string) {
        if (string != null) {
            return new ArrayList<String>(Arrays.asList(string.split(",")));
        }
        return null;
    }
}
