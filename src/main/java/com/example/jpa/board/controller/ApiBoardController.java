package com.example.jpa.board.controller;

import com.example.jpa.board.entity.Board;
import com.example.jpa.board.model.BoardInput;
import com.example.jpa.board.repository.BoardRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
public class ApiBoardController {

    private BoardRepository boardRepository;

    public ApiBoardController(BoardRepository boardRepository){
        this.boardRepository = boardRepository;
    }

    @PostMapping("/api/board")
    public Board addBoard(@RequestBody BoardInput boardInput){
        Board board = new Board();
        board.setTitle(boardInput.getTitle());
        board.setContents(boardInput.getContents());
        board.setRegDate(LocalDateTime.now());

        boardRepository.save(board);
        return board;
    }

    @GetMapping("/api/board/{id}")
    public Board getBoard(@PathVariable Long id) {
        Optional<Board> board = boardRepository.findById(id);

        if (board.isPresent()){
            return board.get();
        }

        return null;
    }
    @PutMapping("/api/board/{id}")
    public Board updateBoard(@PathVariable Long id, @RequestBody BoardInput boardInput) {
        Optional<Board> board = boardRepository.findById(id);

        if (board.isPresent()){
            board.get().setTitle(boardInput.getTitle());
            board.get().setContents(boardInput.getContents());
            board.get().setUpdateDate(LocalDateTime.now());

            boardRepository.save(board.get());
            return board.get();
        }
        return null;
    }

    @DeleteMapping("/api/board/{id}")
    public void deleteBoard(@PathVariable Long id){
        Optional<Board> board = boardRepository.findById(id);

        if(board.isPresent()){
            boardRepository.delete(board.get());
        }
    }
}
