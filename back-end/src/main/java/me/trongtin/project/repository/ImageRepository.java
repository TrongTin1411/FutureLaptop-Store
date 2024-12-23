package me.trongtin.project.repository;

import me.trongtin.project.dto.ImageDTO;
import me.trongtin.project.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {

    @Query("select new me.trongtin.project.dto.ImageDTO(i.fileName, i.fileType, i.downloadUrl) from Image i " +
            "where i.product.id = :productId")
    List<ImageDTO> getImageDTOByProductId(Long productId);

}
