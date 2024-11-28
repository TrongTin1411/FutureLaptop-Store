package me.trongtin.project.service.product;

import me.trongtin.project.dto.ProductDTO;
import me.trongtin.project.entity.Product;
import me.trongtin.project.service.ICrudService;
import me.trongtin.project.service.MapperService;

public interface IProductService extends ICrudService<Product, Long>, MapperService<Product, ProductDTO> {

    Product getByName(String name);

}
