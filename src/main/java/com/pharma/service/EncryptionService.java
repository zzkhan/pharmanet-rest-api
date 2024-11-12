package com.pharma.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.util.Base64;

@Component
@Slf4j
public class EncryptionService {

  public static final String TRANSFORMATION = "AES";


  private SecretKey secretKey;
  private IvParameterSpec ivSpec;

  @Autowired
  public EncryptionService(SecretKey secretKey) {
    this.secretKey = secretKey;
    byte[] iv = new byte[16];
    ivSpec = new IvParameterSpec(iv);
  }

  public String encrypt(String plainText) throws Exception {
    Cipher cipher = Cipher.getInstance(TRANSFORMATION);
    cipher.init(Cipher.ENCRYPT_MODE, secretKey);
    byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
    String encodedCipherText = Base64.getEncoder().encodeToString(encryptedBytes);
    log.info("encodedCipherText {}", encodedCipherText);
    String decrypted = decrypt(encodedCipherText);
    log.info("decrypted {}", decrypted);
    return encodedCipherText;
  }

  public String decrypt(String encryptedData) throws Exception {
    log.info("decrypting cipher text {}", encryptedData);
    Cipher cipher = Cipher.getInstance(TRANSFORMATION);
    cipher.init(Cipher.DECRYPT_MODE, secretKey);
    byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
    return new String(decryptedBytes);
  }

  public static void main(String[] args) {

  }
}
