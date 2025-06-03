import { useState, useEffect } from "react"
import type { User } from '../types/User'
import "../styles/header-style.css"

interface UserProfileProps {
    userId: number
}

export default function Header({ userId }: UserProfileProps) {
    const [user, setUser] = useState<User | null>(null);

    // нужен loading

    useEffect(() => {
        async function getUserById(): Promise<void> {
            const response: Response = await fetch(`http://localhost:8080/api/users/id/${userId}`);
            try {
                if (!response.ok) {
                    throw new Error(`HTTP error status: ${response.status}`)
                }
                const dataUser: User = await response.json();
                setUser(dataUser);
            } catch (error: any) {
                console.error('Ошибка при загрузке пользователя', error);
                setUser(null);
            }
        }

        getUserById();

    }, [userId]);

    return (
        <>
            <div className="my-header">
                {(user !== null) ? <>
                    <p>{user.name}</p>
                    <p>{user.role}</p>
                    <p>{user.id}</p>
                </> : <p>Пользователь не найден</p>}
            </div>
        </>
    )
}