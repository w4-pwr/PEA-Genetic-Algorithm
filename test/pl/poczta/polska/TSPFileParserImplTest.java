package pl.poczta.polska;


import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import pl.poczta.polska.model.City;
import pl.poczta.polska.filemanager.TSPFileParser;
import pl.poczta.polska.filemanager.TSPFileParserImpl;

import java.util.List;

/**
 * Created by Evelan-E6540 on 26.12.2015.
 */
public class TSPFileParserImplTest extends TestCase {

    @Test
    public void testParseFile() throws Exception {
        TSPFileParser tspFileParser = new TSPFileParserImpl();
        List<City> cityList = tspFileParser.parseFile("ulysses16");
        Assert.assertNotNull(cityList);
        Assert.assertEquals(16, cityList.size());

        for (City c : cityList) {
            Assert.assertNotNull(c);
            Assert.assertNotNull(c.getX());
            Assert.assertNotNull(c.getY());
        }

    }
}