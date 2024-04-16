package com.backend.online_qwiz.model;

import lombok.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Marks {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;
    @ManyToOne
    @JoinColumn(name = "quiz_id")
    @JsonIgnore
    
    private Quiz quiz;

    private int marks;
}
