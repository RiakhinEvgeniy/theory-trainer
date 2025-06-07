import { useEffect, useMemo, useState } from "react";
import type { AnswerOption, QuestionData, WrongAnswerResponseDTO } from "../types/QuestionData";
import Question from "./Question";
import Answer from "./Answer";

interface MessageErrorFromServer {
    error: string;
    localDateTime: string;
    path: string;
    status: number;
}

interface QuestionIdQuizPageProps {
    idQuestion: number;
}

function QuizPage({ idQuestion }: QuestionIdQuizPageProps) {

    const [question, setQuestion] = useState<QuestionData | null>(null);
    const [messageError, setMessageError] = useState<MessageErrorFromServer | null>(null);
    const [isLoading, setIsLoading] = useState<boolean>(true);
    const [selectedAnswerId, setSelectedAnswerId] = useState<number | null>(null);
    const [isCorrectAnswer, setIsCorrectAnswer] = useState<boolean | null>(null);

    const shuffledAnswers = useMemo(() => {
        if (!question) {
            return [];
        }

        const allAnswers = [
            { id: question.correctResponseDTO.id, text: question.correctResponseDTO.answer, isCorrect: true },
            ...question.wrongAnswerResponseDTOS.map((wn: WrongAnswerResponseDTO) => ({
                id: wn.id,
                text: wn.answer,
                isCorrect: false
            }))
        ];

        return allAnswers.sort(() => Math.random() - 0.5);

    }, [question]);

    useEffect(() => {

        const abortController = new AbortController();
        const signal = abortController.signal;

        // setQuestion(null);
        setMessageError(null);
        // setIsLoading(true);
        setSelectedAnswerId(null);
        setIsCorrectAnswer(null);

        async function fetchQuestionById(): Promise<void> {
            try {
                console.log(idQuestion);
                const response: Response = await fetch(`http://localhost:8080/api/questions/id/${idQuestion}`, { signal });
                console.log(response);

                if (!response.ok) {
                    const objError: MessageErrorFromServer = await response.json();
                    setMessageError(objError);
                    console.error(`HTTP error! status: ${response.status}`, objError);
                    return;
                }

                const questionData: QuestionData = await response.json();
                setQuestion(questionData);

            } catch (err: any) {
                if (err.name === 'AbortError') {
                    console.log('[QuizPage] запрос был отменен ', err);
                } else {
                    console.error('[QuizPage] ошибка при загрузке вопроса ', err);
                    setMessageError(
                        {
                            error: 'Неизвестная ошибка при загрузке данных',
                            localDateTime: new Date().toISOString(),
                            path: `api/questions/id/${idQuestion}`,
                            status: 500
                        }
                    );

                    setQuestion(null);
                }
            } finally {
                if (!signal.aborted) {
                    setIsLoading(false);
                }
            }
        }

        fetchQuestionById();

        return () => {
            console.log(`QuuizPage Cleanup] очистка запроса для idQuestion ${idQuestion}`);
            abortController.abort();
        }

    }, [idQuestion]);

    const handleIdQuestion = (id: number): void => {
        setSelectedAnswerId(id);
        console.log(`Установлен ID для ответа ${id}`);
        // added logic for checking answer
        if (id === question?.correctResponseDTO.id) {
            setIsCorrectAnswer(true);
            console.log('Правильно!!!');
        } else {
            setIsCorrectAnswer(false);
            console.log('Попробуйте еще раз(((');
        }
    }

    if (isLoading) {
        return (<div className="quiz-page">Загрузка вопроса...</div>);
    };

    if (messageError) {
        return (
            <div className="quiz-page">
                <p style={{ color: 'red' }}> Ошибка загрузки... {messageError.error}</p>
                <p>Статус: {messageError.status}</p>
                <p>Дата и время: {messageError.localDateTime}</p>
            </div>
        )
    };

    if (!question) {

        return (
            <div className="quiz-page">Вопрос не найден</div>
        )
    };

    return (
        <>
            <Question textQuestion={question.questionText}></Question>
            <div>
                {shuffledAnswers.map((answerOption: AnswerOption) => (
                    <Answer
                        key={answerOption.id}
                        answer={answerOption}
                        groupNameOfRadioBtn={`question-${question.id}`}
                        onSelected={handleIdQuestion}
                        isSelected={selectedAnswerId === answerOption.id}
                    />
                ))}
            </div>

        {isCorrectAnswer !== null && (
            <div className="answer" style={{ marginTop: '20px', fontSize: '20px', fontWeight: 'bold' }}>
                {isCorrectAnswer ? (
                    <span style={{ color: 'green' }}>Ответ ВЕРНЫЙ!</span>
                ) : (
                    <span style={{ color: 'red' }}>Ответ НЕВЕРНЫЙ.</span>
                )}
            </div>
        )}
        </>
    )
}

export default QuizPage