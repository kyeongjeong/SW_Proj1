package com.example.Proj1_2021202078.entity;

import com.example.Proj1_2021202078.board.dto.CreateBoardDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "board")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long boardId;

    @Column(name = "title", length = 20)
    private String title;

    @Column(name = "comment", length = 100)
    private String comment;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Board() {}

    public Board(CreateBoardDto createBoardDto) {
        this.title = createBoardDto.getTitle();
        this.comment = createBoardDto.getComment();
        this.createdAt = LocalDateTime.now();
    }
}
