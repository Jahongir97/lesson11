package uz.pdp.lesson11.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.pdp.lesson11.entity.Attachment;
import uz.pdp.lesson11.payload.Result;
import uz.pdp.lesson11.service.AttachmentService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/attachment")
public class AttachmentController {

    @Autowired
    AttachmentService attachmentService;

    @PostMapping("/upload")
    public Result uploadFile(MultipartHttpServletRequest request) {
        return attachmentService.uploadFile(request);
    }

    @GetMapping("/info")
    public List<Attachment> getAllFiles() {
        return attachmentService.getAllFiles();
    }

    @GetMapping("/info/{id}")
    public Optional<Attachment> getFileById(@PathVariable Integer id) {
        return attachmentService.getFileById(id);
    }

    @GetMapping("/download/{id}")
    public void getFile(@PathVariable Integer id, HttpServletResponse response) throws IOException {
        attachmentService.getFile(id, response);
    }

    @DeleteMapping("/{id}")
    public Result deleteFile(@PathVariable Integer id) {
        return attachmentService.deleteFile(id);
    }

}




