package com.quiz.app.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="attemp_answers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttemptAnswer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Attempt attempt;

    @OneToOne
    private Question question;

    @OneToOne
    private Answer answer;

    @CreatedDate
    @JsonIgnore
    private Date createdAt;

    @LastModifiedDate
    @JsonIgnore
    private Date updatedAt;



    
}
