import NextAuth from "next-auth"
import CredentialsProvider from "next-auth/providers/credentials";
import { cookies } from 'next/headers'
import cookie from 'cookie';

export const {
    handlers: { GET, POST },
    auth,
    signIn,
} = NextAuth({
    pages:{
      signIn : '/login',
    },
    secret: process.env.NEXTAUTH_SECRET,
    providers: [
        CredentialsProvider({
            name: 'credentials',
            async authorize(credentials, req) {
                if (!credentials) {
                    throw new Error("No credentials provided");
                }
                try {
                    const res = await fetch("http://localhost:9090/api/v1/login", {
                        method: 'POST',
                        body: JSON.stringify(credentials),
                        headers: {"Content-Type": "application/json"}
                    })
                    if (!res.ok) {
                        throw new Error("Login failed");
                    }

                    // 쿠키나 헤더에서 필요한 정보를 추출하여 사용자 객체를 생성
                    const accessToken = res.headers.get('access'); // 예시로 access 토큰 추출
                    const user = {
                        accessToken,
                        username: credentials.username, // 예시로 사용자 이름 설정
                        id: credentials.username, // 예시로 사용자 이름 설정
                    };
                    return {
                        id: 1,
                        name: credentials.username,
                        image: null,
                    };
                } catch (error) {
                    console.error(error);
                }
            },
        })
    ],
    callbacks: {
        async jwt({token, user}) {
            return token;
        },
        async session({ session, user, token }) {
            return session;
        },
    }
})