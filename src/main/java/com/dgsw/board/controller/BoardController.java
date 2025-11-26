package com.dgsw.board.controller;

import com.dgsw.board.dto.Request.BoardCreateRequest;
import com.dgsw.board.dto.Request.BoardUpdateRequest;
import com.dgsw.board.dto.Response.BoardResponse;
import com.dgsw.board.entity.Board;
import com.dgsw.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    /**
     * 1. 게시글 생성하기 (CREATE)
     * POST /api/boards
     */
    @PostMapping
    public ResponseEntity<BoardResponse> createBoard(@RequestBody BoardCreateRequest request) {
        BoardResponse response = boardService.createBoard(request);

        // Location 헤더 포함: 생성된 자원의 URI 제공 (REST 표준)
        return ResponseEntity
                .created(URI.create("/api/boards/" + response.getId()))
                .body(response);
    }

    /**
     * 2. 전체 게시글 조회 (READ)
     * GET /api/boards
     */
    @GetMapping
    public ResponseEntity<List<Board>> getAllBoards() {
        List<Board> list = boardService.findAllBoard();
        return ResponseEntity.ok(list);
    }

    /**
     * 3. 제목 검색 (READ)
     * GET /api/boards/search?title=xxx
     */
    @GetMapping("/search")
    public ResponseEntity<List<Board>> searchBoardByTitle(@RequestParam String title) {
        List<Board> result = boardService.searchByTitle(title);
        return ResponseEntity.ok(result);
    }

    /**
     * 4. 게시글 수정 (UPDATE)
     * PUT /api/boards/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<BoardResponse> updateBoard(
            @PathVariable Long id,
            @RequestBody BoardUpdateRequest request
    ) {
        BoardResponse response = boardService.updateBoard(id, request);
        return ResponseEntity.ok(response);   // 200 OK + 내용 반환
    }

    /**
     * 5. 게시글 삭제 (DELETE)
     * DELETE /api/boards/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id) {
        boardService.deleteBoard(id);
        return ResponseEntity.noContent().build(); // 204 No Content (REST 표준)
    }
}
