package pl.poczta.polska.filemanager;

import pl.poczta.polska.model.City;

import java.util.List;

/**
 * Created by Evelan-E6540 on 26.12.2015.
 */
public interface TSPFileParser {

    List<City> parseFile(String fileName);
}
