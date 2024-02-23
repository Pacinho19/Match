package pl.pacinho.match.board.tools;

import org.springframework.stereotype.Service;
import pl.pacinho.match.board.model.BoardCube;
import pl.pacinho.match.config.MatchConfiguration;
import pl.pacinho.match.cube.tools.FileCubeReader;
import pl.pacinho.match.utils.ClassicFileReader;

import java.io.File;
import java.io.IOException;

@Service
public class FileBoardBuilder extends BoardBuilder {

    private final FileCubeReader fileCubeReader;

    public FileBoardBuilder(MatchConfiguration _matchConfiguration) {
        super(_matchConfiguration);
        this.fileCubeReader = new FileCubeReader(new ClassicFileReader(), getCubesFile(_matchConfiguration));
    }

    private File getCubesFile(MatchConfiguration matchConfiguration) {
        return new File(getClass().getClassLoader().getResource(matchConfiguration.getCube().getFilePath()).getFile());
    }

    public BoardCube[][] buildBoard() throws IOException {
        return super.buildBoard(fileCubeReader);
    }
}
