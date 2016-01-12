package com.edo.main;

import com.edo.file.FileSchema;
import com.edo.file.FileType;
import com.edo.util.FieldTestDataUtil;
import com.edo.util.FieldTestUtil;

public class FixedFileTest {

    //length schema: 10,10,10,5,10;
    private static final String FIXED_FILE_LINE = "cardholdertoken     3         4    fiid      ";

    public static void main(String[] args) {

        FileSchema fixedWidthSchema = FieldTestDataUtil.createDelimitedSchema("Basic Fixed Width File", "",
                FieldTestDataUtil.createFieldsForFixedWidthFile(), FileType.FIXED);

        FileSchema standardSchema = FieldTestDataUtil.createDelimitedStandardSchema();

        FieldTestUtil.convertFile(fixedWidthSchema, standardSchema, FIXED_FILE_LINE);
    }
}
