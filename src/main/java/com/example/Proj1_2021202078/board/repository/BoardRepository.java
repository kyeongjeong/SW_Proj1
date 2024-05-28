package com.example.Proj1_2021202078.board.repository;

import com.example.Proj1_2021202078.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("SELECT b FROM Board b ORDER BY b.createdAt DESC")
    List<Board> findAllByOrderByCreatedAtDesc();
}
