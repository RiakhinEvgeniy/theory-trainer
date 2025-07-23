import { useState } from 'react'

function getNextPrevUserById() {
    const [getUserById, setNextUser] = useState<number>(1);

    const nextUser = function getNextUser() {
        setNextUser(() => getUserById + 1);
        return getUserById;
    }

    const prevUser = function getPreviousUser() {
        setNextUser(() => getUserById - 1);
        return getUserById;
    }

    return {
        nextUser,
        prevUser,
        getUserById
    }
}

export default getNextPrevUserById
