package in.bellaryinfotech.Controller;

import in.bellaryinfotech.dto.FileDTO;
import in.bellaryinfotech.model.FileEntity;
import in.bellaryinfotech.Service.FileService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/files")
@CrossOrigin(origins = "*")
public class FileController {

    private static final Logger LOG = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileService fileService;

    private static final String UPLOAD_DIR = "uploads/";
    private static final String IMAGE_DIR = UPLOAD_DIR + "images/";
    private static final String VIDEO_DIR = UPLOAD_DIR + "videos/";

    // ✅ Upload file
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                                        @RequestParam(value = "videoFile", required = false) MultipartFile videoFile,
                                        @RequestParam("title") String title,
                                        @RequestParam("location") String location,
                                        @RequestParam("area") String area,
                                        @RequestParam("areaInCents") String areaInCents,
                                        @RequestParam("price") String price,
                                        @RequestParam("features") String features) {
        try {
            FileEntity saved = fileService.uploadFile(imageFile, videoFile, title, location, area, areaInCents, price, features);
            LOG.info("File uploaded successfully: ID={}", saved.getId());
            return ResponseEntity.ok(Map.of("message", "Upload successful", "file", convertToDTO(saved)));
        } catch (Exception e) {
            LOG.error("Upload failed: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError()
                    .body(Map.of("message", "Upload failed", "error", e.getMessage()));
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getFileById(@PathVariable Long id) {
        try {
            FileEntity file = fileService.getFileById(id);
            return ResponseEntity.ok(convertToDTO(file));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(Map.of("message", e.getMessage()));
        }
    }


    @GetMapping("/view/{type}/{filename:.+}")
    public ResponseEntity<FileSystemResource> viewFile(
            @PathVariable String type,
            @PathVariable String filename) {

        try {
            // Match the same absolute paths you used in FileServiceImpl
            String baseDir = "C:/bellary_uploads/";
            String folder = type.equalsIgnoreCase("video")
                    ? baseDir + "videos/"
                    : baseDir + "images/";

            File file = new File(folder + filename);

            if (!file.exists()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null);
            }

            MediaType mediaType = type.equalsIgnoreCase("video")
                    ? MediaType.valueOf("video/mp4")
                    : MediaType.IMAGE_JPEG;

            return ResponseEntity.ok()
                    .contentType(mediaType)
                    .body(new FileSystemResource(file));

        } catch (Exception e) {
            LOG.error("Error serving file: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    // ✅ Fetch all
    @GetMapping("/all")
    public ResponseEntity<?> getAllFiles() {
        try {
            List<FileDTO> files = fileService.getAllFiles().stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            LOG.info("Fetched {} files successfully.", files.size());
            return ResponseEntity.ok(files);
        } catch (Exception e) {
            LOG.error("Error fetching files: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError()
                    .body(Map.of("message", "Error fetching files", "error", e.getMessage()));
        }
    }

 // ✅ Update existing file by ID
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateFile(@PathVariable Long id,
                                        @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                                        @RequestParam(value = "videoFile", required = false) MultipartFile videoFile,
                                        @RequestParam("title") String title,
                                        @RequestParam("location") String location,
                                        @RequestParam("area") String area,
                                        @RequestParam("areaInCents") String areaInCents,
                                        @RequestParam("price") String price,
                                        @RequestParam("features") String features) {
        try {
            FileEntity updated = fileService.updateFile(id, imageFile, videoFile, title, location, area, areaInCents, price, features);
            LOG.info("File updated successfully: ID={}", updated.getId());
            return ResponseEntity.ok(Map.of("message", "File updated successfully", "file", convertToDTO(updated)));
        } catch (Exception e) {
            LOG.error("Error updating file ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Update failed", "error", e.getMessage()));
        }
    }

    
    // ✅ Delete by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteFileById(@PathVariable Long id) {
        try {
            fileService.deleteFileById(id);
            LOG.info("File deleted successfully: ID={}", id);
            return ResponseEntity.ok(Map.of("message", "File deleted successfully"));
        } catch (Exception e) {
            LOG.error("Error deleting file ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.internalServerError()
                    .body(Map.of("message", "Delete failed", "error", e.getMessage()));
        }
    }

    // ✅ Utility to convert Entity → DTO
    private FileDTO convertToDTO(FileEntity file) {
        FileDTO dto = new FileDTO();
        dto.setId(file.getId());
        dto.setName(file.getName());
        dto.setType(file.getType());
        dto.setImageUrl(file.getImageUrl());
        dto.setVideoName(file.getVideoName());
        dto.setVideoType(file.getVideoType());
        dto.setVideoUrl(file.getVideoUrl());
        dto.setTitle(file.getTitle());
        dto.setLocation(file.getLocation());
        dto.setArea(file.getArea());
        dto.setAreaInCents(file.getAreaInCents());
        dto.setPrice(file.getPrice());
        dto.setFeatures(file.getFeatures());
        return dto;
    }
}

