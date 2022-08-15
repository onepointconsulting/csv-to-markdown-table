package io.github.onepointconsulting.csvmarkdown;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Factory used to create CSV Data objects.
 */
public class CsvDataFactory {

    public static CsvData createInstance(List<List<String>> tabularData, List<Integer> maxRowLen, boolean hasHeader) {
        tabularData = tabularData.stream().filter(l -> !l.isEmpty()).collect(Collectors.toList());
        CsvData csvData = new CsvData(tabularData, maxRowLen);
        if(hasHeader && !tabularData.isEmpty()) {
            csvData.setHeaders(tabularData.get(0));
        }
        return csvData;
    }
}
