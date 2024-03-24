package com.fastcampus.boardserver.service.impl;

import com.fastcampus.boardserver.dto.CommentDTO;
import com.fastcampus.boardserver.dto.PostDTO;
import com.fastcampus.boardserver.dto.TagDTO;
import com.fastcampus.boardserver.dto.UserDTO;
import com.fastcampus.boardserver.mapper.CommentMapper;
import com.fastcampus.boardserver.mapper.PostMapper;
import com.fastcampus.boardserver.mapper.TagMapper;
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
    private final CommentMapper commentMapper;
    private final TagMapper tagMapper;

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
            Integer postId = postDTO.getId();
            for (int i = 0; i < postDTO.getTagDTOList().size(); i++) {
                // i번째 태그정보를 dto로 꺼내고 register한다.
                TagDTO tagDTO = postDTO.getTagDTOList().get(i);
                tagMapper.register(tagDTO);

                // 태그의 관계테이블에도 저장한다.
                Integer tagId = tagDTO.getId();
                tagMapper.createPostTag(tagId, postId);
            }

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

    /**
     * 댓글 등록
     */
    @Override
    public void registerComment(CommentDTO commentDTO) {
        if (commentDTO.getPostId() != 0) {
            commentMapper.register(commentDTO);
        } else {
            log.error("registerComment Error! {}", commentDTO);
            throw new RuntimeException("registerComment Error! 댓글 등록 메서드를 확인해주세요." + commentDTO);
        }
    }

    /**
     * 댓글 수정
     */
    @Override
    public void updateComment(CommentDTO commentDTO) {
        if (commentDTO != null) {
            commentMapper.updateComment(commentDTO);
        } else {
            log.error("updateComment Error! {}", commentDTO);
            throw new RuntimeException("updateComment Error! 댓글 수정 메서드를 확인해주세요." + commentDTO);
        }
    }

    /**
     * 댓글 삭제
     */
    @Override
    public void deletePostComment(int userId, int commentId) {
        if (userId != 0 && commentId != 0) {
            commentMapper.deletePostComment(commentId);
        } else {
            log.error("deletePostComment Error! {}", commentId);
            throw new RuntimeException("deletePostComment Error! 댓글 삭제 메서드를 확인해주세요." + commentId);
        }
    }

    /**
     * 태그 등록
     */
    @Override
    public void registerTag(TagDTO tagDTO) {
        if (tagDTO != null) {
            tagMapper.register(tagDTO);
        } else {
            log.error("registerTag Error! {}", tagDTO);
            throw new RuntimeException("registerTag Error! 태그 등록 메서드를 확인해주세요." + tagDTO);
        }
    }

    /**
     * 태그 수정
     */
    @Override
    public void updateTag(TagDTO tagDTO) {
        if (tagDTO != null) {
            tagMapper.updateTags(tagDTO);
        } else {
            log.error("updateTag Error! {}", tagDTO);
            throw new RuntimeException("updateTag Error! 태그 수정 메서드를 확인해주세요." + tagDTO);
        }
    }

    /**
     * 태그 삭제
     */
    @Override
    public void deletePostTag(int userId, int tagId) {
        if (userId != 0 && tagId != 0) {
            tagMapper.deletePostTag(tagId);
        } else {
            log.error("deletePostTag Error! {}", tagId);
            throw new RuntimeException("deletePostTag Error! 태그 삭제 메서드를 확인해주세요." + tagId);
        }
    }

}
