package pl.pacinho.match.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.pacinho.match.board.model.BoardCubeSimple;
import pl.pacinho.match.board.tools.BoardBuilder;
import pl.pacinho.match.cube.tools.FileCubeReader;
import pl.pacinho.match.utils.ClassicFileReader;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/game")
@RestController
public class GameRestController {

    private final BoardBuilder boardBuilder;

    @GetMapping("/board/generate")
    ResponseEntity<?> generateBoard(@RequestParam(value = "simple", defaultValue = "true") boolean simpleView) throws IOException, IllegalStateException {
        return ResponseEntity.ok(
                simpleView ? generateSimpleBoardView() : generateClassicBoardView()
        );
    }

    private BoardCubeSimple[][] generateClassicBoardView() throws IOException {
        return Arrays.stream(boardBuilder.buildBoard(new FileCubeReader(new ClassicFileReader(), new File(getClass().getClassLoader().getResource("static/data/cubes.txt").getFile()))))
                .map(arr -> Arrays.stream(arr).map(BoardCubeSimple::new).toArray(BoardCubeSimple[]::new))
                .toArray(BoardCubeSimple[][]::new);
    }

    private List<String> generateSimpleBoardView() throws IOException {
        return Arrays.stream(boardBuilder.buildBoard(new FileCubeReader(new ClassicFileReader(), new File(getClass().getClassLoader().getResource("static/data/cubes.txt").getFile()))))
                .map(arr -> Arrays.toString(Arrays.stream(arr).map(BoardCubeSimple::new).toArray(BoardCubeSimple[]::new)))
                .toList();
    }

}
