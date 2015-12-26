package pl.poczta.polska;

import pl.poczta.polska.filemanager.FileQueueCreator;
import pl.poczta.polska.filemanager.FileQueueCreatorImpl;
import pl.poczta.polska.filemanager.TSPFileParser;
import pl.poczta.polska.filemanager.TSPFileParserImpl;
import pl.poczta.polska.model.City;

import java.io.File;
import java.util.List;
import java.util.Queue;

/**
 * Created by Evelan-E6540 on 26.12.2015.
 */
public class Initializer {

    FileQueueCreator fileQueueCreator = new FileQueueCreatorImpl();
    TSPFileParser tspFileParser = new TSPFileParserImpl();

    String PATH_TO_TSP_DIRECTORY = "C:\\Users\\Evelan-E6540\\Programowanie\\VR Global\\genetic-algorithm\\tsp";

    public void start() {

        Queue<String> filesToProcess = fileQueueCreator.queueFromPath(PATH_TO_TSP_DIRECTORY);

        while (!filesToProcess.isEmpty()) {
            String fileName = filesToProcess.poll();
            List<City> citiesFromFile;
            try {
                citiesFromFile = tspFileParser.parseFile(PATH_TO_TSP_DIRECTORY + File.separator + fileName);
            } catch (Exception e) {
                continue;
            }

            TourManager.setCities(citiesFromFile);

            int initialPopulation = citiesFromFile.size() + 10;
            int initialDistance = 0;
            Population pop = null;
            int tryTime = 0;
            // Initialize population
            while (initialDistance == 0) {
                pop = new Population(initialPopulation, true);
                initialDistance = pop.getFittest().getDistance();

                if (tryTime++ > 5) {
                    System.out.println(fileName + " chuj kłade i dalej jade");
                    break;
                }

            }

            if (tryTime > 5) {
                continue;
            }

            // Evolve population for 200 generations
            long lStartTime = System.currentTimeMillis();
            pop = GA.evolvePopulation(pop);
            for (int i = 0; i < 50; i++) {
                pop = GA.evolvePopulation(pop);
            }
            long lEndTime = System.currentTimeMillis();
            long difference = lEndTime - lStartTime;

            String output = String.format("%-14.14s time: %5sms  initial: %-10s final: %-10s", fileName, difference, initialDistance, pop.getFittest().getDistance());

            System.out.println(output);

//            System.out.println("Finished");
//            System.out.println("Final distance: " + pop.getFittest().getDistance());
//            System.out.println("Solution:");
//            System.out.println(pop.getFittest());
        }


    }
}
