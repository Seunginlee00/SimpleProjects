package com.project.my.controller;

import com.project.my.auth.service.Auth;
import com.project.my.dto.board.BoardDto;
import com.project.my.service.board.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "게시판관리 API")
@RestController
@RequestMapping("/v1/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @Operation(summary = "게시판 입력 하기")
    @PostMapping
    public ResponseEntity<String> boardInsert(@Auth Long userId, @RequestBody BoardDto dto) {
        boardService.boardInsert(userId, dto);
        return ResponseEntity.ok().body("ok");
    }

//    @Operation(summary = "게시판 수정 하기")
//    @PatchMapping
//    public ResponseEntity<String> boardUpdate(@RequestBody BoardDto dto) {
//        boardService.boardUpdate(dto);
//        return ResponseEntity.ok().body("ok");
//    }
//
//
//    @Operation(summary = "게시판 삭제 하기")
//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> boardDelete(@PathVariable(name = "id") Long boardId) {
//        boardService.boardDelete(boardId);
//        return ResponseEntity.ok().body("ok");
//    }
//
//    @Operation(summary = "게시판 단일 조회 하기")
//    @GetMapping("/{id}")
//    public ResponseEntity<Object> boardInquiry(@PathVariable(name = "id") Long boardId) {
//        Object data = boardService.boardInquiry(boardId);
//        ApiResult result = new ApiResult("200", "게시판 단일 조회", data);
//        return ResponseEntity.ok().body(result);
//    }
//
//    @Operation(summary = "게시판 목록 조회 하기")
//    @GetMapping()
//    public ResponseEntity<Object> boardList(SearchDto dto, @PageableDefault(size = 20, direction = Sort.Direction.DESC) @Parameter(hidden = true) Pageable pageable) {
//        Object data = boardService.boardList(dto,pageable);
//        ApiResult result = new ApiResult("200", "게시판 단일 조회", data);
//        return ResponseEntity.ok().body(result);
//    }
//
//
//    @Operation(summary = "댓글 입력 하기")
//    @PostMapping("/comment")
//    public ResponseEntity<String> commentInsert(@RequestBody CommentDto dto) {
//        boardService.commentInsert(dto);
//        return ResponseEntity.ok().body("ok");
//    }
//
//    @Operation(summary = "댓글 수정 하기")
//    @PatchMapping("/comment")
//    public ResponseEntity<String> commentUpdate(@RequestBody CommentDto dto) {
//        boardService.commentUpdate(dto);
//        return ResponseEntity.ok().body("ok");
//    }
//    @Operation(summary = "댓글 삭제 하기")
//    @PostMapping("/comment/{id}")
//    public ResponseEntity<String> commentInsert(@PathVariable (name = "id") Long commentId) {
//        boardService.commentDelete(commentId);
//        return ResponseEntity.ok().body("ok");
//    }

}
