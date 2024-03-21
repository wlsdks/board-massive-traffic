package com.fastcampus.boardserver.util;

import lombok.extern.log4j.Log4j2;

import java.security.MessageDigest;

/**
 * 비밀번호 암호화 유틸 클래스
 */
@Log4j2
public class SHA256Util {

    public static final String ENCRYPTION_KEY = "SHA-256";

    public static String encryptSHA256(String string) {
        String SHA = null;

        MessageDigest sh;
        try {
            sh = MessageDigest.getInstance(ENCRYPTION_KEY);
            sh.update(string.getBytes());
            byte byteData[] = sh.digest();
            StringBuffer buffer = new StringBuffer();
            for (byte byteDatum : byteData) {
                buffer.append(Integer.toString((byteDatum & 0xff) + 0x100, 16).substring(1));
            }
            SHA = buffer.toString();
        } catch (Exception e) {
            log.error("encryptSHA256 error: {}", e.getMessage());
            SHA = null;
        }
        return SHA;
    }

}
