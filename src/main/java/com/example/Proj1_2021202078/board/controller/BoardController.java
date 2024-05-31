package com.example.Proj1_2021202078.board.controller;

import com.example.Proj1_2021202078.board.dto.BoardContentDto;
import com.example.Proj1_2021202078.board.dto.CreateBoardDto;
import com.example.Proj1_2021202078.board.dto.IndexDto;
import com.example.Proj1_2021202078.board.service.BoardService;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    // 게시판 인덱스 페이지를 가져오는 메소드
    @GetMapping("")
    public String getBoardIndex(Model model) {
        List<IndexDto> indexDtos = boardService.getBoardIndex();
        model.addAttribute("boardList", indexDtos);
        return "index";
    }

    // 업로드 페이지를 가져오는 메소드 (새 게시글 작성)
    @GetMapping("/upload")
    public String getUploadPage(Model model) {
        model.addAttribute("board", new CreateBoardDto());
        model.addAttribute("isUpdate", false);
        return "upload";
    }

    // 업로드 페이지를 가져오는 메소드 (기존 게시글 수정)
    @GetMapping("/upload/{boardId}")
    public String getUploadPage(@PathVariable("boardId") Long boardId, Model model) {
        BoardContentDto boardContent = boardService.getBoardContent(boardId);
        model.addAttribute("board", boardContent);
        model.addAttribute("isUpdate", true);
        return "upload";
    }

    // 게시글을 업로드하는 메소드
    @PostMapping("/")
    public String uploadBoard(@ModelAttribute CreateBoardDto createBoardDto,
                              @RequestParam("image") MultipartFile[] images) throws IOException {

        List<String> imageLinks = new ArrayList<>();
        String currentDir = System.getProperty("user.dir");
        String uploadDir = currentDir + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "static";

        for (MultipartFile image : images) {
            if (!image.isEmpty()) {
                String imagePath = uploadDir + File.separator + image.getOriginalFilename();
                System.out.println(imagePath);
                File file = new File(imagePath);
                file.getParentFile().mkdirs();
                image.transferTo(file);
                String imageLink = image.getOriginalFilename();
                imageLinks.add(imageLink);
            }
        }

        createBoardDto.setImageLinks(imageLinks);
        boardService.uploadBoard(createBoardDto);

        return "redirect:/";
    }

    // 게시글을 수정하는 메소드
    @PutMapping("/upload/{boardId}")
    public String updateBoard(@PathVariable("boardId") Long boardId,
                              @ModelAttribute CreateBoardDto updateBoardDto,
                              @RequestParam(value = "image", required = false) MultipartFile[] images) throws IOException {

        if (images != null && images.length > 0) {

            List<String> imageLinks = new ArrayList<>();
            String currentDir = System.getProperty("user.dir");
            String uploadDir = currentDir + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "static";

            for (MultipartFile image : images) {
                if (!image.isEmpty()) {
                    String imagePath = uploadDir + File.separator + image.getOriginalFilename();
                    File file = new File(imagePath);
                    file.getParentFile().mkdirs();
                    image.transferTo(file);
                    String imageLink = "/" + image.getOriginalFilename();
                    imageLinks.add(imageLink);
                }
            }
            updateBoardDto.setImageLinks(imageLinks);
        }
        boardService.updateBoard(boardId, updateBoardDto);
        return "redirect:/";
    }

    // 특정 게시글 내용을 가져오는 메소드
    @GetMapping("/viewer/{boardId}")
    public String getBoardContent(@PathVariable("boardId") Long boardId, Model model) {
        BoardContentDto boardContent = boardService.getBoardContent(boardId);
        model.addAttribute("board", boardContent);
        return "imageView";
    }

    // 게시글을 삭제하는 메소드
    @DeleteMapping("/{boardId}")
    public String deleteBoard(@PathVariable("boardId") Long boardId) {
        boardService.deleteBoard(boardId);
        return "redirect:/";
    }
}
