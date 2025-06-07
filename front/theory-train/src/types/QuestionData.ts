export interface CorrectResponseDTO {
    id: number;
    answer: string;
    variant: 'CORRECT';
}

export interface WrongAnswerResponseDTO {
    id: number;
    answer: string;
    variant: 'INCORRECT';
}

export interface QuestionData {
    id: number;
    questionText: string;
    correctResponseDTO: CorrectResponseDTO;
    wrongAnswerResponseDTOS: WrongAnswerResponseDTO[];
}

export interface AnswerOption {
    id: number;
    text: string;
    isCorrect: boolean;
}