package com.example.Proj1_2021202078.board.controller;

import com.example.Proj1_2021202078.board.dto.BoardContentDto;
import com.example.Proj1_2021202078.board.dto.CreateBoardDto;
import com.example.Proj1_2021202078.board.dto.IndexDto;
import com.example.Proj1_2021202078.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("")
    public List<IndexDto> getBoardIndex() {
        return boardService.getBoardIndex();
    }

    @PostMapping("")
    public Long uploadBoard(@Validated @RequestBody CreateBoardDto createBoardDto) {
        return boardService.uploadBoard(createBoardDto);
    }

    @GetMapping("/{boardId}")
    public BoardContentDto getBoardContent(@PathVariable("boardId") Long boardId) {
        return boardService.getBoardContent(boardId);
    }

    @DeleteMapping("/{boardId}")
    public boolean deleteBoard(@PathVariable("boardId") Long boardId) {
        return boardService.deleteBoard(boardId);
    }

    @PatchMapping("/{boardId}")
    public Long updateBoard(@PathVariable("boardId") Long boardId, @Validated @RequestBody CreateBoardDto updateBoardDto) {
        return boardService.updateBoard(boardId, updateBoardDto);
    }
}
