package com.edo.converter;

import com.edo.file.Field;
import com.edo.file.FileSchema;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LineConverter {

    public static String convert(String line, FileSchema rawFileSchema, FileSchema standardFileSchema) {
        String[] splitLine = StringUtils.split(line, rawFileSchema.getDelimiter());
        Map<String, Field> rawFieldMap = convertFieldListToMap(rawFileSchema.getFields());
        for(Field field : rawFileSchema.getFields()) {
            field.setValue(splitLine[field.getPosition()]);
        }
        for(Field field : standardFileSchema.getFields()) {
            field.setValue(rawFieldMap.get(field.getName()).getValue());
        }

        return line;
    }

    private static Map<String, Field> convertFieldListToMap(List<Field> fields) {
        Map<String, Field> fieldMap = new HashMap<>();
        for(Field field : fields) {
            fieldMap.put(field.getName(), field);
        }
        return fieldMap;
    }
}
