package br.com.carloshenreis.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import net.sourceforge.tess4j.Tesseract;

@RestController
@RequestMapping("/ocr")
public class OcrController {
	
	@Value("${lang.traineddata}") private String langTraineddata;
		
	@PostMapping()
	@CrossOrigin(origins = "*")
	public ResponseEntity<String> traduzir(@RequestParam(name="file") MultipartFile file) throws Exception{
		String resultado = "";
		try {
			String ext = FilenameUtils.getExtension(file.getOriginalFilename()); 	
			if (!"png".equals(ext.toLowerCase()) && !"jpg".equals(ext.toLowerCase()) && !"gif".equals(ext.toLowerCase())) {
				return ResponseEntity.badRequest().build();
			}
			BufferedImage img = ImageIO.read(file.getInputStream());
	        Tesseract tesseract = new Tesseract();
	        resultado = "";	     
            tesseract.setLanguage(langTraineddata);
			resultado = tesseract.doOCR(img);	        
		} catch (IOException e) {
			throw new Exception("Erro ao ler arquivo");
		}
		return ResponseEntity.ok(resultado);
	}
}