package com.dgsw.board.controller;

import com.dgsw.board.dto.Request.BoardCreateRequest;
import com.dgsw.board.dto.Request.BoardUpdateRequest;
import com.dgsw.board.entity.Board;
import com.dgsw.board.repository.BoardRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BoardRepository boardRepository;

    @BeforeEach
    void setUp() {
        boardRepository.deleteAll();
    }

    @Test
    @DisplayName("1. 게시글 생성 테스트")
    void createBoard() throws Exception {
        // given
        BoardCreateRequest request = new BoardCreateRequest(
                "테스트 제목",
                "테스트 내용",
                "작성자",
                LocalDateTime.now(),
                "file.txt"
        );

        String json = objectMapper.writeValueAsString(request);

        // when
        ResultActions result = mockMvc.perform(
                post("/api/boards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        );

        // then
        result.andExpect(status().isCreated());

        List<Board> boards = boardRepository.findAll();
        assertThat(boards.size()).isEqualTo(1);
        assertThat(boards.get(0).getTitle()).isEqualTo("테스트 제목");
    }

    @Test
    @DisplayName("2. 전체 게시글 조회 테스트")
    void getAllBoards() throws Exception {
        // given
        boardRepository.save(Board.builder()
                .title("A")
                .content("내용A")
                .author("작성자A")
                .deadline(LocalDateTime.now())
                .fileName("fileA.txt")
                .build());

        // when & then
        mockMvc.perform(get("/api/boards"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("3. 제목 검색 테스트")
    void searchBoardByTitle() throws Exception {
        // given
        boardRepository.save(Board.builder()
                .title("검색 테스트")
                .content("내용")
                .author("작성자")
                .deadline(LocalDateTime.now())
                .fileName("file.txt")
                .build());

        // when & then
        mockMvc.perform(get("/api/boards/search")
                        .param("title", "검색"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("4. 게시글 수정 테스트")
    void updateBoard() throws Exception {
        // given
        Board saved = boardRepository.save(Board.builder()
                .title("원래 제목")
                .content("원래 내용")
                .author("작성자")
                .deadline(LocalDateTime.now())
                .fileName("file.txt")
                .build());

        BoardUpdateRequest updateReq = new BoardUpdateRequest(
                "수정된 제목",
                "수정된 내용",
                "작성자2",
                LocalDateTime.now(),
                "file2.txt"
        );

        String json = objectMapper.writeValueAsString(updateReq);

        // when
        ResultActions result = mockMvc.perform(
                put("/api/boards/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        );

        // then
        result.andExpect(status().isOk());

        Board updated = boardRepository.findById(saved.getId()).orElseThrow();
        assertThat(updated.getTitle()).isEqualTo("수정된 제목");
    }

    @Test
    @DisplayName("5. 게시글 삭제 테스트")
    void deleteBoard() throws Exception {
        // given
        Board saved = boardRepository.save(Board.builder()
                .title("삭제할 제목")
                .content("삭제할 내용")
                .author("작성자")
                .deadline(LocalDateTime.now())
                .fileName("file.txt")
                .build());

        // when
        ResultActions result = mockMvc.perform(
                delete("/api/boards/" + saved.getId())
        );

        // then
        result.andExpect(status().isNoContent());
        assertThat(boardRepository.findAll()).isEmpty();
    }
}
