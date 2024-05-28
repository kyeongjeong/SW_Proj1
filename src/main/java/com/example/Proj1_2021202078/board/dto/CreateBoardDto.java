package com.example.Proj1_2021202078.board.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateBoardDto {

    private String title;
    private String comment;
    private List<String> imageLinks;
}
