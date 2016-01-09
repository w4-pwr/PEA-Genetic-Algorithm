package pl.poczta.polska;

import pl.poczta.polska.csv.CSVGenerator;
import pl.poczta.polska.filemanager.FileQueueCreator;
import pl.poczta.polska.filemanager.FileQueueCreatorImpl;
import pl.poczta.polska.filemanager.TSPFileParser;
import pl.poczta.polska.filemanager.TSPFileParserImpl;
import pl.poczta.polska.model.City;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

/**
 * Created by Evelan-E6540 on 26.12.2015.
 */
public class Initializer {

    FileQueueCreator fileQueueCreator = new FileQueueCreatorImpl();
    TSPFileParser tspFileParser = new TSPFileParserImpl();
    CSVGenerator csvGenerator = new CSVGenerator();
    Random random;

    public void start() {
        random = new Random();
        //ilosc generacji, poczatkowa populacja, ilosc miast(jesli generowany)
        runTSPDirectory(50, random.nextInt(49));
        runGeneratedTSP(50, random.nextInt(49), 100);
    }

    private void runTSPDirectory(int numberOfGenerations, int initialPopulation) {
        csvGenerator.createFile(numberOfGenerations, initialPopulation, false);
        Queue<String> filesToProcess = fileQueueCreator.queueProjectDirectory();
        while (!filesToProcess.isEmpty()) {

            String fileName = filesToProcess.poll();
            List<City> cityList = getCitiesFromFile(fileName);

            TourManager.setCities(cityList);

            // Initialize population
            Population pop = new Population(initialPopulation, true);
            int initialDistance = pop.getFittest().getDistance();

            long lStartTime = System.currentTimeMillis();
            pop = GA.evolvePopulation(pop);
            for (int i = 0; i < numberOfGenerations; i++) {
                pop = GA.evolvePopulation(pop);
            }

            long lEndTime = System.currentTimeMillis();
            long elapsedTime = lEndTime - lStartTime;

            int finalDistance = pop.getFittest().getDistance();

            showAndSaveLog(fileName, elapsedTime, initialDistance, finalDistance);
        }
    }

    private void runGeneratedTSP(int numberOfGenerations, int initialPopulation, int numberOfCities) {
        csvGenerator.createFile(numberOfGenerations, initialPopulation, true);
        List<City> cityList = generateRandomCities(numberOfCities);

        TourManager.setCities(cityList);

        // Initialize population
        Population pop = new Population(initialPopulation, true);
        int initialDistance = pop.getFittest().getDistance();
        long lStartTime = System.currentTimeMillis();
        pop = GA.evolvePopulation(pop);
        for (int i = 0; i < numberOfGenerations; i++) {
            pop = GA.evolvePopulation(pop);
        }
        long lEndTime = System.currentTimeMillis();
        long elapsedTime = lEndTime - lStartTime;
        int finalDistance = pop.getFittest().getDistance();

        showAndSaveLog("random" + numberOfCities + ".topkek", elapsedTime, initialDistance, finalDistance);
    }

    private void showAndSaveLog(String fileName, long difference, int initialDistance, int finalDistance) {

        String output = String.format("%-14.14s time: %5sms  initial: %-10s final: %-10s", fileName, difference, initialDistance, finalDistance);

        if (initialDistance != 0) {
            csvGenerator.addRecord(fileName, fileName, difference, initialDistance, finalDistance);
        }

        System.out.println(output);

    }

    private List<City> generateRandomCities(int number) {
        Random r = new Random();
        List<City> cityList = new ArrayList<>();

        for (int i = 0; i < number; i++) {
            double x = r.nextDouble();
            double y = r.nextDouble();
            cityList.add(new City(x, y));
        }
        return cityList;
    }

    private List<City> getCitiesFromFile(String filename) {
        return tspFileParser.parseFile(new File(".") + File.separator + "tsp" + File.separator + filename);
    }

}
