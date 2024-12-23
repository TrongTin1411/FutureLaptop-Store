package me.trongtin.project.service.image;

import me.trongtin.project.dto.ImageDTO;
import me.trongtin.project.entity.Image;
import me.trongtin.project.service.MapperService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService extends MapperService<Image, ImageDTO> {

    Image get(Long id);
    List<Image> getAll();
    List<Image> add(MultipartFile[] files, Long productId);
    Image update(Long id, MultipartFile file);
    void delete(Long id);

}
