package me.trongtin.project.service.category;

import me.trongtin.project.dto.CategoryDTO;
import me.trongtin.project.entity.Category;
import me.trongtin.project.service.ICrudService;
import me.trongtin.project.service.MapperService;

public interface ICategoryService extends ICrudService<Category, Long>, MapperService<Category, CategoryDTO> {

    Category getByName(String name);

}
