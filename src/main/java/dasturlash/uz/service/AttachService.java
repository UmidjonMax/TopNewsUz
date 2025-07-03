package dasturlash.uz.service;

import dasturlash.uz.dto.AttachDTO;
import dasturlash.uz.entity.AttachEntity;
import dasturlash.uz.exceptions.AppBadException;
import dasturlash.uz.repository.AttachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Service
public class AttachService {
    @Autowired
    private AttachRepository attachRepository;
    @Value("${attach.path}")
    private String attachPath;
    @Value("${localhost.api}")
    private String api;

    public String saveToSystem(MultipartFile file) {
        try {
            File folder = new File(attachPath);
            if (!folder.exists()) {
                folder.mkdir();
            }

            byte[] bytes = file.getBytes();
            Path path = Paths.get(attachPath + "/" + file.getOriginalFilename());
            Files.write(path, bytes);
            return file.getOriginalFilename();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public AttachDTO upload(MultipartFile file) {
        if (file.isEmpty()) {
            throw new AppBadException("File not found");
        }

        try {
            String pathFolder = getYmDString(); // 2025/06/09
            String key = UUID.randomUUID().toString(); // dasdasd-dasdasda-asdasda-asdasd
            String extension = getExtension(file.getOriginalFilename()); // .jpg, .png, .mp4

            // create folder if not exists
            File folder = new File(attachPath + "/" + pathFolder);
            if (!folder.exists()) {
                boolean t = folder.mkdirs();
            }

            // save to system
            byte[] bytes = file.getBytes();
            Path path = Paths.get(attachPath + "/" + pathFolder + "/" + key + "." + extension);
            // attaches/ 2025/06/09/dasdasd-dasdasda-asdasda-asdasd.jpg
            Files.write(path, bytes);

            // save to db
            AttachEntity entity = new AttachEntity();
            entity.setId(key + "." + extension);
            entity.setPath(pathFolder);
            entity.setSize(file.getSize());
            entity.setOrigenName(file.getOriginalFilename());
            entity.setExtension(extension);
            entity.setVisible(true);
            attachRepository.save(entity);

            return toDTO(entity);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public PageImpl<AttachDTO> pagination(int size, int page) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<AttachEntity> entities = attachRepository.findAll(pageRequest);
        List<AttachEntity> entityList = entities.getContent();
        long total = entities.getTotalElements();
        List<AttachDTO> dtoList = new ArrayList<>();
        entityList.forEach(entity -> dtoList.add(toDTO(entity)));

        return new PageImpl<>(dtoList, pageRequest, total);
    }

    public ResponseEntity<Resource> open2(String id) { // d5ab71b2-39a8-4ad2-80b3-729c91c932be.jpg
        AttachEntity entity = getEntity(id);
        Path filePath = Paths.get(attachPath + "/" + entity.getPath() + "/" + entity.getId()).normalize();
        // attaches/2025/06/09/d5ab71b2-39a8-4ad2-80b3-729c91c932be.jpg
        Resource resource = null;
        try {
            resource = new UrlResource(filePath.toUri());
            if (!resource.exists()) {
                throw new RuntimeException("File not found: " + filePath);
            }
            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = "application/octet-stream"; // Fallback content type
            }
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    public String deletebyId(String id) {
        AttachEntity entity = getEntity(id);
        Path filePath = Paths.get(attachPath + "/" + entity.getPath() + "/" + entity.getId()).normalize();
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new AppBadException("File not deleted: " + filePath);
        }
        attachRepository.deleteById(id);
        return "File deleted";
    }
//    public ResponseEntity<Resource> open(String fileName) {
//        Path filePath = Paths.get("attaches/" + fileName).normalize();
//        Resource resource = null;
//        try {
//            resource = new UrlResource(filePath.toUri());
//            if (!resource.exists()) {
//                throw new RuntimeException("File not found: " + fileName);
//            }
//            String contentType = Files.probeContentType(filePath);
//            if (contentType == null) {
//                contentType = "application/octet-stream"; // Fallback content type
//            }
//            return ResponseEntity.ok()
//                    .contentType(MediaType.parseMediaType(contentType))
//                    .body(resource);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }

//    }

    public Resource download(String fileName) {
        try {
            AttachEntity entity = getEntity(fileName);
            Path file = Paths.get(attachPath + "/" + entity.getPath()+ "/" + fileName).normalize();
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not read the file!");
        }
    }

    private String getExtension(String fileName) { // dasd.asdasd.zari.jpg
        int lastIndex = fileName.lastIndexOf(".");
        return fileName.substring(lastIndex + 1);
    }


    private String getYmDString() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DATE);
        return year + "/" + month + "/" + day;
    }

    private AttachDTO toDTO(AttachEntity entity) {
        AttachDTO attachDTO = new AttachDTO();
        attachDTO.setId(entity.getId());
        attachDTO.setOriginName(entity.getOrigenName());
        attachDTO.setSize(entity.getSize());
        attachDTO.setExtension(entity.getExtension());
        attachDTO.setCreatedData(entity.getCreatedDate());
        attachDTO.setUrl(openURL(entity.getId()));
        return attachDTO;
    }

    public AttachEntity getEntity(String id) {
        return attachRepository.findById(id).orElseThrow(() -> {
            throw new AppBadException("File not found");
        });
    }

    public AttachDTO openDTO(String id) {
        AttachDTO attachDTO = new AttachDTO();
        attachDTO.setUrl(api + "/api/v1/attach/open/" + id);
        attachDTO.setId(id);
        return attachDTO;
    }

    public String openURL(String fileName) {
        return api + "/attach/open/" + fileName;
    }

}
