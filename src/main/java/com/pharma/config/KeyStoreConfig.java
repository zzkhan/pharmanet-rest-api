package com.pharma.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.crypto.SecretKey;
import java.security.KeyStore;

@Configuration
public class KeyStoreConfig {
  @Bean
  public KeyStore loadKeyStore() throws Exception {
    // Load the JCEKS KeyStore
    KeyStore keyStore = KeyStore.getInstance("JCEKS");
    try (java.io.InputStream keyStoreStream = new ClassPathResource("keystore.jceks").getInputStream()) {
      keyStore.load(keyStoreStream, "pharmanet".toCharArray());
    }
    return keyStore;
  }

  @Bean
  public SecretKey loadAesKey(KeyStore keyStore) throws Exception {
    // Load the AES key from the KeyStore
    KeyStore.ProtectionParameter protectionParam = new KeyStore.PasswordProtection("pharmanet".toCharArray());
    KeyStore.SecretKeyEntry secretKeyEntry = (KeyStore.SecretKeyEntry) keyStore.getEntry("myaeskey", protectionParam);
    return secretKeyEntry.getSecretKey();
  }
}
