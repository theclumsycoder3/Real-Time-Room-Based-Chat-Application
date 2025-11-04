import { httpClient } from "../config/AxiosHelper"

export const createRoomAPI = async (roomDetail) => {
    const response = await httpClient.post(`/api/v1/rooms`, roomDetail, {
        headers: {
            "Content-Type": "application/json"
        }
    });
    return response.data;
};

export const joinChatAPI = async (roomId) => {
    const response = await httpClient.get(`/api/v1/rooms/${roomId}`, {
        headers: {
            "Content-Type": "text/plain"
        }
    });
    return response.data;
};

export const getMessages = async (roomId, size = 50, page = 0) => {
    const response = await httpClient.get(`/api/v1/rooms/${roomId}/messages?size=${size}&page=${page}`, {
        headers: {
            'Content-Type': 'text/plain'
        }
    });
    return response.data;
}