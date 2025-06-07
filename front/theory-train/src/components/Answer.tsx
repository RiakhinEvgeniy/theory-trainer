import { } from '../styles/question-answer.css'
import RadioButton from './buttons/RadioButton'
import type { AnswerOption } from '../types/QuestionData'
import type React from 'react';

interface AnswerProps {
    answer: AnswerOption;
    groupNameOfRadioBtn: string;
    onSelected: (selectedAnswerId: number) => void;
    isSelected: boolean;
}

function Answer({ answer, groupNameOfRadioBtn, onSelected, isSelected }: AnswerProps) {
    const handleRadioAnswer = (_event: React.ChangeEvent<HTMLInputElement>) => {
        onSelected(answer.id);
    }
    return (
        <>
            <div className="answer">
                <RadioButton
                    name={groupNameOfRadioBtn}
                    id={`answer-${answer.id}`}
                    checked={isSelected}
                    onSelected={handleRadioAnswer}
                ></RadioButton>
                <label htmlFor={`answer-${answer.id}`}>
                    <p>
                        {answer.text}
                    </p>
                </label>

            </div>
        </>
    )
}

export default Answer