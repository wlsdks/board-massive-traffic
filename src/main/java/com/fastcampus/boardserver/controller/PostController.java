package com.fastcampus.boardserver.controller;

import com.fastcampus.boardserver.aop.LoginCheck;
import com.fastcampus.boardserver.dto.CommentDTO;
import com.fastcampus.boardserver.dto.PostDTO;
import com.fastcampus.boardserver.dto.TagDTO;
import com.fastcampus.boardserver.dto.UserDTO;
import com.fastcampus.boardserver.dto.response.CommonResponse;
import com.fastcampus.boardserver.service.impl.PostServiceImpl;
import com.fastcampus.boardserver.service.impl.UserServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
@Log4j2
public class PostController {

    private final UserServiceImpl userService;
    private final PostServiceImpl postService;

    /**
     * 게시글 등록
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<PostDTO>> register(
            String accountId,
            @RequestBody PostDTO postDTO
    ) {
        postService.register(accountId, postDTO);
        CommonResponse commonResponse = new CommonResponse<>(
                HttpStatus.OK, "SUCCESS", "게시글 등록", postDTO
        );
        return ResponseEntity.ok(commonResponse);
    }

    /**
     * 내 게시글 조회
     */
    @GetMapping("/my-posts")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<List<PostDTO>>> getMyPosts(String accountId) {
        UserDTO userInfo = userService.getUserInfo(accountId);
        List<PostDTO> postDTOList = postService.getMyPosts(userInfo.getId());
        CommonResponse commonResponse = new CommonResponse<>(
                HttpStatus.OK, "SUCCESS", "내 게시글 조회", postDTOList
        );
        return ResponseEntity.ok(commonResponse);
    }

    /**
     * 게시글 수정
     */
    @PatchMapping("{postId}")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<PostResponse>> updatePosts(
            String accountId,
            @PathVariable(name = "postId") int postId,
            @RequestBody PostRequest postRequest
    ) {
        UserDTO userInfo = userService.getUserInfo(accountId);
        PostDTO postDTO = PostDTO.builder()
                .id(postId)
                .name(postRequest.getName())
                .contents(postRequest.getContents())
                .views(postRequest.getViews())
                .categoryId(postRequest.getCategoryId())
                .userId(userInfo.getId())
                .fileId(postRequest.getFileId())
                .updateTime(new Date())
                .build();
        postService.updatePosts(postDTO);
        CommonResponse commonResponse = new CommonResponse<>(
                HttpStatus.OK, "SUCCESS", "게시글 수정", postDTO
        );
        return ResponseEntity.ok(commonResponse);
    }

    /**
     * 게시글 삭제
     */
    @DeleteMapping("{postId}")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<PostDeleteRequest>> deletePosts(
            String accountId,
            @PathVariable(name = "postId") int postId
    ) {
        UserDTO userInfo = userService.getUserInfo(accountId);
        postService.deletePosts(userInfo.getId(), postId);
        CommonResponse commonResponse = new CommonResponse<>(
                HttpStatus.OK, "SUCCESS", "게시글 삭제", null
        );
        return ResponseEntity.ok(commonResponse);
    }

    /**
     * 댓글 등록
     */
    @PostMapping("comments")
    @ResponseStatus(HttpStatus.CREATED)
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<CommentDTO>> registerPostComment(
            String accountId,
            @RequestBody CommentDTO commentDTO
    ) {
        postService.registerComment(commentDTO);
        CommonResponse<CommentDTO> commonResponse = new CommonResponse<>(
                HttpStatus.OK, "SUCCESS", "댓글 등록", commentDTO
        );
        return ResponseEntity.ok(commonResponse);
    }

    /**
     * 댓글 수정
     */
    @PatchMapping("comments/{commentId}")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<CommentDTO>> updatePostComment(
            String accountId,
            @PathVariable(name = "commentId") int commentId,
            @RequestBody CommentDTO commentDTO
    ) {
        UserDTO userInfo = userService.getUserInfo(accountId);
        if (userInfo != null) {
            postService.updateComment(commentDTO);
        }
        CommonResponse<CommentDTO> commonResponse = new CommonResponse<>(
                HttpStatus.OK, "SUCCESS", "댓글 수정", commentDTO
        );
        return ResponseEntity.ok(commonResponse);
    }

    /**
     * 댓글 삭제
     */
    @DeleteMapping("comments/{commentId}")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<CommentDTO>> deletePostComment(
            String accountId,
            @PathVariable(name = "commentId") int commentId,
            @RequestBody CommentDTO commentDTO
    ) {
        UserDTO userInfo = userService.getUserInfo(accountId);
        if (userInfo != null) {
            postService.deletePostComment(userInfo.getId(), commentId);
        }
        CommonResponse<CommentDTO> commonResponse = new CommonResponse<>(
                HttpStatus.OK, "SUCCESS", "댓글 삭제", commentDTO
        );
        return ResponseEntity.ok(commonResponse);
    }

    /**
     * 태그 등록
     */
    @PostMapping("tags")
    @ResponseStatus(HttpStatus.CREATED)
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<TagDTO>> registerPostTag(
            String accountId,
            @RequestBody TagDTO tagDTO
    ) {
        postService.registerTag(tagDTO);
        CommonResponse<TagDTO> commonResponse = new CommonResponse<>(
                HttpStatus.OK, "SUCCESS", "태그 등록", tagDTO
        );
        return ResponseEntity.ok(commonResponse);
    }

    /**
     * 태그 수정
     */
    @PatchMapping("tags/{tagId}")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<TagDTO>> updatePostTag(
            String accountId,
            @PathVariable(name = "tagId") int tagId,
            @RequestBody TagDTO tagDTO
    ) {
        UserDTO userInfo = userService.getUserInfo(accountId);
        if (userInfo != null) {
            postService.updateTag(tagDTO);
        }
        CommonResponse<TagDTO> commonResponse = new CommonResponse<>(
                HttpStatus.OK, "SUCCESS", "태그 수정", tagDTO
        );
        return ResponseEntity.ok(commonResponse);
    }

    /**
     * 태그 삭제
     */
    @DeleteMapping("tags/{tagId}")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<TagDTO>> deletePostTag(
            String accountId,
            @PathVariable(name = "tagId") int tagId,
            @RequestBody TagDTO tagDTO
    ) {
        UserDTO userInfo = userService.getUserInfo(accountId);
        if (userInfo != null) {
            postService.deletePostTag(userInfo.getId(), tagId);
        }
        CommonResponse<TagDTO> commonResponse = new CommonResponse<>(
                HttpStatus.OK, "SUCCESS", "태그 삭제", tagDTO
        );
        return ResponseEntity.ok(commonResponse);
    }

    // -- response 객체 inner class --
    @Getter
    @AllArgsConstructor
    private static class PostResponse {
        private List<PostDTO> postDTOs;
    }

    // -- request 객체 inner class --
    @Getter
    @Setter
    private static class PostRequest {
        private String name;
        private String contents;
        private int views;
        private int categoryId;
        private int userId;
        private int fileId;
        private Date updateTime;
    }

    // -- 삭제 request inner class --
    @Getter
    @Setter
    private static class PostDeleteRequest {
        private int id;
        private int accountId;
    }


}
