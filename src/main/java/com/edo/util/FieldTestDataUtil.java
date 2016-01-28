package com.edo.util;

import com.edo.file.builder.FileSchemaBuilder;
import com.edo.file.model.Field;
import com.edo.file.model.FieldType;
import com.edo.file.model.FileSchema;
import com.edo.file.model.FileType;

import java.util.ArrayList;
import java.util.List;

public class FieldTestDataUtil {

    public static FileSchema createDelimitedStandardSchema() {
        return createDelimitedSchema("Standard Delimited File", ",", createFieldsForStandardFile(), FileType.DELIMITED);
    }

    public static FileSchema createFixedWidthStandardSchema() {
        return createDelimitedSchema("Standard Delimited File", "\t", createFieldsForFixedWidthStandardFile(), FileType.FIXED);
    }

    public static FileSchema createDelimitedSchema(String name, String delimiter, List<Field> fields, FileType fileType) {
        return new FileSchemaBuilder()
                .buildWithName(name)
                .buildWithDelimiter(delimiter)
                .buildWithFields(fields)
                .buildWithFileType(fileType)
                .build();
    }

    public static List<Field> createFieldsForDelimitedFile() {
        List<Field> fields = new ArrayList<>();
        fields.add(createField("cardholder", FieldType.STRING, 1));
        fields.add(createField("token", FieldType.STRING, 2));
        fields.add(createField("number1", FieldType.INTEGER, 3));
        fields.add(createField("number2", FieldType.INTEGER, 4));
        fields.add(createField("fi", FieldType.STRING, 5));
        return fields;
    }

    //length schema: 10,10,10,5,10;
    public static List<Field> createFieldsForFixedWidthFile() {
        List<Field> fields = new ArrayList<>();
        fields.add(createField("cardholder", FieldType.STRING, 1, 10));
        fields.add(createField("token", FieldType.STRING, 2, 10));
        fields.add(createField("number1", FieldType.INTEGER, 3, 10));
        fields.add(createField("number2", FieldType.INTEGER, 4, 5));
        fields.add(createField("fi", FieldType.STRING, 5, 10));
        return fields;
    }

    public static List<Field> createFieldsForStandardFile() {
        List<Field> fields = new ArrayList<>();
        fields.add(createField("fi", FieldType.STRING, 1));
        fields.add(createField("cardholder", FieldType.STRING, 2));
        fields.add(createField("number1", FieldType.INTEGER, 3));
        fields.add(createField("number2", FieldType.INTEGER, 4));
        return fields;
    }

    public static List<Field> createFieldsForFixedWidthStandardFile() {
        List<Field> fields = new ArrayList<>();
        fields.add(createField("fi", FieldType.STRING, 1, 8));
        fields.add(createField("cardholder", FieldType.STRING, 2, 10));
        fields.add(createField("number1", FieldType.INTEGER, 3, 1));
        fields.add(createField("number2", FieldType.INTEGER, 4, 1));
        return fields;
    }

    public static Field createField(String name, FieldType fieldType, Integer position) {
        return new Field(name, fieldType, position);
    }

    public static Field createField(String name, FieldType fieldType, Integer position, Integer length) {
        return new Field(name, fieldType, position, length);
    }
}
