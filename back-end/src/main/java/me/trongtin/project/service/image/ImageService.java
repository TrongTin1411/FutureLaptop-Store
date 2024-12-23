package me.trongtin.project.service.image;

import lombok.RequiredArgsConstructor;
import me.trongtin.project.dto.ImageDTO;
import me.trongtin.project.entity.Image;
import me.trongtin.project.entity.Product;
import me.trongtin.project.exception.ResourceNotFoundException;
import me.trongtin.project.repository.ImageRepository;
import me.trongtin.project.repository.ProductRepository;
import me.trongtin.project.service.product.IProductService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {

    private final ImageRepository imageRepository;
    private final IProductService productService;

    @Override
    public Image get(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Image id " + id + " not found"));
    }

    @Override
    public List<Image> getAll() {
        return imageRepository.findAll();
    }

    @Override
    public List<Image> add(MultipartFile[] files, Long productId) {
        Product product = productService.get(productId);
        List<Image> images = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                Image savedImage = imageRepository.save(image);
                savedImage.setDownloadUrl("/api/images/download/" + savedImage.getId());
                Image savedFinalImage = imageRepository.save(savedImage);

                images.add(savedFinalImage);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        }
        return images;
    }

    @Override
    public Image update(Long id, MultipartFile file) {
        return imageRepository.findById(id)
                .map(i -> imageRepository.save(updateExistingImage(i, file)))
                .orElseThrow(() -> new ResourceNotFoundException("Image id " + id + " not found"));
    }

    @Override
    public void delete(Long id) {
        imageRepository.findById(id)
                .ifPresentOrElse(imageRepository::delete, () -> {
                    throw new ResourceNotFoundException("Image id " + id + " not found");
                });
    }

    private Image updateExistingImage(Image existingImage, MultipartFile file) {
        try {
            existingImage.setFileName(file.getOriginalFilename());
            existingImage.setFileType(file.getContentType());
            existingImage.setImage(new SerialBlob(file.getBytes()));
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        return existingImage;
    }

    @Override
    public ImageDTO mapper(Image image) {
        return ImageDTO.builder()
                .name(image.getFileName())
                .type(image.getFileType())
                .downloadUrl(image.getDownloadUrl())
                .build();
    }
}
