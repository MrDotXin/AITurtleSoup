/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { GameSession } from '../models/GameSession';
import type { Page_GameSession_ } from '../models/Page_GameSession_';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class GameSessionControllerService {
    /**
     * getAllGameSessions
     * @returns GameSession OK
     * @throws ApiError
     */
    public static getAllGameSessionsUsingGet(): CancelablePromise<Array<GameSession>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/game-session',
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
    /**
     * createGameSession
     * @param gameSession gameSession
     * @returns boolean OK
     * @returns any Created
     * @throws ApiError
     */
    public static createGameSessionUsingPost(
        gameSession: GameSession,
    ): CancelablePromise<boolean | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/game-session',
            body: gameSession,
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
    /**
     * getGameSessionsByPage
     * @param page page
     * @param size size
     * @returns Page_GameSession_ OK
     * @throws ApiError
     */
    public static getGameSessionsByPageUsingGet(
        page: number,
        size: number,
    ): CancelablePromise<Page_GameSession_> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/game-session/page',
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
     * searchGameSessions
     * @param keyword keyword
     * @returns GameSession OK
     * @throws ApiError
     */
    public static searchGameSessionsUsingGet(
        keyword: string,
    ): CancelablePromise<Array<GameSession>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/game-session/search',
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
     * getGameSessionById
     * @param id id
     * @returns GameSession OK
     * @throws ApiError
     */
    public static getGameSessionByIdUsingGet(
        id: string,
    ): CancelablePromise<GameSession> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/game-session/{id}',
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
     * updateGameSession
     * @param gameSession gameSession
     * @param id id
     * @returns boolean OK
     * @returns any Created
     * @throws ApiError
     */
    public static updateGameSessionUsingPut(
        gameSession: GameSession,
        id: string,
    ): CancelablePromise<boolean | any> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/api/game-session/{id}',
            path: {
                'id': id,
            },
            body: gameSession,
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
    /**
     * deleteGameSession
     * @param id id
     * @returns boolean OK
     * @throws ApiError
     */
    public static deleteGameSessionUsingDelete(
        id: string,
    ): CancelablePromise<boolean> {
        return __request(OpenAPI, {
            method: 'DELETE',
            url: '/api/game-session/{id}',
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
