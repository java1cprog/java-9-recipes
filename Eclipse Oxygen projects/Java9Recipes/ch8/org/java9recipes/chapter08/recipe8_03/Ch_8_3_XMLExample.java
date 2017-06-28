package org.java9recipes.chapter08.recipe8_03;

import org.java9recipes.chapter08.recipe8_01.ProgramSettings;
import java.awt.*;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * User: Freddy
 * Encoder XML Example
 */
public class Ch_8_3_XMLExample {
    public static void main(String[] args) {
        Ch_8_3_XMLExample example = new Ch_8_3_XMLExample();
        example.start();
    }

    private void start() {
        ProgramSettings settings = new ProgramSettings(new Point(10,10),new Dimension(300,200), Color.blue, "The title of the application" );
        try {
            FileSystem fileSystem = FileSystems.getDefault();
            try (FileOutputStream fos = new FileOutputStream("settings.xml"); XMLEncoder encoder = new XMLEncoder(fos)) {
                encoder.setExceptionListener((Exception e) -> {
                    System.out.println("Exception! :"+e.toString());
                });
                encoder.writeObject(settings);
            }
            try (FileInputStream fis = new FileInputStream("settings.xml"); XMLDecoder decoder = new XMLDecoder(fis)) {
                ProgramSettings decodedSettings = (ProgramSettings) decoder.readObject();
                System.out.println("Is same? "+settings.equals(decodedSettings));
            }

            Path file= fileSystem.getPath("settings.xml");
            List<String> xmlLines = Files.readAllLines(file, Charset.defaultCharset());
            xmlLines.stream().forEach((line) -> {
                System.out.println(line);
            });


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
