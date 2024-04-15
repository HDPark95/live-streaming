export default function Home() {
    return (
        <div className="flex flex-col min-h-screen bg-gray-100">
            <div className="bg-red-600 text-white w-full text-center py-4">
                <h1 className="text-2xl font-bold">YouTube Style Homepage</h1>
            </div>

            <main className="flex-grow">
                <div className="container mx-auto px-4 py-8">
                    <p className="text-center text-lg mb-6">Explore videos, playlists, and more.</p>
                    <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
                        {/* 비디오 카드들 */}
                        {Array.from({ length: 8 }).map((_, index) => (
                            <div key={index} className="bg-white p-4 rounded-lg shadow-md">
                                <div className="aspect-w-16 aspect-h-9 bg-gray-300 rounded-md mb-2">
                                    {/* 여기에 이미지 또는 썸네일 컴포넌트를 넣을 수 있습니다 */}
                                </div>
                                <h2 className="text-lg font-semibold mb-1">Video Title {index + 1}</h2>
                                <p className="text-gray-700">This is a placeholder for video description or other relevant information.</p>
                            </div>
                        ))}
                    </div>
                </div>
            </main>
        </div>
    );
}
