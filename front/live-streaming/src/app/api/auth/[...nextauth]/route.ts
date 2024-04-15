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
                try {
                    const res = await fetch("/login", {
                        method: 'POST',
                        body: JSON.stringify(credentials),
                        headers: {"Content-Type": "application/json"}
                    })
                    const user = await res.json()

                    console.log("user = ", user)

                    if (res.ok && user) {
                        return user
                    } else {
                        throw new Error("Login failed"); // 로그인 실패 처리
                    }
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


export {handler as GET, handler as POST}