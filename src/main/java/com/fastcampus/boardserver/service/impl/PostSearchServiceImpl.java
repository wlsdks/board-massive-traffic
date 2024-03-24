package com.fastcampus.boardserver.service.impl;

import com.fastcampus.boardserver.dto.PostDTO;
import com.fastcampus.boardserver.dto.request.PostSearchRequest;
import com.fastcampus.boardserver.exception.BoardServerException;
import com.fastcampus.boardserver.mapper.PostSearchMapper;
import com.fastcampus.boardserver.service.PostSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Log4j2
@Service
public class PostSearchServiceImpl implements PostSearchService {

    private final PostSearchMapper postSearchMapper;

    /**
     * 게시글 검색
     */
    @Async
    @Cacheable(value = "getPosts", key = "'getProducts' + #postSearchRequest.getName() + #postSearchRequest.getCategoryId()")
    @Override
    public List<PostDTO> getPosts(PostSearchRequest postSearchRequest) {
        List<PostDTO> postDTOList = null;
        try {
            postDTOList = postSearchMapper.selectPosts(postSearchRequest);
        } catch (RuntimeException e) {
            log.error("getPosts 실패");
            throw new BoardServerException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        return postDTOList;
    }

    /**
     * 태그로 게시글 검색
     */
    @Override
    public List<PostDTO> getPostsByTag(String tagName) {
        List<PostDTO> postDTOList = null;
        try {
            postDTOList = postSearchMapper.getPostByTag(tagName);
        } catch (Exception e) {
            log.error("getPostByTag 메서드 실패", e.getMessage());
        }
        return postDTOList;
    }

}
