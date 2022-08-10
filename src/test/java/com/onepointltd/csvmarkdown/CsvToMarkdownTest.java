package com.onepointltd.csvmarkdown;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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
        CsvToMarkdown.CsvData csvData = CsvToMarkdown.generateCsvData(csvLines, ";");
        assertEquals(4, csvData.maxRowLen.size());
        assertEquals(14, csvData.maxRowLen.get(3));
        assertEquals(4, csvData.tabularData.size());
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
}