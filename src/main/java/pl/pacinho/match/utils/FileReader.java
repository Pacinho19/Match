package pl.pacinho.match.utils;

import java.io.File;
import java.util.List;

public interface FileReader {
    List<String> readTxt(File file);
}