package com.example.Proj1_2021202078.board.repository;

import com.example.Proj1_2021202078.entity.Board;
import com.example.Proj1_2021202078.entity.BoardImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardImageRepository extends JpaRepository<BoardImage, Long> {

    @Query("SELECT i FROM BoardImage i WHERE i.board.boardId = :boardId")
    List<BoardImage> findAllByBoard_BoardId(@Param("boardId") Long boardId);
}
