package com.edo.file.builder;

import com.edo.file.model.Field;
import com.edo.file.model.FileSchema;
import com.edo.file.model.FileType;

import java.util.ArrayList;
import java.util.List;

public class FileSchemaBuilder {

    private String name = "";
    private FileType fileType = FileType.DELIMITED;
    private List<Field> fields = new ArrayList<>();
    private String delimiter = "|";
    private String date = "20151104";

    public FileSchema build() {
        FileSchema schema = new FileSchema();
        schema.setName(name);
        schema.setType(fileType);
        schema.setFields(fields);
        schema.setDelimiter(delimiter);
        schema.setDate(date);
        return schema;
    }

    public FileSchemaBuilder buildWithName(String name) {
        this.name = name;
        return this;
    }

    public FileSchemaBuilder buildWithFileType(FileType fileType) {
        this.fileType = fileType;
        return this;
    }

    public FileSchemaBuilder buildWithFields(List<Field> fields) {
        this.fields = fields;
        return this;
    }

    public FileSchemaBuilder buildWithDelimiter(String delimiter) {
        this.delimiter = delimiter;
        return this;
    }

    public FileSchemaBuilder buildWithDate(String date) {
        this.date = date;
        return this;
    }
}
