package com.edo.file.model;

public class Field {

    private String name;
    private FieldType type;
    private Integer position;
    private Integer length;
    private String value;

    public Field(String name, FieldType type, Integer position) {
        this.name = name;
        this.type = type;
        this.position = position;
    }

    public Field(String name, FieldType type, Integer position, Integer length) {
        this(name, type, position);
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FieldType getType() {
        return type;
    }

    public void setType(FieldType type) {
        this.type = type;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return name + ": " + value;
    }
}
