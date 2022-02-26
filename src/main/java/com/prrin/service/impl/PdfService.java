package com.prrin.service.impl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
//import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;

//import com.itextpdf.html2pdf.ConverterProperties;
//import com.itextpdf.html2pdf.HtmlConverter;


import com.prrin.model.Products;
import com.prrin.service.IPdfService;

@Service
public class PdfService implements IPdfService {

	@Autowired
	ServletContext servletContext;

	@Autowired
	private SpringTemplateEngine templateEngine;

	@Override
	public int documento() {

		try {

			generateQRCodeImage("https://www.ihss.hn/", 250, 250, "C:\\Imagenes\\QR\\test3.png");

			ConverterProperties converterProperties = new ConverterProperties();
			converterProperties.setBaseUri("http://localhost:8090/case-use-html-to-pdf/");

			Context context = new Context();
			context.setVariables(modelPdf());
			
			String html = templateEngine.process("documento.html", context);

			try {
				OutputStream fileOutputStream = new FileOutputStream("C:\\pdf\\string-output_qr.pdf");
				HtmlConverter.convertToPdf(html, fileOutputStream, converterProperties);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// String html = templateEngine.process("factura.html", context);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return 0;
	}

	@Override
	public int factura() {

		ConverterProperties converterProperties = new ConverterProperties();
		converterProperties.setBaseUri("http://localhost:8090/case-use-html-to-pdf/");

		Context context = new Context();
		context.setVariables(model());
		String html = templateEngine.process("factura.html", context);

		try {
			OutputStream fileOutputStream = new FileOutputStream("C:\\pdf\\string-output.pdf");
			HtmlConverter.convertToPdf(html, fileOutputStream, converterProperties);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 0;
	}

	private List<Products> productos() {
		List<Products> items = new ArrayList<>();
		items.add(new Products("Zambo Grande", 30, 50.25));
		items.add(new Products("Zambo", 30, 5));
		items.add(new Products("Caribas", 18, 4));
		items.add(new Products("Caribas Grande", 18, 25.15));
		items.add(new Products("Coca Cola", 30, 15));
		items.add(new Products("Coca Cola 3 Litros", 25, 50));
		items.add(new Products("Gas LPG", 25, 300));
		return items;
	}

	private Map<String, Object> model() {
		Map<String, Object> model = new HashMap<>();
		List<Products> p = productos();
		model.put("items", p);
		model.put("total", p.size());

		return model;
	}

	private Map<String, Object> modelPdf() {
		Map<String, Object> model = new HashMap<>();
		model.put("imagen", "http://localhost:8080/project/images/QR/test.png");

		return model;
	}

	private void generateQRCodeImage(String text, int width, int height, String filePath)
			throws WriterException, IOException {

		try {
			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

			Path path = FileSystems.getDefault().getPath(filePath);
			MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
		} catch (Exception e) {
			// TODO: handle exception
			
			System.out.print(e.getMessage());
		}

	}
}