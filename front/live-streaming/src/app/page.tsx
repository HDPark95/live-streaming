// page.tsx

import { useSession } from 'next-auth/react';
import {auth} from "@/auth";
import Link from "next/link";

export default async function Home() {
    const data = await auth();
    if (!data) {
        return <div><Link href="/login">로그인</Link>You are not logged in</div>;
    }

    return (
        <div>

            <h1>Hello, Next.js!</h1>
            <p>Your User ID is: {data.user.name}</p>
        </div>
    );
}
