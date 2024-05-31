package com.example.Proj1_2021202078.board.dto;

import com.example.Proj1_2021202078.entity.Board;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class BoardContentDto {

    private Long boardId;
    private String title;
    private String comment;
    private LocalDateTime createdAt;
    private List<String> imageLinks;

    public BoardContentDto() {}

    public BoardContentDto(Board board, List<String> imageLinks) {
        this.boardId = board.getBoardId();
        this.title = board.getTitle();
        this.comment = board.getComment();
        this.createdAt = board.getCreatedAt();
        this.imageLinks = imageLinks;
    }
}
