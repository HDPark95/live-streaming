import {http, HttpResponse, StrictResponse} from 'msw'


const User = [
    {id: '1', nickname: 'hyundoo', image: '', accessToken: "1234", refreshToken: "3456"},
]

export const handlers = [
    http.post('/api/v1/login', () => {
        return HttpResponse.json(User[1])
    }),
];