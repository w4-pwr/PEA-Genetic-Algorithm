package pl.poczta.polska.util;

import junit.framework.Assert;
import org.junit.Test;
import pl.poczta.polska.filemanager.FileQueueCreator;
import pl.poczta.polska.filemanager.FileQueueCreatorImpl;

import java.util.Queue;

/**
 * Created by Evelan-E6540 on 26.12.2015.
 */
public class FileQueueTest {

    @Test
    public void testCreateQueueToProcess() throws Exception {
        FileQueueCreator fileQueue = new FileQueueCreatorImpl();
        Queue<String> queueToProcess = fileQueue.queueProjectDirectory();

        Assert.assertNotNull(queueToProcess);
        Assert.assertEquals(2, queueToProcess.size());
    }

    @Test
    public void testCreateQueueFromPathProcess() throws Exception {
        FileQueueCreator fileQueue = new FileQueueCreatorImpl();
        Queue<String> queueToProcess = fileQueue.queueFromPath("C:\\Users\\Evelan-E6540\\Programowanie\\VR Global\\genetic-algorithm\\tsp");

        Assert.assertNotNull(queueToProcess);
        Assert.assertEquals(94, queueToProcess.size());
    }
}