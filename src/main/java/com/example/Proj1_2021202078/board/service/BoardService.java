package com.example.Proj1_2021202078.board.service;

import com.example.Proj1_2021202078.board.dto.BoardContentDto;
import com.example.Proj1_2021202078.board.dto.CreateBoardDto;
import com.example.Proj1_2021202078.board.dto.IndexDto;
import com.example.Proj1_2021202078.board.repository.BoardImageRepository;
import com.example.Proj1_2021202078.board.repository.BoardRepository;
import com.example.Proj1_2021202078.entity.Board;
import com.example.Proj1_2021202078.entity.BoardImage;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private BoardImageRepository imageRepository;

    public List<IndexDto> getBoardIndex() {

        List<IndexDto> indexDtos = new ArrayList<>();
        List<Board> boards = boardRepository.findAllByOrderByCreatedAtDesc();

        if(!boards.isEmpty()) {
            for (Board board : boards) {

                List<BoardImage> images = imageRepository.findAllByBoard_BoardId(board.getBoardId());
                BoardImage image;
                if(images != null && !images.isEmpty()) {
                    image = images.get(0);
                }
                else {
                    image = null;
                }
                IndexDto indexDto = new IndexDto(board, image);
                indexDtos.add(indexDto);
            }
        }
        return indexDtos;
    }

    public Long uploadBoard(CreateBoardDto createBoardDto) {

        try {
            Board board = new Board(createBoardDto);
            boardRepository.saveAndFlush(board);

            if (createBoardDto.getImageLinks() != null && !createBoardDto.getImageLinks().isEmpty()) {
                for (int i = 0; i < createBoardDto.getImageLinks().size(); i++) {
                    BoardImage boardImage = new BoardImage(board, createBoardDto.getImageLinks().get(i));
                    imageRepository.saveAndFlush(boardImage);
                }
            }
            return board.getBoardId();
        }
        catch (DataAccessException e) {
            throw new RuntimeException("Failed to upload board: " + e.getMessage(), e);
        }
    }

    public BoardContentDto getBoardContent(Long boardId) {

        try {
            Optional<Board> board = boardRepository.findById(boardId);
            if (board.isEmpty()) {
                throw new NoSuchElementException("Board not found with id: " + boardId);
            }
            List<BoardImage> images = imageRepository.findAllByBoard_BoardId(boardId);

            List<String> imageLinks = new ArrayList<>();
            for (BoardImage image : images) {
                imageLinks.add(image.getImageLink());
            }
            return new BoardContentDto(board.get(), imageLinks);
        }
        catch (NoSuchElementException e) {
            throw new RuntimeException("Failed to get board content: " + e.getMessage(), e);
        }
        catch (DataAccessException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
    }

    public boolean deleteBoard(Long boardId) {

        try {
            List<BoardImage> images = imageRepository.findAllByBoard_BoardId(boardId);
            if (images != null && !images.isEmpty()) {
                for (BoardImage image : images) {
                    imageRepository.deleteById(image.getImageId());
                }
            }
            boardRepository.deleteById(boardId);
            return true;
        }
        catch (EmptyResultDataAccessException e) {
            throw new NoSuchElementException("Board not found with id: " + boardId, e);
        }
        catch (DataAccessException e) {
            throw new RuntimeException("Failed to delete board: " + e.getMessage(), e);
        }
    }

    public Long updateBoard(Long boardId, CreateBoardDto updateBoardDto) {

        Optional<Board> board = boardRepository.findById(boardId);
        if(board.isPresent()) {

            board.get().setTitle(updateBoardDto.getTitle());
            board.get().setComment(updateBoardDto.getComment());
            boardRepository.saveAndFlush(board.get());

            List<BoardImage> images = imageRepository.findAllByBoard_BoardId(boardId);
            imageRepository.deleteAll(images);

            if (updateBoardDto.getImageLinks() != null && !updateBoardDto.getImageLinks().isEmpty()) {

                for (int i = 0; i < updateBoardDto.getImageLinks().size(); i++) {
                    BoardImage boardImage = new BoardImage(board.get(), updateBoardDto.getImageLinks().get(i));
                    imageRepository.saveAndFlush(boardImage);
                }
            }
            return boardId;
        }
        else {
            throw new EntityNotFoundException("Board not found with id: " + boardId);
        }
    }
}
