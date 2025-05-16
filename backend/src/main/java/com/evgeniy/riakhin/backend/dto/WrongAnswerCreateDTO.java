package com.evgeniy.riakhin.backend.dto;

import com.evgeniy.riakhin.backend.entity.VariantOfAnswer;

public record WrongAnswerCreateDTO(
        String answer,
        VariantOfAnswer variantOfAnswer) {

    // todo сделать валидацию
}
