package com.dgsw.board.repository;

import com.dgsw.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    //쿼리메소드.. 제목으로 게시물 조회
    List<Board> findByTitleContainingOrderByTitleAsc(String title);
}
