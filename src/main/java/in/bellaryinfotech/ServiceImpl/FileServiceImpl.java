package in.bellaryinfotech.ServiceImpl;

import in.bellaryinfotech.model.FileEntity;
import in.bellaryinfotech.Repository.FileRepository;
import in.bellaryinfotech.Service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Optional;

@Service
public class FileServiceImpl implements FileService {

    private static final Logger LOG = LoggerFactory.getLogger(FileServiceImpl.class);

    @Autowired
    private FileRepository fileRepository;

    // ✅ Absolute paths for Windows
    private static final String UPLOAD_DIR = "C:/bellary_uploads/";
    private static final String IMAGE_DIR = UPLOAD_DIR + "images/";
    private static final String VIDEO_DIR = UPLOAD_DIR + "videos/";

    // ✅ Upload new file (Create)
    @Override
    public FileEntity uploadFile(MultipartFile imageFile, MultipartFile videoFile,
                                 String title, String location, String area, String areaInCents,
                                 String price, String features) throws Exception {

        LOG.info("Starting upload: title={}, location={}, area={}, price={}", title, location, area, price);

        if ((imageFile == null || imageFile.isEmpty()) && (videoFile == null || videoFile.isEmpty())) {
            throw new Exception("At least one file (image or video) must be provided");
        }

        // Create folders if they don't exist
        new File(IMAGE_DIR).mkdirs();
        new File(VIDEO_DIR).mkdirs();

        String imageUrl = null;
        String videoUrl = null;

        if (imageFile != null && !imageFile.isEmpty()) {
            String imageFileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
            File dest = new File(IMAGE_DIR + imageFileName);
            imageFile.transferTo(dest);

            // URL to serve via controller
            imageUrl = "/api/files/view/image/" + imageFileName;
        }

        if (videoFile != null && !videoFile.isEmpty()) {
            String videoFileName = System.currentTimeMillis() + "_" + videoFile.getOriginalFilename();
            File dest = new File(VIDEO_DIR + videoFileName);
            videoFile.transferTo(dest);

            // URL to serve via controller
            videoUrl = "/api/files/view/video/" + videoFileName;
        }

        FileEntity entity = new FileEntity(
                imageFile != null ? imageFile.getOriginalFilename() : null,
                imageFile != null ? imageFile.getContentType() : null,
                imageUrl,
                videoFile != null ? videoFile.getOriginalFilename() : null,
                videoFile != null ? videoFile.getContentType() : null,
                videoUrl,
                title, location, area, areaInCents, price, features
        );

        FileEntity saved = fileRepository.save(entity);
        LOG.info("File uploaded successfully with ID: {}", saved.getId());
        return saved;
    }

    // ✅ Fetch all
    @Override
    public List<FileEntity> getAllFiles() {
        LOG.info("Fetching all uploaded files...");
        List<FileEntity> files = fileRepository.findAll();
        LOG.info("Total files fetched: {}", files.size());
        return files;
    }

    // ✅ Fetch by ID
    @Override
    public FileEntity getFileById(Long id) throws Exception {
        LOG.info("Fetching file with ID: {}", id);
        Optional<FileEntity> fileOpt = fileRepository.findById(id);
        if (fileOpt.isPresent()) return fileOpt.get();
        else throw new Exception("File not found with ID: " + id);
    }

    // ✅ Update existing file
    @Override
    public FileEntity updateFile(Long id, MultipartFile imageFile, MultipartFile videoFile,
                                 String title, String location, String area, String areaInCents,
                                 String price, String features) throws Exception {

        Optional<FileEntity> fileOpt = fileRepository.findById(id);
        if (!fileOpt.isPresent()) throw new Exception("File not found for update with ID: " + id);

        FileEntity existing = fileOpt.get();

        new File(IMAGE_DIR).mkdirs();
        new File(VIDEO_DIR).mkdirs();

        if (imageFile != null && !imageFile.isEmpty()) {
            String imageFileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
            File dest = new File(IMAGE_DIR + imageFileName);
            imageFile.transferTo(dest);
            existing.setName(imageFile.getOriginalFilename());
            existing.setType(imageFile.getContentType());
            existing.setImageUrl("/api/files/view/image/" + imageFileName);
        }

        if (videoFile != null && !videoFile.isEmpty()) {
            String videoFileName = System.currentTimeMillis() + "_" + videoFile.getOriginalFilename();
            File dest = new File(VIDEO_DIR + videoFileName);
            videoFile.transferTo(dest);
            existing.setVideoName(videoFile.getOriginalFilename());
            existing.setVideoType(videoFile.getContentType());
            existing.setVideoUrl("/api/files/view/video/" + videoFileName);
        }

        existing.setTitle(title);
        existing.setLocation(location);
        existing.setArea(area);
        existing.setAreaInCents(areaInCents);
        existing.setPrice(price);
        existing.setFeatures(features);

        return fileRepository.save(existing);
    }

    // ✅ Delete by ID
    @Override
    public void deleteFileById(Long id) throws Exception {
        Optional<FileEntity> fileOpt = fileRepository.findById(id);
        if (fileOpt.isPresent()) fileRepository.deleteById(id);
        else throw new Exception("File not found with ID: " + id);
    }

    // ✅ Delete all files
    @Override
    public void deleteAllFiles() {
        fileRepository.deleteAll();
    }
}

