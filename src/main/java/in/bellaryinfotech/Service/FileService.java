package in.bellaryinfotech.Service;

import in.bellaryinfotech.model.FileEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {

    FileEntity uploadFile(MultipartFile imageFile, MultipartFile videoFile,
                          String title, String location, String area, String areaInCents,
                          String price, String features) throws Exception;

    List<FileEntity> getAllFiles();

    FileEntity getFileById(Long id) throws Exception;

    FileEntity updateFile(Long id, MultipartFile imageFile, MultipartFile videoFile,
                          String title, String location, String area, String areaInCents,
                          String price, String features) throws Exception;

    void deleteFileById(Long id) throws Exception;

    void deleteAllFiles();
}
