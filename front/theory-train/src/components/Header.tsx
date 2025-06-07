import { useState, useEffect } from "react"
import type { User } from '../types/User'
import "../styles/header-style.css"

interface UserIdHeaderProps {
    userId: number
}

interface MessageErrorFromServer {
    error: string;
    localDateTime: string;
    path: string;
    status: number;
}

export default function Header({ userId }: UserIdHeaderProps) {
    const [user, setUser] = useState<User | null>(null);
    const [messageError, setMessageError] = useState<MessageErrorFromServer | null>(null);

    // нужен loading

    useEffect(() => {

        const abortController = new AbortController();
        const signal = abortController.signal;

        async function getUserById(): Promise<void> {
            try {

                const response: Response = await fetch(`http://localhost:8080/api/users/id/${userId}`, { signal });

                if (!response.ok) {
                    const objError: MessageErrorFromServer = await response.json();
                    setMessageError(objError);
                    console.error(`HTTP error! status: ${response.status}`, objError);
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