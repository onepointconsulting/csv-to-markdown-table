package com.onepointltd.csvmarkdown;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import static java.lang.String.*;

/**
 * Used to convert CSV to Mark down.
 */
public class CsvToMarkdown {

    public static final String TAB_REPLACEMENT = "    ";

    private CsvToMarkdown() {
    }

    static class CsvData {
        final List<List<String>> tabularData;
        final List<Integer> maxRowLen;

        private CsvData(List<List<String>> tabularData, List<Integer> maxRowLen) {
            this.tabularData = tabularData;
            this.maxRowLen = maxRowLen;
        }
    }

    public static String convertCsvToMarkDown(String csvContent, final String delimiter, final boolean hasHeader) {
        csvContent = replaceTabs(csvContent, delimiter);

        CsvData csvData = generateCsvData(csvContent, delimiter);

        StringBuilder headerOutput = new StringBuilder();
        StringBuilder separatorOutput = new StringBuilder();

        for (int len : csvData.maxRowLen) {
            int sizer = len + 1 + 2;
            separatorOutput.append("|").append(repeat(sizer, "-"));
            headerOutput.append("|").append(repeat(sizer, " "));
        }

        headerOutput.append(format("| %n"));
        separatorOutput.append(format("| %n"));

        if (hasHeader) {
            headerOutput.setLength(0);
        }

        StringBuilder rowOutput = new StringBuilder();
        int i = 0;
        for (Iterator<List<String>> iterator = csvData.tabularData.iterator(); iterator.hasNext(); i++) {
            List<String> col = iterator.next();
            int j = 0;
            for (Iterator<Integer> maxRowLenIterator = csvData.maxRowLen.iterator(); maxRowLenIterator.hasNext(); j++) {
                int len = maxRowLenIterator.next();
                String row = j >= col.size() ? "" : col.get(j);
                String spacing = repeat((len - row.length()) + 1, " ");
                String out = format("| %s%s ", row, spacing);
                if (hasHeader && i == 0) {
                    headerOutput.append(out);
                } else {
                    rowOutput.append(out);
                }
            }
            if (hasHeader && i == 0) {
                headerOutput.append(format("| %n"));
            } else {
                rowOutput.append(format("| %n"));
            }
        }
        return format("%s%s%s", headerOutput, separatorOutput, rowOutput);
    }

    public static List<String> createChunks(String content, int maxSize) {
        final String[] lines = splitLines(content);
        final List<String> resultArray = new ArrayList<>();
        final StringBuilder builder = new StringBuilder();
        int counter = 0;
        for (String line : lines) {
            counter += line.length();
            if(counter >= maxSize) {
                resultArray.add(builder.toString());
                builder.setLength(0);
                counter = line.length();
            }
            builder.append(format("%s%n", line));
        }
        if(builder.length() > 0) {
            resultArray.add(builder.toString());
            builder.setLength(0);
        }
        return resultArray;
    }

    static CsvData generateCsvData(String csvContent, final String delimiter) {
        String[] lines = splitLines(csvContent);
        List<List<String>> tabularData = new ArrayList<>();
        List<Integer> maxRowLen = new ArrayList<>();

        Pattern regex = Pattern.compile(delimiter + "(?![^\"]*\"\\B)");

        for (int i = 0; i < lines.length; i++) {
            String e = lines[i];
            if (i >= tabularData.size()) {
                tabularData.add(new ArrayList<>());
            }
            String[] row = regex.split(e);
            for (int ii = 0; ii < row.length; ii++) {
                String ee = row[ii];
                if (ii >= maxRowLen.size()) {
                    maxRowLen.add(0);
                }
                // escape pipes and backslashes
                ee = ee.replaceAll("[|\\\\]", "\\$1");
                maxRowLen.set(ii, Math.max(maxRowLen.get(ii), ee.length()));
                tabularData.get(i).add(ee);
            }
        }
        return new CsvData(tabularData, maxRowLen);
    }

    static String[] splitLines(String csvContent) {
        return csvContent.split("\r?\n");
    }

    static String repeat(int n, String s) {
        return join("", Collections.nCopies(n, s));
    }

    static String replaceTabs(String csvContent, String delimiter) {
        if (!"\t".equals(delimiter)) {
            csvContent = csvContent.replace("\t", TAB_REPLACEMENT);
        }
        return csvContent;
    }
}
