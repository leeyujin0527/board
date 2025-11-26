package com.dgsw.board.dto.Response;

import com.dgsw.board.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardResponse {

    private Long id;
    private String title;
    private String content;
    private String author;
    private LocalDateTime deadline;
    private String fileName;

    public static BoardResponse fromEntity(Board board) {
        return BoardResponse.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .author(board.getAuthor())
                .deadline(board.getDeadline())
                .fileName(board.getFileName())
                .build();
    }
}
