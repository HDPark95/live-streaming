"use client"
import LoginForm from "@/app/login/_component/LoginForm";

export default function Login() {
    return (
        <div className="flex min-h-screen bg-gray-50 items-center justify-center">
            <div className="max-w-md w-full space-y-8 p-10 border rounded-xl bg-white shadow-lg">
                <div className="flex justify-center">
                    <img
                        className="h-12 w-auto"
                        src="https://upload.wikimedia.org/wikipedia/commons/b/b8/YouTube_Logo_2017.svg"
                        alt="YouTube logo"
                    />
                </div>
                <h2 className="mt-6 text-center text-3xl font-extrabold text-gray-900">
                    YouTube에 로그인
                </h2>
                <LoginForm/>
            </div>
        </div>
    );
}