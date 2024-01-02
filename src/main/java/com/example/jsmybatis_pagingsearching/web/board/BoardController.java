package com.example.jsmybatis_pagingsearching.web.board;

import com.example.jsmybatis_pagingsearching.config.security.principal.MyUserDetails;
import com.example.jsmybatis_pagingsearching.service.BoardService;
import com.example.jsmybatis_pagingsearching.web.board.dto.BoardSave_InDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@RequiredArgsConstructor
@Controller
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/board/list")
    public String list(Model model) {
        log.debug("GET - 글 목록 페이지");
        model.addAttribute("boardList", boardService.findAll());

        return "pages/board/list";
    }
    
    @GetMapping("/board/detail/{boardId}")
    public String detail(@PathVariable Long boardId, Model model) {
        log.debug("GET - 상세 페이지 요청");
        boardService.viewsCount(boardId);
        model.addAttribute("boardList", boardService.findById(boardId));

        return "pages/board/detail";
    }

    @GetMapping("/auth/board")
    public String saveForm() {
        log.debug("GET - 글 등록 페이지");
        return "pages/board/saveForm";
    }

    @PostMapping("/auth/board")
    public ResponseEntity<?> save(@RequestBody BoardSave_InDTO boardSaveInDTO, @AuthenticationPrincipal MyUserDetails myUserDetails) {
        log.debug("POST - 글 등록");
        boardService.save(boardSaveInDTO, myUserDetails.getUser().getId());
        return null;
    }

}
