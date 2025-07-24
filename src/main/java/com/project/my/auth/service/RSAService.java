package com.project.my.auth.service;

import com.project.my.common.CryptoUtil;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RSAService {
    private final CryptoUtil cryptoUtil;
    private final Map<String, KeyPair> keyStore = new ConcurrentHashMap<>();
    private String base64PublicKey;
    private String base64PrivateKey;

    public RSAService(CryptoUtil cryptoUtil) {
        this.cryptoUtil = cryptoUtil;
        jonGenerateRSAKey(); // 서버 시작 시 고정 RSA 키 불러오거나 생성
    }

    /**
     * 회원가입 용 RSA 암호화 KEY 쌍으로 생성
     */
    private void jonGenerateRSAKey() {
        KeyPair keyPair = cryptoUtil.generateRSAKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        base64PublicKey = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        base64PrivateKey = Base64.getEncoder().encodeToString(privateKey.getEncoded());
    }

    /**
     * 회원 가입용 공개키
     */
    public String getPubKey() {
        return base64PublicKey;
    }

    /**
     * 회원가입용 비밀키
     */
    public String getPriKey() {
        return base64PrivateKey;
    }

    /**
     * 회원 가입 시 비밀번호 복호화 용도
     */
    public String decryptedText(String encryptedMessage) {
        return cryptoUtil.decryptRSA(encryptedMessage, base64PrivateKey);
    }

    /**
     * 요청할 때마다 새로운 RSA 키 쌍을 생성하고 저장
     */
    public String generateNewPublicKey(String sessionId) {
        KeyPair keyPair = cryptoUtil.generateRSAKeyPair();
        keyStore.put(sessionId, keyPair); // 키를 저장
        return Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
    }

    /**
     * 클라이언트가 비밀번호를 복호화할 때 사용
     */
    public String decryptMessage(String sessionId, String encryptedMessage) {
        KeyPair keyPair = keyStore.get(sessionId);
        if (keyPair == null) {
            throw new IllegalArgumentException("세션 키가 만료되었거나 존재하지 않습니다.");
        }
        return cryptoUtil.decryptRSA(encryptedMessage, Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded()));
    }

    /**
     * 일정 시간이 지나면 키 자동 삭제 (메모리 누수 방지)
     */
    public void cleanupOldKeys() {
        keyStore.entrySet().removeIf(entry -> {
            // 예제에서는 2분 후 키 삭제 (더 짧거나 길게 조정 가능)
            long creationTime = System.currentTimeMillis() - 2 * 60 * 1000;
            return entry.getValue().getPrivate().hashCode() < creationTime;
        });
    }
}

