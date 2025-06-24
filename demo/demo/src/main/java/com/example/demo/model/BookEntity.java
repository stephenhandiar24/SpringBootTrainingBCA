package com.example.demo.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class BookEntity {
    private Long id;

    @NotBlank(message = "Title may not be blank")
    private String title;
    @NotBlank(message = "Author may not be blank")
    private String author;

    public BookEntity(Long id, String title, String author){
        this.id = id;
        this.title = title;
        this.author= author;
    }

    public String getAuthor() {
        return author;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
