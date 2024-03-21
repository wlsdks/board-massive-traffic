package com.fastcampus.boardserver.mapper;

import com.fastcampus.boardserver.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * mybatis 매퍼 인터페이스 선언
 */
@Mapper
public interface UserProfileMapper {

    public UserDTO getUserProfile(@Param("userId") String userId);

    int insertUserProfile(@Param("id") String id, @Param("password") String password, @Param("name") String name, @Param("phone") String phone, @Param("address") String address);

    int updateUserProfile(@Param("id") String id, @Param("password") String password, @Param("name") String name, @Param("phone") String phone, @Param("address") String address);

    int deleteUserProfile(@Param("id") String id);

    public int register(UserDTO userDTO);

    public UserDTO findByIdAndPassword(@Param("id") String id,
                                       @Param("password") String password);

    public UserDTO findByUserIdAndPassword(@Param("userId") String userId,
                                           @Param("password") String password);

    int idCheck(@Param("userId") String userId);

    public int updatePassword(UserDTO userDTO);

//    public int updateAddress(UserDTO userDTO);
}
