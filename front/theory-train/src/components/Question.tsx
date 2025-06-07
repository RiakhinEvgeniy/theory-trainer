import { } from '../styles/question-answer.css'

interface QuestionProps {
    textQuestion: string;
}

function Question({ textQuestion }: QuestionProps) {
    return (
        <>
            <div className="question">
                <p>
                    {textQuestion}
                </p>
            </div>

        </>
    )
}
export default Question