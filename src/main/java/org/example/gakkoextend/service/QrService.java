package org.example.gakkoextend.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class QrService {
    public byte[] toPng(String data) {
        try {
            var matrix = new QRCodeWriter().encode(data, BarcodeFormat.QR_CODE, 320, 320);
            var out = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(matrix, "PNG", out);
            return out.toByteArray();
        } catch (Exception e) { throw new RuntimeException(e); }
    }
}
