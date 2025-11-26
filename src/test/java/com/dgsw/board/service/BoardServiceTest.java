package com.dgsw.board.service;

import com.dgsw.board.dto.Request.BoardCreateRequest;
import com.dgsw.board.dto.Response.BoardResponse;
import com.dgsw.board.entity.Board;
import com.dgsw.board.repository.BoardRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class BoardServiceTest {
    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardRepository boardRepository;

    @Test
    @DisplayName("게시글 생성 테스트")
    void createBoard() {
        BoardCreateRequest request = new BoardCreateRequest(
                "새 게시물",
                "새 내용",
                "새 작성자",
                LocalDateTime.now(),
                "newFile.txt"
        );
        BoardResponse saved = boardService.createBoard(request);
        assertThat(saved.getTitle()).isEqualTo("새 게시물");

    }

    @Test
    @DisplayName("게시글 전체 조회 테스트")
    void findAllBoard() {
        boardRepository.deleteAll();
        boardRepository.save(new Board(
                "첫 게시물",
                "내용1",
                "작성자1",
                LocalDateTime.of(2025, 12, 31, 23, 59, 59),
                "file1.txt"
        ));
        boardRepository.save(new Board(
                "두 번째 게시물",
                "내용2",
                "작성자2",
                LocalDateTime.of(2025, 12, 30, 23, 59, 59),
                "file2.txt"
        ));
        List<Board> boards = boardService.findAllBoard();
        assertThat(boards.get(0).getTitle()).isEqualTo("첫 게시물");
        assertThat(boards.get(1).getTitle()).isEqualTo("두 번째 게시물");
    }

    @Test
    void searchByTitle() {
    }

    @Test
    void deleteBoard() {
    }

    @Test
    void updateBoard() {
    }

}