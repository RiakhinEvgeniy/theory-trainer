import type { User } from '../types/User';

type Props = {
    user: User;
}

export default function UserCard({ user }: Props) {
    return (
        <div className="user-card">
            <p>ID: {user.id}</p>
            <p>Name: {user.name}</p>
            <p>Email: {user.email}</p>
            <p>Role: {user.role}</p>
        </div>
    )
}