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
        <form onSubmit={handleOnSubmit}>
            <input type="text" name="username" id="username" placeholder="Username" />
            <input type="password" name="password" id="password" placeholder="Password" />
            <button type="submit">Login</button>
        </form>
    );
}
