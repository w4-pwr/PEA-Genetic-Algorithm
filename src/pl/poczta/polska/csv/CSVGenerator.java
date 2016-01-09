package pl.poczta.polska.csv;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Evelan-E6540 on 09.01.2016.
 */
public class CSVGenerator {

    String SEPARATOR = ";";

    public void createFile(int generations, int population, boolean random) {
        String filename = getFilename(generations, population, random);
        try {
            FileWriter writer = new FileWriter(filename);

            writer.append("Nazwa instancji");
            writer.append(SEPARATOR);
            writer.append("Czas");
            writer.append(SEPARATOR);
            writer.append("Poczatkowa dlugosc");
            writer.append(SEPARATOR);
            writer.append("Koncowa dlugosc");
            writer.append('\n');

            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addRecord(String filename, String tspName, long time, int initialPath, int finalPath) {
        try {
            FileWriter writer = new FileWriter(filename, true);
            writer.append(tspName);
            writer.append(SEPARATOR);
            writer.append(String.valueOf(time));
            writer.append(SEPARATOR);
            writer.append(String.valueOf(initialPath));
            writer.append(SEPARATOR);
            writer.append(String.valueOf(finalPath));
            writer.append('\n');
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String getFilename(int generations, int population, boolean random) {
        String baseName = "wyniki_gen";

        if (random)
            baseName = "random_wyniki_gen";
        return baseName + generations + "_pop" + population + ".csv";
    }
}
