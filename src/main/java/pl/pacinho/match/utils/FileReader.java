package pl.pacinho.match.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface FileReader {
    List<String> readFile(File file) throws IOException;
}