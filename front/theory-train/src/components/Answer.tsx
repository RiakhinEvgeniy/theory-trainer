import { } from '../styles/question-answer.css'
import RadioButton from './buttons/RadioButton'

interface ID {
    id: string
}

function Answer({ id }: ID) {
    return (
        <>
            <div className="answer">
                <RadioButton name="1" id={id}></RadioButton>
                <p>
                    Lorem ipsum dolor sit amet consectetur adipisicing elit.
                    Vel ratione quam in voluptates, itaque quae recusandae similique officia,
                    praesentium officiis incidunt fugiat, rem laboriosam. Rerum voluptatibus nam iste tenetur quia.
                </p>
            </div>
        </>
    )
}

export default Answer