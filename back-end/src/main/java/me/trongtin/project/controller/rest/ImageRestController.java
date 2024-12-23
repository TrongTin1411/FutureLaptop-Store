package me.trongtin.project.controller.rest;

import lombok.RequiredArgsConstructor;
import me.trongtin.project.dto.ImageDTO;
import me.trongtin.project.entity.Image;
import me.trongtin.project.service.image.IImageService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/images")
public class ImageRestController {

    private final IImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<List<ImageDTO>> uploadImage(@RequestParam MultipartFile[] files, @RequestParam Long productId) {
        List<Image> images = imageService.add(files, productId);
        return ResponseEntity.ok(images.stream().map(imageService::mapper).toList());
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<?> downloadImage(@PathVariable Long id) throws SQLException {
        Image image = imageService.get(id);
        ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1L, (int) image.getImage().length()));
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"")
                .body(resource);
    }

}
