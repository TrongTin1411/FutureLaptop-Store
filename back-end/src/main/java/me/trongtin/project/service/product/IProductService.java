package me.trongtin.project.service.product;

import me.trongtin.project.dto.ProductDTO;
import me.trongtin.project.entity.Product;
import me.trongtin.project.service.ICrudService;
import me.trongtin.project.service.MapperService;

import java.util.List;

public interface IProductService extends ICrudService<Product, Long>, MapperService<Product, ProductDTO> {

    List<Product> getByName(String name);

}
