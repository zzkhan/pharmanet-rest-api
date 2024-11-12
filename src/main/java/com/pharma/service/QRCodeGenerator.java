package com.pharma.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@Component
public class QRCodeGenerator {
  public String generate(String data){
    // Generate QR Code
    try {
      BitMatrix bitMatrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, 300, 300);
      ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
      MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
      byte[] qrCodeBytes = pngOutputStream.toByteArray();
      return Base64.getEncoder().encodeToString(qrCodeBytes);
    } catch (WriterException | IOException e) {
      throw new RuntimeException(e);
    }
  }
}
