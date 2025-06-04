interface NextUser {
    buttonText: string
    getUser(): void;
}

function ButtonTest({ buttonText, getUser}: NextUser) {
    return (
        <>
            <button onClick={getUser} style={{ marginTop: "1rem" }} >{buttonText}</button>
        </>
    )
}
export default ButtonTest;