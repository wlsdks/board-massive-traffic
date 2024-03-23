package com.fastcampus.boardserver.service.impl;

import com.fastcampus.boardserver.dto.PostDTO;
import com.fastcampus.boardserver.dto.UserDTO;
import com.fastcampus.boardserver.mapper.PostMapper;
import com.fastcampus.boardserver.mapper.UserProfileMapper;
import com.fastcampus.boardserver.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Log4j2
@Service
public class PostServiceImpl implements PostService {

    private final PostMapper postMapper;
    private final UserProfileMapper userProfileMapper;

    /**
     * 게시글 등록
     */
    @Override
    public void register(String userId, PostDTO postDTO) {
        UserDTO memberInfo = userProfileMapper.getUserProfile(userId);
        postDTO.setUserId(memberInfo.getId());
        postDTO.setCreateTime(new Date());

        if (memberInfo != null) {
            postMapper.register(postDTO);
        } else {
            log.error("register Error! {}", postDTO);
            throw new RuntimeException("register Error! 게시글 등록 메서드를 확인해주세요." + postDTO);
        }
    }

    /**
     * 내 게시글 조회
     */
    @Override
    public List<PostDTO> getMyPosts(int accountId) {
        List<PostDTO> postDtoList = postMapper.selectMyPosts(accountId);
        return postDtoList;
    }

    /**
     * 게시글 수정
     */
    @Override
    public void updatePosts(PostDTO postDTO) {
        if (postDTO != null && postDTO.getId() != 0) {
            postMapper.updatePosts(postDTO);
        } else {
            log.error("update Error! {}", postDTO);
            throw new RuntimeException("update Error! 게시글 수정 메서드를 확인해주세요." + postDTO);
        }
    }

    /**
     * 게시글 삭제
     */
    @Override
    public void deletePosts(int userId, int postId) {
        if (userId != 0 && postId != 0) {
            postMapper.deletePosts(postId);
        } else {
            log.error("delete Error! {}", postId);
            throw new RuntimeException("delete Error! 게시글 삭제 메서드를 확인해주세요." + postId);
        }
    }

}
