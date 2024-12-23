package me.trongtin.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
public class ImageDTO {

    private String name;
    private String type;
    private String downloadUrl;

}
