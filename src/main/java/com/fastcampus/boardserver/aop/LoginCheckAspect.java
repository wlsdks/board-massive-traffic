package com.fastcampus.boardserver.aop;

import com.fastcampus.boardserver.util.SessionUtil;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 실제 aop동작을 적용시킬 클래스
 */
@Order(Ordered.LOWEST_PRECEDENCE)
@Component
@Log4j2
@Aspect
public class LoginCheckAspect {

    // aspect의 실행범위를 LoginCheck 어노테이션으로 설정
    @Around("@annotation(com.fastcampus.boardserver.aop.LoginCheck) && @annotation(loginCheck)")
    public Object adminLoginCheck(
            ProceedingJoinPoint proceedingJoinPoint, LoginCheck loginCheck
    ) throws Throwable {

        HttpSession session = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes()))
                .getRequest().getSession();

        String id = null; // 넘겨줄 id
        int idIndex = 0;  // 몇번째 파라미터인지 확인할 변수

        String userType = loginCheck.type().toString(); // 어노테이션의 타입을 가져옴
        switch (userType) {
            case "ADMIN" -> id = SessionUtil.getLoginAdminId(session);
            case "USER" -> id = SessionUtil.getLoginMemberId(session);
        }

        if (id == null) {
            log.info(proceedingJoinPoint.toString() + "accountName : " + id);
            throw new HttpStatusCodeException(HttpStatus.UNAUTHORIZED, "로그인한 ID를 확인해주세요.") {};
        }

        Object[] modifiedArgs = proceedingJoinPoint.getArgs();

        // id값을 넘길 매개변수 리스트에 추가한다.
        if (proceedingJoinPoint != null) {
            modifiedArgs[idIndex] = id;
        }

        // 요청한 컨트롤러 메서드에 넘긴다.
        return proceedingJoinPoint.proceed(modifiedArgs);
    }


}
