package com.quiz.app.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="quizzes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Quiz implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Subject subject;

    @NotBlank
    private String title;

    @NotBlank
    private String slug;
    
    @NotBlank
    private Integer totalQuestions;

    @NotBlank
    private Integer duration;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Question> questions;

    @CreatedDate
    @JsonIgnore
    private Date createdAt;

    @LastModifiedDate
    @JsonIgnore
    private Date updatedAt;

}
