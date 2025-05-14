/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Page_RoomPlayer_ } from '../models/Page_RoomPlayer_';
import type { RoomPlayer } from '../models/RoomPlayer';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class RoomPlayerControllerService {
    /**
     * getAllRoomPlayers
     * @returns RoomPlayer OK
     * @throws ApiError
     */
    public static getAllRoomPlayersUsingGet(): CancelablePromise<Array<RoomPlayer>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/room-player',
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
    /**
     * createRoomPlayer
     * @param roomPlayer roomPlayer
     * @returns boolean OK
     * @returns any Created
     * @throws ApiError
     */
    public static createRoomPlayerUsingPost(
        roomPlayer: RoomPlayer,
    ): CancelablePromise<boolean | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/room-player',
            body: roomPlayer,
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
    /**
     * getRoomPlayersByPage
     * @param page page
     * @param size size
     * @returns Page_RoomPlayer_ OK
     * @throws ApiError
     */
    public static getRoomPlayersByPageUsingGet(
        page: number,
        size: number,
    ): CancelablePromise<Page_RoomPlayer_> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/room-player/page',
            query: {
                'page': page,
                'size': size,
            },
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
    /**
     * searchRoomPlayers
     * @param keyword keyword
     * @returns RoomPlayer OK
     * @throws ApiError
     */
    public static searchRoomPlayersUsingGet(
        keyword: string,
    ): CancelablePromise<Array<RoomPlayer>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/room-player/search',
            query: {
                'keyword': keyword,
            },
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
    /**
     * getRoomPlayerById
     * @param id id
     * @returns RoomPlayer OK
     * @throws ApiError
     */
    public static getRoomPlayerByIdUsingGet(
        id: string,
    ): CancelablePromise<RoomPlayer> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/room-player/{id}',
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
     * updateRoomPlayer
     * @param id id
     * @param roomPlayer roomPlayer
     * @returns boolean OK
     * @returns any Created
     * @throws ApiError
     */
    public static updateRoomPlayerUsingPut(
        id: string,
        roomPlayer: RoomPlayer,
    ): CancelablePromise<boolean | any> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/api/room-player/{id}',
            path: {
                'id': id,
            },
            body: roomPlayer,
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
    /**
     * deleteRoomPlayer
     * @param id id
     * @returns boolean OK
     * @throws ApiError
     */
    public static deleteRoomPlayerUsingDelete(
        id: string,
    ): CancelablePromise<boolean> {
        return __request(OpenAPI, {
            method: 'DELETE',
            url: '/api/room-player/{id}',
            path: {
                'id': id,
            },
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
            },
        });
    }
}
