package com.dgsw.board.service;

import com.dgsw.board.dto.Request.BoardCreateRequest;
import com.dgsw.board.dto.Request.BoardUpdateRequest;
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
    @DisplayName("게시글 제목 검색 테스트")
    void searchByTitle() {
        boardRepository.deleteAll();
        boardRepository.save(new Board(
                "Spring 테스트",
                "내용1",
                "작성자1",
                LocalDateTime.now(),
                "file1.txt"
        ));
        boardRepository.save(new Board(
                "JUnit 테스트",
                "내용2",
                "작성자2",
                LocalDateTime.now(),
                "file2.txt"
        ));

        List<Board> results = boardService.searchByTitle("Spring");
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getTitle()).isEqualTo("Spring 테스트");
    }

    @Test
    @DisplayName("게시글 삭제 테스트")
    void deleteBoard() {
        boardRepository.deleteAll();
        Board board = boardRepository.save(new Board(
                "삭제 테스트",
                "내용",
                "작성자",
                LocalDateTime.now(),
                "file.txt"
        ));

        boardService.deleteBoard(board.getId());

        List<Board> boards = boardService.findAllBoard();
        assertThat(boards).isEmpty();
    }

    @Test
    @DisplayName("게시글 수정 테스트")
    void updateBoard() {
        boardRepository.deleteAll();
        Board board = boardRepository.save(new Board(
                "수정 전 제목",
                "수정 전 내용",
                "작성자",
                LocalDateTime.now(),
                "file.txt"
        ));

        // BoardUpdateRequest 사용
        BoardUpdateRequest updateRequest = new BoardUpdateRequest(
                "수정 후 제목",
                "수정 후 내용",
                "작성자",
                LocalDateTime.now(),
                "fileUpdated.txt"
        );

        BoardResponse updated = boardService.updateBoard(board.getId(), updateRequest);

        assertThat(updated.getTitle()).isEqualTo("수정 후 제목");
        assertThat(updated.getContent()).isEqualTo("수정 후 내용");
        assertThat(updated.getFileName()).isEqualTo("fileUpdated.txt");
    }


}