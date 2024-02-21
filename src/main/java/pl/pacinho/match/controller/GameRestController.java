package pl.pacinho.match.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.pacinho.match.board.model.BoardCube;
import pl.pacinho.match.board.tools.BoardBuilder;
import pl.pacinho.match.cube.tools.FileCubeReader;
import pl.pacinho.match.utils.ClassicFileReader;

import java.io.File;
import java.io.IOException;

@RequiredArgsConstructor
@RequestMapping("/game")
@RestController
public class GameRestController {

    private final BoardBuilder boardBuilder;

    @GetMapping("/board/generate")
    ResponseEntity<BoardCube[][]> generateBoard() throws IOException {
        return ResponseEntity.ok(
                boardBuilder.buildBoard(new FileCubeReader(new ClassicFileReader(), new File(getClass().getClassLoader().getResource("static/data/cubes.txt").getFile())))
        );
    }
}
