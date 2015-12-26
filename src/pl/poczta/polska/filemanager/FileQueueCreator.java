package pl.poczta.polska.filemanager;

import java.util.Queue;

/**
 * Created by Evelan-E6540 on 26.12.2015.
 */
public interface FileQueueCreator {

    Queue<String> queueFromPath(String path);

    Queue<String> queueProjectDirectory();
}
