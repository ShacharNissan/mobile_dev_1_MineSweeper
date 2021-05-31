package com.shacharnissan.minesweeper.logic;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;

public class FileHelper {

    private static FileWriter fw;

    public static void saveScoresJSONToFile(List<Score> scores, String filePath, String fileName) throws IOException {
        Gson gson = new Gson();
        createPathIfNeeded(filePath);
        FileWriter fw = new FileWriter(filePath + "" + fileName);
        gson.toJson(scores, fw);
        fw.flush(); //flush data to file   <---
        fw.close(); //close write
    }

    private static void createPathIfNeeded(String filePath) {
        File direct = new File(filePath);
        if(!direct.exists()) {
            direct.mkdirs();
        }
    }

    public static List<Score> readScoresFromFile(String filePath, String fileName) {
        try {
            Gson gson = new Gson();
            Reader jsonReader = new FileReader(filePath + "" + fileName);
            Type Score_list_type = new TypeToken<List<Score>>() {}.getType();
            List<Score> s  = gson.fromJson(jsonReader, Score_list_type);
            jsonReader.close();
            return s;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
