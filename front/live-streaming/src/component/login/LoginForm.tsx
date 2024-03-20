"use client"

import { signIn, useSession } from 'next-auth/react'
export default function LoginForm() {
    const handleOnSubmit = async (event: any) => {
        await signIn('credentials', {
            callbackUrl: '/',
            username: event.target.username.value,
            password: event.target.password.value
        })
    }
    return (
            <form onSubmit={handleOnSubmit}>
                <input type="text" name="username" id="username" placeholder="Username" />
                <input type="password" name="password" id="password" placeholder="Password" />
                <button type="submit">Login</button>
            </form>
    );
}