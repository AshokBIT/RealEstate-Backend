package in.bellaryinfotech.Service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import in.bellaryinfotech.model.FileEntity;

public interface FileService {

    FileEntity uploadFile(MultipartFile imageFile,
                          MultipartFile videoFile,
                          String title,
                          String location,
                          String area,
                          String areaInCents,
                          String price,
                          String features) throws Exception;

    FileEntity getFile(Long id) throws Exception;
    
    // New methods
    List<FileEntity> getAllFiles();
    FileEntity updateFile(Long id, MultipartFile imageFile, MultipartFile videoFile,
                          String title, String location, String area, String areaInCents,
                          String price, String features) throws Exception;
    void deleteFile(Long id);
    void deleteAllFiles();
}
