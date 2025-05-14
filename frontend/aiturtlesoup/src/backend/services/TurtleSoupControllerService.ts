/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { BaseResponse_boolean_ } from '../models/BaseResponse_boolean_';
import type { BaseResponse_List_TurtleSoup_ } from '../models/BaseResponse_List_TurtleSoup_';
import type { BaseResponse_Page_TurtleSoup_ } from '../models/BaseResponse_Page_TurtleSoup_';
import type { BaseResponse_TurtleSoup_ } from '../models/BaseResponse_TurtleSoup_';
import type { TurtleSoup } from '../models/TurtleSoup';
import type { TurtleSoupCreateRequest } from '../models/TurtleSoupCreateRequest';
import type { TurtleSoupQueryRequest } from '../models/TurtleSoupQueryRequest';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class TurtleSoupControllerService {
    /**
     * getAllTurtleSoups
     * @returns BaseResponse_List_TurtleSoup_ OK
     * @throws ApiError
     */
    public static getAllTurtleSoupsUsingGet(): CancelablePromise<BaseResponse_List_TurtleSoup_> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/turtle-soup',
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
    /**
     * createTurtleSoup
     * @param turtleSoup turtleSoup
     * @returns BaseResponse_boolean_ OK
     * @returns any Created
     * @throws ApiError
     */
    public static createTurtleSoupUsingPost(
        turtleSoup: TurtleSoupCreateRequest,
    ): CancelablePromise<BaseResponse_boolean_ | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/turtle-soup',
            body: turtleSoup,
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
    /**
     * listTurtleSoupByPages
     * @param request request
     * @returns BaseResponse_Page_TurtleSoup_ OK
     * @returns any Created
     * @throws ApiError
     */
    public static listTurtleSoupByPagesUsingPost(
        request: TurtleSoupQueryRequest,
    ): CancelablePromise<BaseResponse_Page_TurtleSoup_ | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/turtle-soup/list/page',
            body: request,
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
    /**
     * getTurtleSoupsByPage
     * @param page page
     * @param size size
     * @returns BaseResponse_Page_TurtleSoup_ OK
     * @throws ApiError
     */
    public static getTurtleSoupsByPageUsingGet(
        page: number,
        size: number,
    ): CancelablePromise<BaseResponse_Page_TurtleSoup_> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/turtle-soup/page',
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
     * searchTurtleSoups
     * @param keyword keyword
     * @returns TurtleSoup OK
     * @throws ApiError
     */
    public static searchTurtleSoupsUsingGet(
        keyword: string,
    ): CancelablePromise<Array<TurtleSoup>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/turtle-soup/search',
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
     * getTurtleSoupById
     * @param id id
     * @returns BaseResponse_TurtleSoup_ OK
     * @throws ApiError
     */
    public static getTurtleSoupByIdUsingGet(
        id: string,
    ): CancelablePromise<BaseResponse_TurtleSoup_> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/turtle-soup/{id}',
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
     * updateTurtleSoup
     * @param id id
     * @param turtleSoup turtleSoup
     * @returns boolean OK
     * @returns any Created
     * @throws ApiError
     */
    public static updateTurtleSoupUsingPut(
        id: string,
        turtleSoup: TurtleSoup,
    ): CancelablePromise<boolean | any> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/api/turtle-soup/{id}',
            path: {
                'id': id,
            },
            body: turtleSoup,
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
    /**
     * deleteTurtleSoup
     * @param id id
     * @returns boolean OK
     * @throws ApiError
     */
    public static deleteTurtleSoupUsingDelete(
        id: string,
    ): CancelablePromise<boolean> {
        return __request(OpenAPI, {
            method: 'DELETE',
            url: '/api/turtle-soup/{id}',
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
