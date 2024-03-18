package asmeta.server.controllers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;

@RestController
public class AsmetaModelController {
	
	static final String modelsFolderPath = "src/main/resources/models/";
	static final String librariesFolderPath = "src/main/resources/libraries/";
	
	// MODELS INFO
	
	@Operation(summary = "Models list", description = "Returns the list of the runnable models")
	@GetMapping("/model-list")
	public Map<String, Object> modelList() {
		Map<String, Object> returnVal = new HashMap<>(); 
		
		List<String> models = new ArrayList<String>();
		File[] files = new File(modelsFolderPath).listFiles();
		for (File file : files) {
		    if (file.isFile()) {
		        models.add(file.getName());
		    }
		}	
		
		List<String> libraries = new ArrayList<String>();
		files = new File(librariesFolderPath).listFiles();
		for (File file : files) {
		    if (file.isFile()) {
		        libraries.add(file.getName());
		    }
		}	
		Collections.sort(models, String.CASE_INSENSITIVE_ORDER);
		Collections.sort(libraries, String.CASE_INSENSITIVE_ORDER);
		
		returnVal.put("models", models);
		returnVal.put("libraries", libraries);
		
		return returnVal;
	}
	
	@Operation(summary = "Upload a new model", description = "Upload new model in the runnable model list")
	@PostMapping("/upload-model")
    public ResponseEntity<String> uploadModel(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Il file è vuoto");
        }

        try {
			if (file.isEmpty()) {
				return ResponseEntity.badRequest().body("Il file è vuoto");
			}
			
			Path destinationFile = Paths.get(modelsFolderPath + file.getOriginalFilename()); 
			
			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, destinationFile,
					StandardCopyOption.REPLACE_EXISTING);
			}
			
			return ResponseEntity.ok("Il file è stato caricato con successo");
		}
		catch (IOException e) {
			return ResponseEntity.badRequest().body("Problemus");
		}
    }
	
	@Operation(summary = "Delete a model", description = "Delete a model file")
	@DeleteMapping("/delete-model")
	public ResponseEntity<String> deleteModel(@RequestParam(value = "name") String name) {
		String modelPath = modelsFolderPath + name; 
		File asmFile = new File(modelPath);
		
		if (!asmFile.delete()) {
			ResponseEntity.badRequest().body("Il file non esiste");
		}
		
		
		return ResponseEntity.ok("Il file è stato eliminato con successo");
				
	}
	
	/*
	 *  Library section 
	 */
	
	@Operation(summary = "Upload a new library", description = "Upload new library that can be used by models")
	@PostMapping("/upload-library")
    public ResponseEntity<String> uploadLibrary(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Il file è vuoto");
        }

        try {
			if (file.isEmpty()) {
				return ResponseEntity.badRequest().body("Il file è vuoto");
			}
			
			Path destinationFile = Paths.get(librariesFolderPath + file.getOriginalFilename()); 
			
			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, destinationFile,
					StandardCopyOption.REPLACE_EXISTING);
			}
			
			return ResponseEntity.ok("Il file è stato caricato con successo");
		}
		catch (IOException e) {
			return ResponseEntity.badRequest().body("Problemus");
		}
    }
	
	@DeleteMapping("/delete-library")
	public ResponseEntity<String> deleteLibrary(@RequestParam(value = "name") String name) {
		String modelPath = librariesFolderPath + name; 
		File asmFile = new File(modelPath);
		
		if (!asmFile.delete()) {
			ResponseEntity.badRequest().body("Il file non esiste");
		}
		
		
		return ResponseEntity.ok("Il file è stato eliminato con successo");
				
	}
}
