package com.fastcampus.boardserver.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 로그인 체크를 하는 어노테이션 생성
 */
@Retention(RetentionPolicy.RUNTIME) // 런타임시에 적용
@Target(ElementType.METHOD) // 메소드에 적용
public @interface LoginCheck {

    // enum 타입 설정
    public static enum UserType {
        USER, ADMIN
    }

    // type() 메소드를 통해 UserType을 반환
    UserType type();

}
