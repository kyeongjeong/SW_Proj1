package com.example.Proj1_2021202078.board.dto;

import com.example.Proj1_2021202078.entity.Board;
import com.example.Proj1_2021202078.entity.BoardImage;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class IndexDto {

    private Long boardId;
    private String title;
    private LocalDateTime createdAt;
    private Long ImageId;
    private String ImageLink;

    public IndexDto() {}

    public IndexDto(Board board, BoardImage image) {
        this.boardId = board.getBoardId();
        this.title = board.getTitle();
        this.createdAt = board.getCreatedAt();
        this.ImageId = image.getImageId();
        this.ImageLink = image.getImageLink();
    }
}
