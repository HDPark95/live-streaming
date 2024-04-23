"use client";
import { useEffect } from "react";

export const MSWComponent = () => {
    useEffect(() => {
        if (typeof window !== 'undefined') { //브라우저 검사
            if (process.env.NEXT_PUBLIC_API_MOCKING === "enabled") {
                require("@/mocks/browser");
            }
        }
    }, []);

    return null;
};