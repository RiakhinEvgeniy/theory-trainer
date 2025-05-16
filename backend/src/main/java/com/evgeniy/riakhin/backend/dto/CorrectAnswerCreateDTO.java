package com.evgeniy.riakhin.backend.dto;

import com.evgeniy.riakhin.backend.entity.VariantOfAnswer;

public record CorrectAnswerCreateDTO(
        String correctAnswer,
        VariantOfAnswer variantOfAnswer) {
}
