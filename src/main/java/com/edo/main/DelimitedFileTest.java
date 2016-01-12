package com.edo.main;

import com.edo.file.FileSchema;
import com.edo.file.FileType;
import com.edo.util.FieldTestDataUtil;
import com.edo.util.FieldTestUtil;

public class DelimitedFileTest {

    private static final String DELIMITED_FILE_LINE = "cardholder|token|3|4|fiid";

    public static void main(String[] args) {

        FileSchema delimitedSchema = FieldTestDataUtil.createDelimitedSchema("Basic Delimited File", "|",
                FieldTestDataUtil.createFieldsForDelimitedFile(), FileType.DELIMITED);

        FileSchema standardSchema = FieldTestDataUtil.createDelimitedStandardSchema();

        FieldTestUtil.convertFile(delimitedSchema, standardSchema, DELIMITED_FILE_LINE);
    }
}
