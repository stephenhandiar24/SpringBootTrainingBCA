package com.example.demo.data;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookResponse {
    private Long id;
    private String title;
    private String author;

    public BookResponse(Long id, String title, String author){
        this.id = id;
        this.title = title;
        this.author= author;
    }

}
