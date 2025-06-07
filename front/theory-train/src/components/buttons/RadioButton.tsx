import type { ChangeEvent } from "react";

interface RadioProps {
    name: string;
    id: string;
    checked: boolean;
    onSelected: (event: ChangeEvent<HTMLInputElement>) => void;
}

function RadioButton({ name, id, checked, onSelected }: RadioProps) {
    return (
        <>
            <div>
                <input
                    type="radio"
                    name={name}
                    id={id}
                    checked={checked}
                    onChange={onSelected} />
            </div>
        </>
    )
}

export default RadioButton