package com.example.Proj1_2021202078.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "board_image")
public class BoardImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long imageId;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @Column(name = "image_link")
    private String imageLink;

    public BoardImage() {}

    public BoardImage(Board board, String imageLink) {
        this.board = board;
        this.imageLink = imageLink;
    }
}
