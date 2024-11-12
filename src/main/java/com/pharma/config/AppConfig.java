package com.pharma.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pharma.interceptor.LoggingInterceptor;
import io.grpc.Grpc;
import io.grpc.ManagedChannel;
import io.grpc.TlsChannelCredentials;
import org.hyperledger.fabric.client.Gateway;
import org.hyperledger.fabric.client.identity.Identities;
import org.hyperledger.fabric.client.identity.Identity;
import org.hyperledger.fabric.client.identity.Signer;
import org.hyperledger.fabric.client.identity.Signers;
import org.hyperledger.fabric.client.identity.X509Identity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.cert.CertificateException;

@Configuration
public class AppConfig {

  @Bean
  public ObjectMapper objectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    return objectMapper;
  }

  @Bean
  public Gateway manufacturerGateway(AppProperties appProperties) throws Exception {
    return createGateway(appProperties.connections.get("manufacturer"));
  }

  @Bean
  public Gateway distributorGateway(AppProperties appProperties) throws Exception {
    return createGateway(appProperties.connections.get("distributor"));
  }

  @Bean
  public Gateway transporterGateway(AppProperties appProperties) throws Exception {
    return createGateway(appProperties.connections.get("transporter"));
  }

  @Bean
  public Gateway pharmacyGateway(AppProperties appProperties) throws Exception {
    return createGateway(appProperties.connections.get("pharmacy"));
  }

  @Bean
  public Gateway consumerGateway(AppProperties appProperties) throws Exception {
    return createGateway(appProperties.connections.get("consumer"));
  }

  private static Gateway createGateway(ConnectionDetails connectionDetails) throws IOException, CertificateException, InvalidKeyException {
    return Gateway.newInstance().identity(newIdentity(connectionDetails)).signer(newSigner(connectionDetails)).connection(newGrpcConnection(connectionDetails)).connect();
  }

  private static ManagedChannel newGrpcConnection(ConnectionDetails connectionDetails) throws IOException {
    var tlsCertPath = Paths.get(connectionDetails.getCryptoBasePath()).resolve(connectionDetails.getTlsCertPath());
    var credentials = TlsChannelCredentials.newBuilder()
            .trustManager(tlsCertPath.toFile())
            .build();
    return Grpc.newChannelBuilder(connectionDetails.getPeerEndpoint(), credentials)
            .overrideAuthority(connectionDetails.getOverrideAuth())
            .intercept(new LoggingInterceptor())
            .build();
  }

  private static Identity newIdentity(ConnectionDetails connectionDetails) throws IOException, CertificateException {
    var certDirPath = Paths.get(connectionDetails.getCryptoBasePath()).resolve(connectionDetails.getCertPath());
    try (var certReader = Files.newBufferedReader(getFirstFilePath(certDirPath))) {
      var certificate = Identities.readX509Certificate(certReader);
      return new X509Identity(connectionDetails.getMspId(), certificate);
    }
  }
  private static Signer newSigner(ConnectionDetails connectionDetails) throws IOException, InvalidKeyException {
    var keyDirPath = Paths.get(connectionDetails.getCryptoBasePath()).resolve(connectionDetails.getKeystorePath());
    try (var keyReader = Files.newBufferedReader(getFirstFilePath(keyDirPath))) {
      var privateKey = Identities.readPrivateKey(keyReader);
      return Signers.newPrivateKeySigner(privateKey);
    }
  }

  private static Path getFirstFilePath(Path dirPath) throws IOException {
    try (var keyFiles = Files.list(dirPath)) {
      return keyFiles.findFirst().orElseThrow();
    }
  }
}
