package com.backend.online_qwiz.dto;

import com.backend.online_qwiz.model.QuizQuestion;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuizDto {
    @NotNull
    private String title;

    private List<QuizQuestionDto> questions; 
}
