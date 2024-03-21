package com.fastcampus.boardserver.mapper;

import com.fastcampus.boardserver.dto.CategoryDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper {

    // 카테고리 생성
    public int register(CategoryDTO categoryDTO);

    // 카테고리 수정
    public void updateCategory(CategoryDTO categoryDTO);

    // 카테고리 삭제
    public void deleteCategory(int CategoryId);

}
