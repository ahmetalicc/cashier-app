package org.sau.toyota.backend.productservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sau.toyota.backend.productservice.entity.Category;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {

    private String name;

    private String description;

    public static CategoryResponse Convert(Category category){
        return CategoryResponse.builder()
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }
}
