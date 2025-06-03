import type { User } from '../types/User'

export default function arrayAllUsers(): User[] {
    const userMock: User[] = [
        { id: 1, name: 'Alice', email: 'alice@gmail.com', role: 'ADNIN' },
        { id: 2, name: 'Bob', email: 'bob@gmail.com', role: 'ADNIN' },
        { id: 3, name: 'Evgeniy', email: 'evgeniy@gmail.com', role: 'ADNIN' }
    ];

    return userMock;
}
