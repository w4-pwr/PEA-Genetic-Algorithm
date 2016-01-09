package pl.poczta.polska.filemanager;

import java.io.File;
import java.io.FilenameFilter;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Evelan-E6540 on 26.12.2015.
 */
public class FileQueueCreatorImpl implements FileQueueCreator {

    private Queue<String> filesToProcess = new LinkedList<>();

    public Queue<String> queueFromPath(String path) {
        return createQueue(new File(path));
    }

    public Queue<String> queueProjectDirectory() {
        File pathToTSPDirectory = new File(".", File.separator + "tsp");
        return createQueue(pathToTSPDirectory);
    }

    private Queue<String> createQueue(File folder) {


        FilenameFilter filter = (dir, name) -> name.endsWith(".tsp");

        File[] listOfFiles = folder.listFiles(filter);

        for (File file : listOfFiles) {
            filesToProcess.add(file.getName());
        }
        return filesToProcess;
    }
}
