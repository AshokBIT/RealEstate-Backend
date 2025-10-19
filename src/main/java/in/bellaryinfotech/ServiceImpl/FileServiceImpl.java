package in.bellaryinfotech.ServiceImpl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import in.bellaryinfotech.Repository.FileRepository;
import in.bellaryinfotech.Service.FileService;
import in.bellaryinfotech.model.FileEntity;

@Service
public class FileServiceImpl implements FileService {

    private static final Logger LOG = LoggerFactory.getLogger(FileServiceImpl.class);

    @Autowired
    private FileRepository fileRepository;

    /**
     * Retrieves a file by its ID.
     *
     * @param id The ID of the file
     * @return FileEntity object
     * @throws Exception if file not found or fetch fails
     */
    @Override
    public FileEntity getFile(Long id) throws Exception {
        LOG.info("Fetching file with ID: {}", id);

        try {
            // Using Optional to handle missing file gracefully
            return fileRepository.findById(id).map(file -> {
                LOG.debug("File fetched successfully: Name={}, ID={}", file.getName(), file.getId());
                return file;
            }).orElseThrow(() -> new Exception("File not found with ID: " + id));

        } catch (Exception e) {
            LOG.error("Error while fetching file with ID {}: {}", id, e.getMessage(), e);
            throw new Exception("File fetch failed: " + e.getMessage());
        }
    }

    /**
     * Uploads image and/or video files with metadata.
     */
    @Override
    public FileEntity uploadFile(MultipartFile imageFile, MultipartFile videoFile,
                                 String title, String location, String area, String areaInCents,
                                 String price, String features) throws Exception {

        LOG.info("Starting upload: Title={}, Location={}, Area={}, AreaInCents={}, Price={}, Features={}",
                title, location, area, areaInCents, price, features);

        try {
            if ((imageFile == null || imageFile.isEmpty()) && (videoFile == null || videoFile.isEmpty())) {
                throw new Exception("At least one file (image or video) must be provided");
            }

            byte[] imageBytes = (imageFile != null && !imageFile.isEmpty()) ? imageFile.getBytes() : null;
            byte[] videoBytes = (videoFile != null && !videoFile.isEmpty()) ? videoFile.getBytes() : null;

            FileEntity fileEntity = new FileEntity(
                    imageFile != null ? imageFile.getOriginalFilename() : null,
                    imageFile != null ? imageFile.getContentType() : null,
                    imageBytes,
                    videoFile != null ? videoFile.getOriginalFilename() : null,
                    videoFile != null ? videoFile.getContentType() : null,
                    videoBytes,
                    title,
                    location,
                    area,
                    areaInCents,
                    price,
                    features
            );

            FileEntity savedFile = fileRepository.save(fileEntity);
            LOG.info("File uploaded successfully with ID: {}", savedFile.getId());
            return savedFile;

        } catch (Exception e) {
            LOG.error("File upload failed: {}", e.getMessage(), e);
            throw new Exception("File upload failed: " + e.getMessage());
        }
    }
       
    // ✅ NEW METHODS WITH LOGGING
    /**
     * Retrieves all files from the database.
     */
    @Override
    public List<FileEntity> getAllFiles() {
        LOG.info("Fetching all files from database...");
        List<FileEntity> files = fileRepository.findAll();
        LOG.info("Total files fetched: {}", files.size());
        return files;
    }

    /**
     * Updates file and metadata by ID.
     */
    @Override
    public FileEntity updateFile(Long id, MultipartFile imageFile, MultipartFile videoFile,
                                 String title, String location, String area, String areaInCents,
                                 String price, String features) throws Exception {
        LOG.info("Updating file with ID: {}", id);

        try {
            FileEntity existing = fileRepository.findById(id)
                    .orElseThrow(() -> new Exception("File not found with ID: " + id));

            if (imageFile != null && !imageFile.isEmpty()) {
                LOG.debug("Updating image for file ID: {}", id);
                existing.setName(imageFile.getOriginalFilename());
                existing.setType(imageFile.getContentType());
                existing.setImageData(imageFile.getBytes());
            }

            if (videoFile != null && !videoFile.isEmpty()) {
                LOG.debug("Updating video for file ID: {}", id);
                existing.setVideoName(videoFile.getOriginalFilename());
                existing.setVideoType(videoFile.getContentType());
                existing.setVideoData(videoFile.getBytes());
            }

            existing.setTitle(title);
            existing.setLocation(location);
            existing.setArea(area);
            existing.setAreaInCents(areaInCents);
            existing.setPrice(price);
            existing.setFeatures(features);

            FileEntity updated = fileRepository.save(existing);
            LOG.info("File updated successfully with ID: {}", updated.getId());
            return updated;

        } catch (Exception e) {
            LOG.error("Failed to update file with ID {}: {}", id, e.getMessage(), e);
            throw new Exception("File update failed: " + e.getMessage());
        }
    }

    /**
     * Deletes a single file by its ID.
     */
    @Override
    public void deleteFile(Long id) {
        LOG.info("Attempting to delete file with ID: {}", id);
        try {
            fileRepository.deleteById(id);
            LOG.info("File deleted successfully with ID: {}", id);
        } catch (Exception e) {
            LOG.error("Failed to delete file with ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Deletes all files from the database.
     */
    @Override
    public void deleteAllFiles() {
        LOG.warn("Deleting ALL files from the database...");
        try {
            fileRepository.deleteAll();
            LOG.info("All files deleted successfully.");
        } catch (Exception e) {
            LOG.error("Failed to delete all files: {}", e.getMessage(), e);
            throw e;
        }
    }
    
    
    
    
//  
//  //new methods 
//  @Override
//  public List<FileEntity> getAllFiles() {
//      return fileRepository.findAll();
//  }
//
//  @Override
//  public FileEntity updateFile(Long id, MultipartFile imageFile, MultipartFile videoFile,
//                               String title, String location, String area, String areaInCents,
//                               String price, String features) throws Exception {
//      FileEntity existing = fileRepository.findById(id)
//              .orElseThrow(() -> new Exception("File not found with ID: " + id));
//
//      if (imageFile != null && !imageFile.isEmpty()) {
//          existing.setName(imageFile.getOriginalFilename());
//          existing.setType(imageFile.getContentType());
//          existing.setImageData(imageFile.getBytes());
//      }
//      if (videoFile != null && !videoFile.isEmpty()) {
//          existing.setVideoName(videoFile.getOriginalFilename());
//          existing.setVideoType(videoFile.getContentType());
//          existing.setVideoData(videoFile.getBytes());
//      }
//
//      existing.setTitle(title);
//      existing.setLocation(location);
//      existing.setArea(area);
//      existing.setAreaInCents(areaInCents);
//      existing.setPrice(price);
//      existing.setFeatures(features);
//
//      return fileRepository.save(existing);
//  }
//
//  @Override
//  public void deleteFile(Long id) {
//      fileRepository.deleteById(id);
//  }
//
//  @Override
//  public void deleteAllFiles() {
//      fileRepository.deleteAll();
//  }
}
