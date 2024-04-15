import {http, HttpResponse, StrictResponse} from 'msw'
import {faker} from "@faker-js/faker";


const User = [
    {id: 'hyundoo', nickname: 'hyundoo'},
]

export const handlers = [
    http.post('/api/login', () => {
        return HttpResponse.json(User[1])
    }),
];