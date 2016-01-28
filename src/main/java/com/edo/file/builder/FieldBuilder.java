package com.edo.file.builder;

import com.edo.file.model.Field;
import com.edo.file.model.FieldType;

public class FieldBuilder {

    private String name = "name";
    private FieldType type = FieldType.STRING;
    private Integer position = 0;
    private Integer length = 0;
    private String value = "value";

    public Field build() {
        Field field = new Field(name, type, position, length);
        field.setValue(value);
        return field;
    }

    public FieldBuilder buildWithName(String name) {
        this.name = name;
        return this;
    }

    public FieldBuilder buildWithType(FieldType type) {
        this.type = type;
        return this;
    }

    public FieldBuilder buildWithPosition(Integer position) {
        this.position = position;
        return this;
    }

    public FieldBuilder buildWithLength(Integer length) {
        this.length = length;
        return this;
    }

    public FieldBuilder buildWithValue(String value) {
        this.value = value;
        return this;
    }
}
