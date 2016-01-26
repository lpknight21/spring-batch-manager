package com.edo.converter;

import com.edo.builder.FileSchemaBuilder;
import com.edo.file.FileSchema;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LineConverterTest {

    @Test
    public void convert_emptyLine() {
        assertLineConvert("", "", new FileSchemaBuilder().build(), new FileSchemaBuilder().build());
    }

    @Test
    public void convert_oneField() {
        assertLineConvert("a", "a", new FileSchemaBuilder().build(), new FileSchemaBuilder().build());
    }

    @Test
    public void convert_twoFields() {
        assertLineConvert("a,b", "a|b", new FileSchemaBuilder().build(), new FileSchemaBuilder().build());
    }

    private void assertLineConvert(String expectedLine, String actualLine, FileSchema currentFileSchema, FileSchema standardFileSchema) {
        assertEquals(expectedLine, LineConverter.convert(actualLine, currentFileSchema, standardFileSchema));
    }
}