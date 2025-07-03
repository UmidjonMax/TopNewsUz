package dasturlash.uz.controller;

import dasturlash.uz.dto.AttachDTO;
import dasturlash.uz.service.AttachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1/attach")
public class AttachController {
    @Autowired
    private AttachService attachService;

//    @PostMapping("/upload")
//    public String upload(@RequestParam("file") MultipartFile file) {
//        String fileName = attachService.saveToSystem(file);
//        return fileName;
//    }


    @PostMapping("/upload")
    public ResponseEntity<AttachDTO> create(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(attachService.upload(file));
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        return ResponseEntity.ok(attachService.deletebyId(id));
    }

    @GetMapping("/open/{fileId}")
    public ResponseEntity<Resource> open(@PathVariable String fileId) throws Exception {
        return attachService.open2(fileId);
    }

    @GetMapping("/download/{fineName}")
    public ResponseEntity<Resource> download(@PathVariable("fineName") String fileName) {
        Resource file = attachService.download(fileName);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @GetMapping("/admin/pagination")
    public ResponseEntity<Page<AttachDTO>> pagination(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                      @RequestParam(value = "size", defaultValue = "5") Integer size) {
        return ResponseEntity.ok(attachService.pagination(size, page-1));
    }
}