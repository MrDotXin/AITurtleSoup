/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { BaseResponse_boolean_ } from '../models/BaseResponse_boolean_';
import type { BaseResponse_ChatRoom_ } from '../models/BaseResponse_ChatRoom_';
import type { BaseResponse_long_ } from '../models/BaseResponse_long_';
import type { BaseResponse_Page_ChatRoom_ } from '../models/BaseResponse_Page_ChatRoom_';
import type { ChatRoom } from '../models/ChatRoom';
import type { ChatRoomCreateRequest } from '../models/ChatRoomCreateRequest';
import type { ChatRoomQueryRequest } from '../models/ChatRoomQueryRequest';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class ChatRoomControllerService {
    /**
     * createChatRoom
     * @param request request
     * @returns BaseResponse_long_ OK
     * @returns any Created
     * @throws ApiError
     */
    public static createChatRoomUsingPost(
        request: ChatRoomCreateRequest,
    ): CancelablePromise<BaseResponse_long_ | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/chat-room',
            body: request,
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
    /**
     * listChatRoomByPages
     * @param request request
     * @returns BaseResponse_Page_ChatRoom_ OK
     * @returns any Created
     * @throws ApiError
     */
    public static listChatRoomByPagesUsingPost(
        request: ChatRoomQueryRequest,
    ): CancelablePromise<BaseResponse_Page_ChatRoom_ | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/chat-room/list/page',
            body: request,
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
    /**
     * getChatRoomById
     * @param id id
     * @returns BaseResponse_ChatRoom_ OK
     * @throws ApiError
     */
    public static getChatRoomByIdUsingGet(
        id: string,
    ): CancelablePromise<BaseResponse_ChatRoom_> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/chat-room/{id}',
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
     * deleteChatRoom
     * @param id id
     * @returns BaseResponse_boolean_ OK
     * @throws ApiError
     */
    public static deleteChatRoomUsingDelete(
        id: string,
    ): CancelablePromise<BaseResponse_boolean_> {
        return __request(OpenAPI, {
            method: 'DELETE',
            url: '/api/chat-room/{id}',
            path: {
                'id': id,
            },
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
            },
        });
    }
    /**
     * updateChatRoom
     * @param chatRoom chatRoom
     * @param id id
     * @returns BaseResponse_boolean_ OK
     * @returns any Created
     * @throws ApiError
     */
    public static updateChatRoomUsingPut(
        chatRoom: ChatRoom,
        id: string,
    ): CancelablePromise<BaseResponse_boolean_ | any> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/api/chat-room/{id}/update',
            path: {
                'id': id,
            },
            body: chatRoom,
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
    /**
     * updateTurtleSoup
     * @param id id
     * @param soupId soupId
     * @returns BaseResponse_boolean_ OK
     * @returns any Created
     * @throws ApiError
     */
    public static updateTurtleSoupUsingPost(
        id: string,
        soupId: string,
    ): CancelablePromise<BaseResponse_boolean_ | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/chat-room/{id}/updateTurtleSoup',
            path: {
                'id': id,
            },
            query: {
                'soupId': soupId,
            },
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
}
