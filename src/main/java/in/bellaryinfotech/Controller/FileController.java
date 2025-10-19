package in.bellaryinfotech.Controller;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import in.bellaryinfotech.Service.FileService;
import in.bellaryinfotech.dto.FileDTO;
import in.bellaryinfotech.model.FileEntity;

@RestController
@RequestMapping("/api/files")
@CrossOrigin(origins = "*")
public class FileController {

	private static final Logger LOG = LoggerFactory.getLogger(FileController.class);

	@Autowired
	private FileService fileService;

	/**
	 * Uploads a file along with metadata (title, location, area, price, features)
	 */
	@PostMapping("/upload")
	public ResponseEntity<?> uploadFile(@RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
			@RequestParam(value = "videoFile", required = false) MultipartFile videoFile,
			@RequestParam("title") String title, @RequestParam("location") String location,
			@RequestParam("area") String area, @RequestParam("areaInCents") String areaInCents,
			@RequestParam("price") String price, @RequestParam("features") String features) {

		try {
			FileEntity savedFile = fileService.uploadFile(imageFile, videoFile, title, location, area, areaInCents,
					price, features);
			LOG.info("File uploaded successfully: {}", savedFile.getId());
			return ResponseEntity.ok(savedFile);
		} catch (Exception e) {
			LOG.error("Upload failed: {}", e.getMessage(), e);
			return ResponseEntity.internalServerError().body("Upload failed: " + e.getMessage());
		}
	}

	/**
	 * Retrieves a file and its metadata by ID
	 */
	@GetMapping("/{id}")
	public ResponseEntity<?> getFile(@PathVariable Long id) {
		LOG.info("Fetching file and metadata for ID: {}", id);

		try {
			FileEntity file = fileService.getFile(id);
			if (file == null) {
				LOG.warn("File not found with ID: {}", id);
				return ResponseEntity.notFound().build();
			}

			// Convert image and video to Base64 (if available)
			String base64Image = null;
			String base64Video = null;

			if (file.getImageData() != null) {
				base64Image = "data:" + file.getType() + ";base64,"
						+ java.util.Base64.getEncoder().encodeToString(file.getImageData());
			}

			if (file.getVideoData() != null) {
				base64Video = "data:" + file.getVideoType() + ";base64,"
						+ java.util.Base64.getEncoder().encodeToString(file.getVideoData());
			}

			// Build the DTO
			FileDTO response = new FileDTO();
			response.setId(file.getId());
			response.setName(file.getName());
			response.setType(file.getType());
			response.setVideoType(file.getVideoType());
			response.setTitle(file.getTitle());
			response.setLocation(file.getLocation());
			response.setArea(file.getArea());
			response.setAreaInCents(file.getAreaInCents());
			response.setPrice(file.getPrice());
			response.setFeatures(file.getFeatures());
			response.setBase64Image(base64Image);
			response.setBase64Video(base64Video);

			LOG.info("File and metadata retrieved successfully for ID: {}", id);
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			LOG.error("Error fetching file for ID {}: {}", id, e.getMessage(), e);
			return ResponseEntity.internalServerError().body("File fetch failed: " + e.getMessage());
		}
	}


	// ✅ NEW METHODS WITH LOGGING

	/**
	 * Fetch all uploaded files.
	 */
	@GetMapping("/all")
	public ResponseEntity<?> getAllFiles() {
		LOG.info("Fetching all uploaded files...");

		try {
			List<FileDTO> files = fileService.getAllFiles().stream().map(file -> {
				FileDTO dto = new FileDTO();
				dto.setId(file.getId());
				dto.setName(file.getName());
				dto.setType(file.getType());
				dto.setVideoType(file.getVideoType());
				dto.setTitle(file.getTitle());
				dto.setLocation(file.getLocation());
				dto.setArea(file.getArea());
				dto.setAreaInCents(file.getAreaInCents());
				dto.setPrice(file.getPrice());
				dto.setFeatures(file.getFeatures());

				if (file.getImageData() != null) {
					dto.setBase64Image("data:" + file.getType() + ";base64,"
							+ Base64.getEncoder().encodeToString(file.getImageData()));
				}

				if (file.getVideoData() != null) {
					dto.setBase64Video("data:" + file.getVideoType() + ";base64,"
							+ Base64.getEncoder().encodeToString(file.getVideoData()));
				}

				return dto;
			}).collect(Collectors.toList());

			LOG.info("Total files fetched: {}", files.size());
			return ResponseEntity.ok(files);

		} catch (Exception e) {
			LOG.error("Error fetching all files: {}", e.getMessage(), e);
			return ResponseEntity.internalServerError().body("Error fetching all files: " + e.getMessage());
		}
	}

	/**
	 * Updates existing file by ID.
	 */
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateFile(
			@PathVariable Long id,
			@RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
			@RequestParam(value = "videoFile", required = false) MultipartFile videoFile,
			@RequestParam("title") String title,
			@RequestParam("location") String location,
			@RequestParam("area") String area,
			@RequestParam("areaInCents") String areaInCents,
			@RequestParam("price") String price,
			@RequestParam("features") String features) {

		LOG.info("Received update request for file ID: {}", id);

		try {
			FileEntity updated = fileService.updateFile(id, imageFile, videoFile, title, location, area, areaInCents,
					price, features);
			LOG.info("File updated successfully for ID: {}", id);
			return ResponseEntity.ok(updated);
		} catch (Exception e) {
			LOG.error("Update failed for file ID {}: {}", id, e.getMessage(), e);
			return ResponseEntity.internalServerError().body("Update failed: " + e.getMessage());
		}
	}

	/**
	 * Delete file by ID.
	 */
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteFile(@PathVariable Long id) {
		LOG.warn("Received delete request for file ID: {}", id);

		try {
			fileService.deleteFile(id);
			LOG.info("File deleted successfully with ID: {}", id);
			return ResponseEntity.ok("File deleted successfully");
		} catch (Exception e) {
			LOG.error("Failed to delete file with ID {}: {}", id, e.getMessage(), e);
			return ResponseEntity.internalServerError().body("Delete failed: " + e.getMessage());
		}
	}

	/**
	 * Delete all files from database.
	 */
	@DeleteMapping("/deleteAll")
	public ResponseEntity<?> deleteAllFiles() {
		LOG.warn("Received request to delete ALL files.");

		try {
			fileService.deleteAllFiles();
			LOG.info("All files deleted successfully.");
			return ResponseEntity.ok("All files deleted successfully");
		} catch (Exception e) {
			LOG.error("Failed to delete all files: {}", e.getMessage(), e);
			return ResponseEntity.internalServerError().body("Delete all failed: " + e.getMessage());
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	// new methods
//
//	@GetMapping("/all")
//	public ResponseEntity<?> getAllFiles() {
//		try {
//			List<FileDTO> files = fileService.getAllFiles().stream().map(file -> {
//				FileDTO dto = new FileDTO();
//				dto.setId(file.getId());
//				dto.setName(file.getName());
//				dto.setType(file.getType());
//				dto.setVideoType(file.getVideoType());
//				dto.setTitle(file.getTitle());
//				dto.setLocation(file.getLocation());
//				dto.setArea(file.getArea());
//				dto.setAreaInCents(file.getAreaInCents());
//				dto.setPrice(file.getPrice());
//				dto.setFeatures(file.getFeatures());
//
//				if (file.getImageData() != null)
//					dto.setBase64Image("data:" + file.getType() + ";base64,"
//							+ Base64.getEncoder().encodeToString(file.getImageData()));
//
//				if (file.getVideoData() != null)
//					dto.setBase64Video("data:" + file.getVideoType() + ";base64,"
//							+ Base64.getEncoder().encodeToString(file.getVideoData()));
//
//				return dto;
//			}).collect(Collectors.toList());
//
//			return ResponseEntity.ok(files);
//		} catch (Exception e) {
//			return ResponseEntity.internalServerError().body("Error fetching all files: " + e.getMessage());
//		}
//	}
//
//	@PutMapping("/update/{id}")
//	public ResponseEntity<?> updateFile(@PathVariable Long id,
//			@RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
//			@RequestParam(value = "videoFile", required = false) MultipartFile videoFile,
//			@RequestParam("title") String title, @RequestParam("location") String location,
//			@RequestParam("area") String area, @RequestParam("areaInCents") String areaInCents,
//			@RequestParam("price") String price, @RequestParam("features") String features) {
//		try {
//			FileEntity updated = fileService.updateFile(id, imageFile, videoFile, title, location, area, areaInCents,
//					price, features);
//			return ResponseEntity.ok(updated);
//		} catch (Exception e) {
//			return ResponseEntity.internalServerError().body("Update failed: " + e.getMessage());
//		}
//	}
//
//	@DeleteMapping("/delete/{id}")
//	public ResponseEntity<?> deleteFile(@PathVariable Long id) {
//		try {
//			fileService.deleteFile(id);
//			return ResponseEntity.ok("File deleted successfully");
//		} catch (Exception e) {
//			return ResponseEntity.internalServerError().body("Delete failed: " + e.getMessage());
//		}
//	}
//
//	@DeleteMapping("/deleteAll")
//	public ResponseEntity<?> deleteAllFiles() {
//		try {
//			fileService.deleteAllFiles();
//			return ResponseEntity.ok("All files deleted successfully");
//		} catch (Exception e) {
//			return ResponseEntity.internalServerError().body("Delete all failed: " + e.getMessage());
//		}
//	}
	
	
}
