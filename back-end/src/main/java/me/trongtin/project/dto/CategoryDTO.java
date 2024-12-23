package me.trongtin.project.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CategoryDTO {

    private long id;
    private String name;
    private List<ProductDTO> products;

}
