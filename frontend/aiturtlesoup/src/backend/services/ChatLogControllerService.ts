/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { BaseResponse_List_ChatLog_ } from '../models/BaseResponse_List_ChatLog_';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class ChatLogControllerService {
    /**
     * getAllGameChatLogs
     * @param roomId roomId
     * @returns BaseResponse_List_ChatLog_ OK
     * @throws ApiError
     */
    public static getAllGameChatLogsUsingGet(
        roomId: string,
    ): CancelablePromise<BaseResponse_List_ChatLog_> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/chat-log/room/{roomId}/logs/all',
            query: {
                'roomId': roomId,
            },
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
}
