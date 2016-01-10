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

    int POSITION_BOUND_GENERERATED_VALUES = 10000;

    public void start() {
        random = new Random();
        //ilosc generacji, poczatkowa populacja, ilosc miast(jesli generowany)

//        runTSPDirectory(500, 50);
//        runTSPDirectory(2000, 50);

        int initialPopulation = 100;
        int generations = 10000;
        runGeneratedTSP(generations, initialPopulation, 50);
        runGeneratedTSP(generations, initialPopulation, 100);
        runGeneratedTSP(generations, initialPopulation, 150);
        runGeneratedTSP(generations, initialPopulation, 200);
        runGeneratedTSP(generations, initialPopulation, 250);
        runGeneratedTSP(generations, initialPopulation, 300);
        runGeneratedTSP(generations, initialPopulation, 350);

//        generations = 2000;
//        runGeneratedTSP(generations, initialPopulation, 500);
//        runGeneratedTSP(generations, initialPopulation, 1000);
//        runGeneratedTSP(generations, initialPopulation, 1500);
//        runGeneratedTSP(generations, initialPopulation, 2000);
    }

    private void runTSPDirectory(int numberOfGenerations, int initialPopulation) {
        String outputFilename = csvGenerator.createFile(numberOfGenerations, initialPopulation, false);
        Queue<String> filesToProcess = fileQueueCreator.queueProjectDirectory();
        while (!filesToProcess.isEmpty()) {

            String tspFilename = filesToProcess.poll();
            List<City> cityList = getCitiesFromFile(tspFilename);

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

            showAndSaveLog(outputFilename, tspFilename, elapsedTime, initialDistance, finalDistance);
        }
    }

    private void runGeneratedTSP(int numberOfGenerations, int initialPopulation, int numberOfCities) {
        String outputFilename = csvGenerator.createFile(numberOfGenerations, initialPopulation, true);
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

        showAndSaveLog(outputFilename, "random" + numberOfCities + ".topkek", elapsedTime, initialDistance, finalDistance);
    }

    private void showAndSaveLog(String outputFilename, String tspFilename, long difference, int initialDistance, int finalDistance) {

        String output = String.format("%-14.14s time: %5sms  initial: %-10s final: %-10s", tspFilename, difference, initialDistance, finalDistance);

        if (initialDistance != 0) {
            csvGenerator.addRecord(outputFilename, tspFilename, difference, initialDistance, finalDistance);
        }

        System.out.println(output);

    }

    private List<City> generateRandomCities(int number) {
        Random r = new Random();
        List<City> cityList = new ArrayList<>();

        for (int i = 0; i < number; i++) {
            double x = r.nextInt(POSITION_BOUND_GENERERATED_VALUES);
            double y = r.nextInt(POSITION_BOUND_GENERERATED_VALUES);
            cityList.add(new City(x, y));
        }
        return cityList;
    }

    private List<City> getCitiesFromFile(String filename) {
        return tspFileParser.parseFile(new File(".") + File.separator + "tsp" + File.separator + filename);
    }

}
