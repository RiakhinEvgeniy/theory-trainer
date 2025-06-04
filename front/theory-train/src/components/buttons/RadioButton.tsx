interface RadioProps {
    name: string;
    id: string
}

function RadioButton({ name, id }: RadioProps) {
    return (
        <>
            <div>
                <input type="radio" name={name} id={id} />
            </div>
        </>
    )
}

export default RadioButton