package com.backend.online_qwiz.dto;
import lombok.AllArgsConstructor;
import lombok.*;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarksDto {
    
    private Long quizId;

    private Long userId;
    private int marks;
}
