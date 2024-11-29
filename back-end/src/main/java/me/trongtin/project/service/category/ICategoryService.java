package me.trongtin.project.service.category;

import me.trongtin.project.entity.Category;
import me.trongtin.project.service.ICrudService;

public interface ICategoryService extends ICrudService<Category, Long> {

    Category getByName(String name);

}
