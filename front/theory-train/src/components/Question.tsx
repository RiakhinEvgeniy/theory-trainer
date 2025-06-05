import { } from '../styles/question-answer.css'
import type { QuestionText } from '../types/QuestionText'

function Question({ questionText }: QuestionText) {
    return (
        <>
            <div className="question">
                <p>
                    {questionText}
                </p>
            </div>

        </>
    )
}
export default Question