package com.example.saveatrainplaywrith;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import utils.ExcelReader;

import java.io.IOException;
import java.util.stream.Stream;

public class SaveATrainMixE2EForEurailTests {
    @Disabled("Disabled until DB connection will start.")
    @ParameterizedTest
    @MethodSource("excelDataProvider")
    void testUsingExcelData(String originUID, String destinationUID, String departureDatetime) {
        System.out.println(originUID);
        System.out.println(destinationUID);
        System.out.println(departureDatetime);
        // originUID, destinationUID, and departureDatetime corresponds to the columns in Excel sheet
    }

    static Stream<Arguments> excelDataProvider() throws IOException {
        String filePath = "/Users/rafalst/IdeaProjects/SaveATrainPlaywrith/src/test/resources/ConnectionsEurail.xlsx";
        return ExcelReader.readExcelData(filePath)
                .filter(arg -> isValidRow(arg.get()));
    }

    private static boolean isValidRow(Object[] records) {
        if (records.length != 3) return false;
        for (Object record : records) {
            String str = (String) record;
            if (str == null || str.equals("BLANK") || str.equals("UNKNOWN")) return false;
        }
        return true;
    }
}
