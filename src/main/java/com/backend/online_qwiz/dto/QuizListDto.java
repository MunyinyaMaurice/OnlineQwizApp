package com.backend.online_qwiz.dto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuizListDto {
    @Valid
    @NotNull
    private Long quizId;
    @NotNull
    private String quizTitle;

    @NotNull
    private String questionText;

    private List<String> options;
    @NotNull
    private int correctOptionIndex;

}
