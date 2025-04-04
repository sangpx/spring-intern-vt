package com.demo.project_intern.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;

@Slf4j
public class WriteToDisk {
    public static void saveToLocalDisk(byte[] data, String name) {
        String directoryPath = "D:/excel";
        String filePath = directoryPath + "/" + name + "_export_" + System.currentTimeMillis() + ".xlsx";
        try {
            // create folder if folder not existed
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            // Write file
            try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
                fileOutputStream.write(data);
                log.info("File saved to: " + filePath);
            }
        } catch (Exception e) {
            log.error("Error saving file to local disk: " + e.getMessage());
        }
    }
}
