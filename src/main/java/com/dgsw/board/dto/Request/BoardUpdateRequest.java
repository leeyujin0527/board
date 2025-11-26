package com.dgsw.board.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class BoardUpdateRequest {
    private String title;
    private String content;
    private String author;
    private LocalDateTime deadline;
    private String fileName;
}
