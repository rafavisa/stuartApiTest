package com.stuart.api.tests;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileHelper {

    public static String resourceToString(String resourceFileName) throws IOException {
        File requestBodyFile = new File("src/test/resources/" + resourceFileName);
        return new String(Files.readAllBytes(Paths.get(requestBodyFile.getPath())));
    }
}
