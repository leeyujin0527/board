package com.dgsw.board.dto.Request;

import com.dgsw.board.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardCreateRequest {

    private String title;
    private String content;
    private String author;
    private LocalDateTime deadline;
    private String fileName;

    public Board toEntity() {
        return Board.builder()
                .title(title)
                .content(content)
                .author(author)
                .deadline(deadline)
                .fileName(fileName)
                .build();
    }
}
