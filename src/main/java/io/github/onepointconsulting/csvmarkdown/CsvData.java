package io.github.onepointconsulting.csvmarkdown;

import java.util.List;

/**
 * Extracts the data and the maximum length of each field.
 */
public class CsvData {
    private List<String> headers;

    private final List<List<String>> tabularData;

    private final List<Integer> maxRowLen;
    CsvData(List<List<String>> tabularData, List<Integer> maxRowLen) {
        this.tabularData = tabularData;
        this.maxRowLen = maxRowLen;
    }

    public List<List<String>> getTabularData() {
        return tabularData;
    }

    public List<Integer> getMaxRowLen() {
        return maxRowLen;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }
}
