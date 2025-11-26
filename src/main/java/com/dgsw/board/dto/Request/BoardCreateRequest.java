package com.dgsw.board.dto.Request;

import com.dgsw.board.entity.Board;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardCreateRequest {

    @NotBlank(message = "제목은 필수입니다.")
    @Size(max = 100, message = "제목은 최대 100자까지 가능합니다.")
    private String title;
    @NotBlank(message = "본문은 필수입니다.")
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
