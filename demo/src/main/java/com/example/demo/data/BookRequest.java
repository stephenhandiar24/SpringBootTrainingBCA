package com.example.demo.data;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
public class BookRequest {
    @NotBlank(message = "Title may not be blank")
    private String title;
    @NotBlank(message = "Author may not be blank")
    private String author;

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
