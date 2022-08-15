package io.github.onepointconsulting.csvmarkdown;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Creates dummy data.
 */
public class CsvProvider {

    public static String createSingleLine() {
        return "John;Dow;Manchester;United\tKingdom";
    }

    public static String createMultiline() {
        return "John;Doe;Manchester;United\tKingdom\r\n"
                + "Mary;Jones;Manchester;United\tKingdom\r\n"
                + "Margaret;Johnson;London;United\tKingdom\r\n"
                + "Jake;Lewinson;Hertfordshire;Scotland\r\n"
                ;
    }

    public static String createMultilineTab() {
        String s = createMultiline().replace("\t", " ");
        return s.replace(";", "\t");
    }

    public static String createMultilineWithHeader() {
        String header = "Name;Surname;City;Country\r\n";
        return header + createMultiline();
    }

    public static String loadFileFromCp(String location) throws IOException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        try(InputStream is = classloader.getResourceAsStream(location)) {
            if(is == null) {
                throw new FileNotFoundException(String.format("Could not find file: %s", location));
            }
            Scanner scanner = new Scanner(new InputStreamReader(is, StandardCharsets.UTF_8));
            scanner.useDelimiter("\\Z");
            return scanner.next();
        }
    }
}
