package com.backend.online_qwiz.dto;


import com.backend.online_qwiz.model.Quiz;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.JoinColumn;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuizQuestionDto {
    @Valid
    @NotNull
    private Long id;

    @NotNull
    private String questionText;

    private List<String> options;
    @NotNull
    private int correctOptionIndex;
}
