// const question =  {
//         "id": 29,
//         "questionText": "Which company developed Java?",
//         "correctResponseDTO": {
//             "id": 29,
//             "correctAnswer": "Sun Microsystems",
//             "variant": "CORRECT"
//         },
//         "wrongAnswerResponseDTOS": [
//             {
//                 "id": 77,
//                 "answer": "Microsoft",
//                 "variant": "INCORRECT"
//             },
//             {
//                 "id": 78,
//                 "answer": "Apple",
//                 "variant": "INCORRECT"
//             }
//         ]
//     }


import { useState } from 'react'

function getNextPrevQuestionById() {
    const [questionById, setNextQuestion] = useState<number>(27);

    const nextQuestion = function getNextUser() {
        setNextQuestion(() => questionById + 1);
        return questionById;
    }

    const prevQuestion = function getPreviousUser() {
        setNextQuestion(() => questionById - 1);
        return questionById;
    }

    return {
        nextQuestion,
        prevQuestion,
        questionById
    }
}

export default getNextPrevQuestionById
