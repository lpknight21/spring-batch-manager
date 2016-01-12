package com.edo.util;

import com.edo.file.Field;
import com.edo.file.FileSchema;
import com.edo.file.FileType;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FieldTestUtil {

    public static void convertFile(FileSchema currentFileSchema, FileSchema standardSchema, String line) {
       if(currentFileSchema.getType() == FileType.FIXED) {
           convertFixedWidthFile(currentFileSchema, standardSchema, line);
       } else {
           convertDelimitedFile(currentFileSchema, standardSchema, line);
       }
    }

    //Step 1: Put lines values into current Schema
    //FileType matters here.
    private static void convertDelimitedFile(FileSchema currentFileSchema, FileSchema standardSchema, String line) {
        String[] split = StringUtils.split(line, currentFileSchema.getDelimiter());
        for(Field field : currentFileSchema.getFields()) {
            field.setValue(split[field.getPosition()-1]);
        }

        updateStandardSchemaWithValues(currentFileSchema, standardSchema);
        printFieldValues(standardSchema);
    }

    private static void convertFixedWidthFile(FileSchema currentFileSchema, FileSchema standardSchema, String line) {
        Integer linePosition = 0;
        for(Field field : currentFileSchema.getFields()) {
            String fieldValue = StringUtils.substring(line, linePosition, field.getLength()+linePosition);
            field.setValue(fieldValue.trim());
            linePosition += field.getLength();
        }

        updateStandardSchemaWithValues(currentFileSchema, standardSchema);
        printFieldValues(standardSchema);
    }
    //End Step 1

    //Step 2: Put values from the current schema into the new schema.
    //FileType does NOT matter here.
    private static void updateStandardSchemaWithValues(FileSchema currentFileSchema, FileSchema standardSchema) {
        Map<String, Field> fieldMap = FieldTestUtil.convertFieldsToMap(currentFileSchema.getFields());

        for(Field field : standardSchema.getFields()) {
            Field prevFileField = fieldMap.get(field.getName());
            field.setValue(prevFileField.getValue());
        }
    }

    public static Map<String, Field> convertFieldsToMap(List<Field> fields) {
        Map<String, Field> fieldMap = new HashMap<>();
        for(Field field : fields) {
            fieldMap.put(field.getName(), field);
        }
        return fieldMap;
    }
    //End Step 2

    //Step 3: Printing the line of the standard file schema.
    //FileType matters here.
    public static void printFieldValues(FileSchema fileSchema) {
        System.out.println(FieldTestUtil.fieldsToLine(fileSchema));
    }

    public static String fieldsToLine(FileSchema fileSchema) {
        if(fileSchema.getType() == FileType.FIXED) {
            return fieldsToFixedWidthLine(fileSchema);
        } else {
            return fieldsToDelimitedLine(fileSchema);
        }
    }

    public static String fieldsToFixedWidthLine(FileSchema fileSchema) {
        StringBuilder line = new StringBuilder();
        for(Field field : fileSchema.getFields()) {
            String value = StringUtils.rightPad(field.getValue(), field.getLength());
            line.append(value);
        }
        return line.toString();
    }

    public static String fieldsToDelimitedLine(FileSchema fileSchema) {
        List<String> fieldValues = new ArrayList<>();
        for(Field field : fileSchema.getFields()) {
            fieldValues.add(field.getValue());
        }
        return StringUtils.join(fieldValues, fileSchema.getDelimiter());
    }
    //End Step 3
}
