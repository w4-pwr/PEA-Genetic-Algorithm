package pl.poczta.polska.filemanager;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import pl.poczta.polska.model.City;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * Created by Evelan-E6540 on 26.12.2015.
 */
public class TSPFileParserImpl implements TSPFileParser {

    FileReader fr = null;
    BufferedReader bfr;

    String tspFileExtension = ".tsp";

    @Override
    public List<City> parseFile(String fileName) {
        List<City> cityList;

        openFile(fileName);

        cityList = readData();

        closeFile();
        return cityList;
    }

    private List<City> readData() {
        List<City> cityList = new ArrayList<>();

        Optional<String> readedLine = Optional.ofNullable(readLine());
        while (readedLine.isPresent()) {
            String dataCheck = readedLine.get();
            if (isLineContainsData(dataCheck)) {
                City city = getCityFromData(dataCheck);
                cityList.add(city);
            }
            readedLine = Optional.ofNullable(readLine());
        }
        return cityList;
    }

    private boolean isLineContainsData(String probablyData) {
        Iterable<String> splittedData = splitData(probablyData);
        return isValid(splittedData);
    }

    private City getCityFromData(String line) {

        Iterable<String> splittedData = splitData(line);

        if (isValid(splittedData)) {
            Iterator<String> stringSlitted = splittedData.iterator();
            int index = Integer.parseInt(stringSlitted.next());
            double x = Double.parseDouble(stringSlitted.next());
            double y = Double.parseDouble(stringSlitted.next());
            return new City(x, y);
        }
        return null;
    }

    private Iterable<String> splitData(String s) {
        return Splitter.on(' ')
                .trimResults()
                .omitEmptyStrings()
                .split(s);
    }

    private boolean isValid(Iterable<String> splittedData) {
        int sizeOfArray = Iterables.size(splittedData);
        if (sizeOfArray != 3)
            return false;

        String firstData = splittedData.iterator().next();
        boolean notNumber = !firstData.matches(".*\\d+.*");
        if (notNumber)
            return false;

        return true;
    }

    private void openFile(String fileName) {
        boolean withoutExtension = !fileName.contains(tspFileExtension);
        if (withoutExtension) {
            fileName += tspFileExtension;
        }

        try {
            fr = new FileReader(fileName);
            bfr = new BufferedReader(fr);
        } catch (FileNotFoundException e) {
            System.out.println("BŁĄD PRZY OTWIERANIU PLIKU!");
        }
    }

    private String readLine() {
        try {
            return bfr.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void closeFile() {
        try {
            fr.close();
        } catch (IOException e) {
            System.out.println("BŁĄD PRZY ZAMYKANIU PLIKU!");
            System.exit(3);
        }
    }

}
