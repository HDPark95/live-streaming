'use client';
import { signIn } from 'next-auth/react';
import {useRouter} from "next/navigation";


export default function LoginForm() {
    const router = useRouter();
    // 폼 제출 핸들러
    const handleOnSubmit = async (event:any) => {
        event.preventDefault(); // 폼의 기본 제출 동작을 방지합니다.
        // signIn 메서드를 호출하여 로그인 시도
        await signIn('credentials', {
            username: event.target.username.value, // 사용자 이름 입력 필드의 값
            password: event.target.password.value, // 비밀번호 입력 필드의 값
            callbackUrl: "/",
        });

        router.replace("/")
    };

    // 로그인 폼 렌더링
    return (
        <form className="mt-8 space-y-6" onSubmit={handleOnSubmit}>
            <input type="hidden" name="remember" value="true"/>
            <div className="rounded-md shadow-sm -space-y-px">
                <div>
                    <label htmlFor="email-address" className="sr-only">
                        아이디
                    </label>
                    <input
                        id="username"
                        name="username"
                        type="username"
                        required
                        className="appearance-none rounded-none relative block w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-900 rounded-t-md focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 focus:z-10 sm:text-sm"
                        placeholder="아이디"
                    />
                </div>
                <div>
                    <label htmlFor="password" className="sr-only">
                        비밀번호
                    </label>
                    <input
                        id="password"
                        name="password"
                        type="password"
                        required
                        className="appearance-none rounded-none relative block w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-900 rounded-b-md focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 focus:z-10 sm:text-sm"
                        placeholder="비밀번호"
                    />
                </div>
            </div>

            <div className="flex items-center justify-between">
                {/*<div className="flex items-center">
                    <input
                        id="remember-me"
                        name="remember-me"
                        type="checkbox"
                        className="h-4 w-4 text-indigo-600 focus:ring-indigo-500 border-gray-300 rounded"
                    />
                    <label htmlFor="remember-me" className="ml-2 block text-sm text-gray-900">
                        로그인 상태 유지
                    </label>
                </div>

                <div className="text-sm">
                    <a href="#" className="font-medium text-indigo-600 hover:text-indigo-500">
                        비밀번호를 잊으셨나요?
                    </a>
                </div>*/}
            </div>

            <div>
                <button
                    type="submit"
                    className="group relative w-full flex justify-center py-2 px-4 border border-transparent text-sm font-medium rounded-md text-white bg-red-600 hover:bg-red-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-red-500"
                >
                    로그인
                </button>
            </div>
        </form>
    );
}
