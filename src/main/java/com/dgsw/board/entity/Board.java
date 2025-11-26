package com.dgsw.board.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Board{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    private String author;

    private LocalDateTime deadline;   // 마감일

    private String fileName;// 첨부 파일명

    public void update(String title, String content, String author, LocalDateTime deadline, String fileName){
        this.title = title;
        this.content = content;
        this.author = author;
        this.deadline = deadline;
        this.fileName = fileName;
    }

    @Builder
    public Board(String title, String content, String author, LocalDateTime deadline, String fileName) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.deadline = deadline;
        this.fileName = fileName;

    }
}
