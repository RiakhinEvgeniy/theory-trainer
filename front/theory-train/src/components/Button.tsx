interface NextUser {
    buttonText: string
    getNextUser(): void;
}

function ButtonTest({ buttonText, getNextUser}: NextUser) {
    return (
        <>
            <button onClick={getNextUser} style={{ marginTop: "1rem" }} >{buttonText}</button>
        </>
    )
}
export default ButtonTest;