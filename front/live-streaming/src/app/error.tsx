'use client'

import { useEffect } from 'react'

interface ErrorProps {
    error: Error & { digest?: string };  // Including an optional 'digest' property
    reset: () => void;
}

export default function Error({ error, reset }: ErrorProps) {
    useEffect(() => {
        console.error(error);
    }, [error]);

    return (
        <div className="flex flex-col items-center justify-center min-h-screen bg-white p-4">
            <h2 className="text-xl md:text-2xl font-bold text-red-600 mb-4">Something went wrong!</h2>
            <p className="text-gray-600 text-center mb-4">
                Sorry for the inconvenience. Please try reloading the page, or click the button below to retry.
            </p>
            <button
                onClick={() => reset()}
                className="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline transition-colors"
            >
                Try again
            </button>
        </div>
    );
}