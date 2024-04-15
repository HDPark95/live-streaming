import NextAuth from "next-auth"
import CredentialsProvider from "next-auth/providers/credentials";
import { cookies } from 'next/headers'
import cookie from 'cookie';

export const {
    handlers: { GET, POST },
    auth,
    signIn,
} = NextAuth({
    pages: {
        signIn: "/login",
    },
    callbacks: {
        jwt({ token}) {
            return token;
        },
        session({ session, newSession, user}) {
            return session;
        }
    },
    providers: [
        CredentialsProvider({
            credentials: {
                username: { label: "Username", type: "text", placeholder: "username" },
                password: {  label: "Password", type: "password" },

            },
            async authorize(credentials) {
                const authResponse = await fetch(`${process.env.NEXT_PUBLIC_BASE_URL}/api/login`, {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify({
                        id: credentials?.username,
                        password: credentials?.password,
                    }),
                })
                let setCookie = authResponse.headers.get('Set-Cookie');

                if (setCookie) {
                    const parsed = cookie.parse(setCookie);
                    cookies().set('connect.sid', parsed['connect.sid'], parsed); // 브라우저에 쿠키를 심어주는 것
                }
                if (!authResponse.ok) {
                    return null
                }

                const user = await authResponse.json()
                return {
                    email: user.id,
                    name: user.nickname,
                    image: user.image,
                    ...user,
                }
            }
        }),
    ]
});