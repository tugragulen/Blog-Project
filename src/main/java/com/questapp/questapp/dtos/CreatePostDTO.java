package com.questapp.questapp.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreatePostDTO {
    Long id;
    String text;
    String title;
    Long userId;

}
