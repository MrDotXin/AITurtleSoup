/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { BaseResponse_boolean_ } from '../models/BaseResponse_boolean_';
import type { BaseResponse_ChatLog_ } from '../models/BaseResponse_ChatLog_';
import type { BaseResponse_long_ } from '../models/BaseResponse_long_';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class ChatControllerService {
    /**
     * endGame
     * @param id id
     * @returns BaseResponse_boolean_ OK
     * @returns any Created
     * @throws ApiError
     */
    public static endGameUsingPost(
        id: string,
    ): CancelablePromise<BaseResponse_boolean_ | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/chat/room/{id}/endGame',
            path: {
                'id': id,
            },
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
    /**
     * postQueryById
     * @param id id
     * @param message message
     * @returns BaseResponse_ChatLog_ OK
     * @returns any Created
     * @throws ApiError
     */
    public static postQueryByIdUsingPost(
        id: string,
        message?: string,
    ): CancelablePromise<BaseResponse_ChatLog_ | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/chat/room/{id}/postQueryById',
            path: {
                'id': id,
            },
            query: {
                'message': message,
            },
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
    /**
     * startGame
     * @param id id
     * @returns BaseResponse_long_ OK
     * @returns any Created
     * @throws ApiError
     */
    public static startGameUsingPost(
        id: string,
    ): CancelablePromise<BaseResponse_long_ | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/chat/room/{id}/startGame',
            path: {
                'id': id,
            },
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
}
