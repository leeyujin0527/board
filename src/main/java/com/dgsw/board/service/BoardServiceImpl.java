package com.dgsw.board.service;

import com.dgsw.board.dto.Request.BoardCreateRequest;
import com.dgsw.board.dto.Request.BoardUpdateRequest;
import com.dgsw.board.dto.Response.BoardResponse;
import com.dgsw.board.entity.Board;
import com.dgsw.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;

    //게물 생성
    @Override
    public BoardResponse createBoard(BoardCreateRequest boardCreateRequest){
        Board board = boardRepository.save(boardCreateRequest.toEntity());
        return BoardResponse.fromEntity(board);
    }

    //모든 게시판 조회
    @Override
    public List<Board> findAllBoard(){
        List<Board> boardList = boardRepository.findAll();
        return boardList;
    }

    //제목으로 검색
    @Override
    public List<Board> searchByTitle(String title){
        List<Board> result = boardRepository.findByTitleContainingOrderByTitleAsc(title);
        return result.stream().toList();
    }

    //특정 게시물 지우기
    @Override
    public void deleteBoard(Long id){
        boardRepository.deleteById(id);
    }

    //수정
    @Override
    public BoardResponse updateBoard(Long id, BoardUpdateRequest boardUpdateRequest) {
        Board board = boardRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("게시글 없음: " + id));

        board.update(
                boardUpdateRequest.getTitle(),
                boardUpdateRequest.getContent(),
                boardUpdateRequest.getAuthor(),
                boardUpdateRequest.getDeadline(),
                boardUpdateRequest.getFileName()
        );

        boardRepository.save(board);
        return BoardResponse.fromEntity(board);
    }

}
