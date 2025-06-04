import { useState, useEffect } from "react"
import type { User } from '../types/User'
import "../styles/header-style.css"

interface HeaderProps {
    userId: number
}

interface MessageErrorFromServer {
    error: string;
    localDateTime: string;
    path: string;
    status: number;
}

export default function Header({ userId }: HeaderProps) {
    const [user, setUser] = useState<User | null>(null);
    const [messageError, setMessageError] = useState<MessageErrorFromServer | null>(null);

    // нужен loading

    useEffect(() => {

        const abortController = new AbortController();
        const signal = abortController.signal;

        async function getUserById(): Promise<void> {

            const response: Response = await fetch(`http://localhost:8080/api/users/id/${userId}`, { signal });

            try {

                if (!response.ok) {
                    const objErr: MessageErrorFromServer = await response.json();
                    setMessageError(objErr);
                    return;
                }

                const dataUser: User = await response.json();

                setUser(dataUser);
            } catch (error: any) {
                if (error.name === 'AbortError') {
                    console.log('[Header] Запрос пользователя был отменен.');
                } else {
                    console.error('Ошибка при загрузке пользователя', error);
                    setUser(null);
                    setMessageError(null);
                }
            }
        }

        getUserById();

        // console.log('Log of user', user);
        // console.log('Log of messageError', messageError);


        return () => {
            console.log(`[Header Cleanup] Очистка эффекта для userId: ${userId}`);
            abortController.abort();
            setMessageError(null);
        };

    }, [userId]);

    return (
        <>
            <div className="my-header">
                {messageError ? (
                    <>
                        <p>{messageError.error}</p>
                        <p>Время ошибки: {messageError.localDateTime}</p>
                        <p>Путь ошибки: {messageError.path}</p>
                        <p>Статус код: {messageError.status}</p>
                    </>) : user ? (
                        <>
                            <p>{user.name}</p>
                            <p>{user.role}</p>
                            <p>{user.id}</p>
                        </>) : 'Пользователь не найден'}
            </div>
        </>
    )
}