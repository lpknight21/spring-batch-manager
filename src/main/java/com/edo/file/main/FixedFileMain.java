package com.edo.file.main;

public class FixedFileMain {

    //length schema: 10,10,10,5,10;
    private static final String FIXED_FILE_LINE = "cardholdertoken     3         4    fiid      ";

    /*public static void main(String[] args) {

        FileSchema fixedWidthSchema = FieldTestDataUtil.createDelimitedSchema("Raws Fixed Width File", "",
                FieldTestDataUtil.createFieldsForFixedWidthFile(), FileType.FIXED);

        FileSchema standardSchema = FieldTestDataUtil.createDelimitedStandardSchema();

        FieldTestUtil.convertFile(fixedWidthSchema, standardSchema, FIXED_FILE_LINE);
    }*/
}
