import NextAuth from "next-auth";
import CredentialsProvider from "next-auth/providers/credentials";

const handler = NextAuth({
    secret: process.env.NEXTAUTH_SECRET,
    providers: [
        CredentialsProvider({
            name: 'credentials',
            async authorize(credentials, req) {
                console.log("call authorize")
                console.log(credentials);
                if (!credentials) {
                    throw new Error("No credentials provided");
                }
                try {
                    const res = await fetch("http://localhost:8080/login", {
                        method: 'POST',
                        body: JSON.stringify(credentials),
                        headers: {"Content-Type": "application/json"}
                    })
                    console.log("res = ", res)

                    if (!res.ok) throw new Error("Login failed");

                    // 쿠키나 헤더에서 필요한 정보를 추출하여 사용자 객체를 생성
                    const accessToken = res.headers.get('access'); // 예시로 access 토큰 추출
                    const user = {
                        accessToken,
                        id: credentials.username, // 예시로 사용자 이름 설정
                    };

                    console.log("user = ", user)
                    return user;
                } catch (error) {
                    console.error(error);
                    throw new Error("Authentication error");
                }
            },
            credentials: {
                username: {label: "Username", type: "text", placeholder: "username"},
                password: {label: "Password", type: "password"}
            }
        })
    ],
    callbacks: {
        async signIn({user, account, profile, email, credentials}) {
            console.log("call signIn user = ", user);
            if (user) {
                return true
            }
            return false;
        },
        async jwt({token, user, account, profile, isNewUser}) {
            console.log("call jwt");
            return {...token, ...user}
        },
        async session({session, token}) {
            console.log("call session");
            return session;
        }
    }
})

export {handler as GET, handler as POST};