package com.questapp.questapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.questapp.questapp.dtos.CreatePostDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "post")
@Data
@Getter
@Setter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    User user;

    String title;

    @Lob
    @Column(columnDefinition = "text") // varchar(255) olarak algılamasın diye text verildi
    String text;

    public Post(){

    }

    public Post(CreatePostDTO postDTO) {
        this.id = postDTO.getId();
        this.text = postDTO.getText();
        this.title = postDTO.getTitle();
    }
}
