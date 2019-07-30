package com.t_systems.webstore.service.impl;

import com.t_systems.webstore.model.dto.CategoryDto;
import com.t_systems.webstore.model.dto.IngredientDto;
import com.t_systems.webstore.model.dto.ProductDto;
import com.t_systems.webstore.model.dto.TagDto;
import com.t_systems.webstore.model.entity.Category;
import com.t_systems.webstore.model.entity.Ingredient;
import com.t_systems.webstore.model.entity.Product;
import com.t_systems.webstore.model.entity.Tag;
import com.t_systems.webstore.service.api.FilesService;
import com.t_systems.webstore.service.api.MappingService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MappingServiceImpl implements MappingService {
    private final ModelMapper modelMapper;
    private final FilesService filesService;

    @Override
    public ProductDto toProductDto(Product product) {
        return modelMapper.map(product, ProductDto.class);
    }

    @Override
    public CategoryDto toCategoryDto(Category category) {
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public IngredientDto toIngredientDto(Ingredient ingredient) {
        return modelMapper.map(ingredient, IngredientDto.class);
    }

    @Override
    public TagDto toTagDto(Tag tag) {
        return modelMapper.map(tag, TagDto.class);
    }

    @Override
    public Product toProduct(Product product, ProductDto productDto) throws Exception{
        if (product == null)
        {
            product = new Product();
            product.setName(String.valueOf((new Date()).getTime()));
        }
        if (productDto.getName() != null && productDto.getName().trim().length() != 0)
            product.setName(productDto.getName());
        if (productDto.getPrice() != null)
            product.setPrice(productDto.getPrice());
        List<String> image = filesService.saveUploadedFiles(productDto.getFiles());
        if (image.size() > 0) {
            product.setImage(image.get(0));
        }
        if (productDto.getSpicy() != null)
            product.setSpicy(productDto.getSpicy());
        return product;
    }


}
