package io.github.onepointconsulting.csvmarkdown;


import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CsvToMarkdownTest {

    @Test
    void whenConvertCsvToMarkDown_ShouldConvertMarkdown() {
        String csvLines = CsvProvider.createMultiline();
        String markdown = CsvToMarkdown.convertCsvToMarkDown(csvLines, ";", false);
        assertTrue(markdown.length() > 0);
        assertTrue(markdown.contains("| John      | Doe       | Manchester     | United    Kingdom  |"));
        System.out.println(markdown);
    }

    @Test
    void whenConvertCsvToMarkDown_ShouldConvertMarkdownWithHeader() {
        String csvLines = CsvProvider.createMultilineWithHeader();
        String markdown = CsvToMarkdown.convertCsvToMarkDown(csvLines, ";", true);
        assertTrue(markdown.length() > 0);
        assertTrue(markdown.contains("| John      | Doe       | Manchester     | United    Kingdom  |"));
        System.out.println(markdown);
    }

    @Test
    void whenCreateMultilineTab_ShouldConvertMarkdownWithHeader() {
        String csvLines = CsvProvider.createMultilineTab();
        String markdown = CsvToMarkdown.convertCsvToMarkDown(csvLines, "\t", false);
        assertTrue(markdown.length() > 0);
        assertTrue(markdown.contains("| John      | Doe       | Manchester     | United Kingdom  |"));
        System.out.println(markdown);
    }

    @Test
    void whenGenerateCsvData_ShouldHaveData() {
        String csvLines = CsvProvider.createMultiline();
        CsvData csvData = CsvToMarkdown.generateCsvData(csvLines, ";", true);
        assertEquals(4, csvData.getMaxRowLen().size());
        assertEquals(14, csvData.getMaxRowLen().get(3));
        assertEquals(4, csvData.getTabularData().size());
    }

    @Test
    void whenReplaceTabs_ShouldHaveNone() {
        String csvBit = CsvProvider.createSingleLine();
        String res = CsvToMarkdown.replaceTabs(csvBit, ";");
        assertTrue(res.contains(CsvToMarkdown.TAB_REPLACEMENT));
    }

    @Test
    void whenChunk_ChunksAreCreated() throws IOException {
        String content = CsvProvider.loadFileFromCp("json/example_large.txt");
        assertNotNull(content);
        int maxSize = 2000;
        List<String> chunks = CsvToMarkdown.createChunks(content, maxSize);
        assertTrue(chunks.get(0).length() <= maxSize);
        assertTrue(chunks.get(1).length() <= maxSize);
        String joined = String.join("", chunks);
        assertEquals(content.trim(), joined.trim());
    }

    @Test
    void whenLoadComplex_ShouldHaveCorrectCsvData() throws IOException {
        String content = CsvProvider.loadFileFromCp("csv/complex.csv");
        assertNotNull(content);
        CsvData csvData = CsvToMarkdown.generateCsvData(content, ",", true);
        assertNotNull(csvData);
        List<List<String>> tabularData = csvData.getTabularData();
        tabularData.forEach(l -> {
            assertEquals(4, l.size());
        });
    }
}