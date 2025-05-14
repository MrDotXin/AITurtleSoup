/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { GameSession } from './GameSession';
import type { OrderItem } from './OrderItem';
export type Page_GameSession_ = {
    countId?: string;
    current?: string;
    maxLimit?: string;
    optimizeCountSql?: boolean;
    orders?: Array<OrderItem>;
    pages?: string;
    records?: Array<GameSession>;
    searchCount?: boolean;
    size?: string;
    total?: string;
};

