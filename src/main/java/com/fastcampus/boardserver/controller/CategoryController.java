package com.fastcampus.boardserver.controller;

import com.fastcampus.boardserver.aop.LoginCheck;
import com.fastcampus.boardserver.dto.CategoryDTO;
import com.fastcampus.boardserver.service.impl.CategoryServiceImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/categories")
@Log4j2
public class CategoryController {

    private final CategoryServiceImpl categoryService;

    /**
     * 카테고리 생성
     * admin 유저만 카테고리 등록이 가능하다.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @LoginCheck(type = LoginCheck.UserType.ADMIN)
    public void registerCategory(String accountId, @RequestBody CategoryDTO categoryDTO) {
        categoryService.register(accountId, categoryDTO);
    }

    /**
     * 카테고리 수정
     * admin 유저만 카테고리 수정이 가능하다.
     */
    @PatchMapping("{categoryId}")
    @LoginCheck(type = LoginCheck.UserType.ADMIN)
    public void updateCategories(
            String accountId,
            @PathVariable(name = "categoryId") int categoryId,
            @RequestBody CategoryRequest categoryRequest
    ) {

        CategoryDTO categoryDTO = new CategoryDTO(
                categoryId,
                categoryRequest.getName(),
                CategoryDTO.SortStatus.NEWEST,
                10,
                1
        );

        categoryService.update(categoryDTO);
    }

    /**
     * 카테고리 삭제
     * admin 유저만 카테고리 삭제이 가능하다.
     */
    @DeleteMapping("{categoryId}")
    @LoginCheck(type = LoginCheck.UserType.ADMIN)
    public void deleteCategories(
            String accountId,
            @PathVariable(name = "categoryId") int categoryId
    ) {
        categoryService.delete(categoryId);
    }

    /**
     * --- request 객체를 inner 클래스로 선언 ---
     * 이렇게 하면 외부에서는 접근해서 사용할 수 없다는 장점이 있다.
     */
    @Getter
    @Setter
    private static class CategoryRequest {
        private int id;
        private String name;
    }


}
