package com.dgsw.board.service;

import com.dgsw.board.dto.Request.BoardCreateRequest;
import com.dgsw.board.dto.Request.BoardUpdateRequest;
import com.dgsw.board.dto.Response.BoardResponse;
import com.dgsw.board.entity.Board;

import java.util.List;

public interface BoardService {

    //게시물 생성하기
    public BoardResponse createBoard(BoardCreateRequest boardCreateRequest);

    //모든 게시판 조회
    public List<Board> findAllBoard();

    public List<Board> searchByTitle(String title);

    //특정 게시판 삭제
    public void deleteBoard(Long id);

    public BoardResponse updateBoard(Long id, BoardUpdateRequest boardUpdateRequest);
}
