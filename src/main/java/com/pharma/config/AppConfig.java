package com.pharma.config;

import com.pharma.temp.AuthBuilder;
import com.pharma.temp.FiscalAuthBuilder;
import com.pharma.temp.FiscalAuthorisation;
import com.pharma.temp.TransitAuthBuilder;
import com.pharma.temp.TransitAuthorisation;
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
  public Gateway manufacturerGateway(AppProperties appProperties) throws Exception {
    ConnectionDetails connectionDetails = appProperties.connections.get("manufacturer");
    return Gateway.newInstance().identity(newIdentity(connectionDetails)).signer(newSigner(connectionDetails)).connection(newGrpcConnection(connectionDetails)).connect();
  }

  @Bean
  public Gateway distributorGateway(AppProperties appProperties) throws Exception {
    ConnectionDetails connectionDetails = appProperties.connections.get("distributor");
    return Gateway.newInstance().identity(newIdentity(connectionDetails)).signer(newSigner(connectionDetails)).connection(newGrpcConnection(connectionDetails)).connect();
  }

  @Bean
  public Gateway transporterGateway(AppProperties appProperties) throws Exception {
    ConnectionDetails connectionDetails = appProperties.connections.get("transporter");
    return Gateway.newInstance().identity(newIdentity(connectionDetails)).signer(newSigner(connectionDetails)).connection(newGrpcConnection(connectionDetails)).connect();
  }

  @Bean
  public Gateway pharmacyGateway(AppProperties appProperties) throws Exception {
    ConnectionDetails connectionDetails = appProperties.connections.get("pharmacy");
    return Gateway.newInstance().identity(newIdentity(connectionDetails)).signer(newSigner(connectionDetails)).connection(newGrpcConnection(connectionDetails)).connect();
  }

  @Bean
  public AuthBuilder<TransitAuthorisation> transitAuthBuilder(){
    return new TransitAuthBuilder();
  }

@Bean
  public AuthBuilder<FiscalAuthorisation> fiscalAuthBuilder(){
    return new FiscalAuthBuilder();
  }

  private static ManagedChannel newGrpcConnection(ConnectionDetails connectionDetails) throws IOException {
    var tlsCertPath = Paths.get(connectionDetails.getCryptoBasePath()).resolve(connectionDetails.getTlsCertPath());
    var credentials = TlsChannelCredentials.newBuilder()
            .trustManager(tlsCertPath.toFile())
            .build();
    return Grpc.newChannelBuilder(connectionDetails.getPeerEndpoint(), credentials)
            .overrideAuthority(connectionDetails.getOverrideAuth())
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
