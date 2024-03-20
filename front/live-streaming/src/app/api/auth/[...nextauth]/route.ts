import NextAuth from "next-auth";
import CredentialsProvider from "next-auth/providers/credentials";

const handler = NextAuth({
    secret: process.env.NEXTAUTH_SECRET,
    providers: [
        CredentialsProvider({
            name: 'credentials',
            async authorize(credentials, req) {
                console.log(credentials);
                // const res = await fetch("/your/endpoint", {
                //     method: 'POST',
                //     body: JSON.stringify(credentials),
                //     headers: { "Content-Type": "application/json" }
                // })
                //const user = await res.json()

                // if (res.ok && user) {
                //     return user
                // }

                let newVar = {
                    id: '1',
                    name: 'John Doe',
                    email: 'n9qjv@example.com'
                };
                return newVar
            },
            credentials: {
                username: { label: "Username", type: "text", placeholder: "jsmith" },
                password: { label: "Password", type: "password" }
            }
        })
    ],
    callbacks: {
        // async signIn({ user, account, profile, email, credentials }) {
        //     console.log(user);
        //   return true;
        // },
        async jwt({ token, user, account, profile, isNewUser }) {
            return {...token, ...user}
        },
        async session({ session, token }) {
            console.log(123);
            return session;
        }
    }
})


export { handler as GET, handler as POST }