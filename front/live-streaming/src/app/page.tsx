// page.tsx

import { useSession } from 'next-auth/react';

export default function Home() {
    const { data: session, status } = useSession();

    console.log("session.status",session, status);
    if (status === 'loading') {
        return <div>Loading...</div>;
    }

    if (!session || !session.user) {
        return <div>You are not logged in</div>;
    }

    return (
        <div>
            <h1>Hello, Next.js!</h1>
            <p>Your User ID is: {session.user.name}</p>
        </div>
    );
}
