package com.fastcampus.boardserver.service.impl;

import com.fastcampus.boardserver.dto.CategoryDTO;
import com.fastcampus.boardserver.mapper.CategoryMapper;
import com.fastcampus.boardserver.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;

    /**
     * 카테고리 생성
     */
    @Override
    public void register(String accountId, CategoryDTO categoryDTO) {
        if (accountId != null) {
            categoryMapper.register(categoryDTO);
        } else {
            log.error("register Error! {}", categoryDTO);
            throw new RuntimeException("register Error! 게시글 카테고리 등록 메서드를 확인해주세요." + categoryDTO);
        }
    }

    /**
     * 카테고리 수정
     */
    @Override
    public void update(CategoryDTO categoryDTO) {
        if (categoryDTO != null) {
            categoryMapper.updateCategory(categoryDTO);
        } else {
            log.error("update Error! {}", categoryDTO);
            throw new RuntimeException("update Error! 게시글 카테고리 수정 메서드를 확인해주세요." + categoryDTO);
        }
    }

    /**
     * 카테고리 삭제
     */
    @Override
    public void delete(int CategoryId) {
        if (CategoryId != 0) {
            categoryMapper.deleteCategory(CategoryId);
        } else {
            log.error("delete Error! {}", CategoryId);
            throw new RuntimeException("delete Error! 게시글 카테고리 삭제 메서드를 확인해주세요." + CategoryId);
        }
    }

}
