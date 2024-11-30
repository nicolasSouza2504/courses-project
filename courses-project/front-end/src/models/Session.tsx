export interface Session {
    id: number;
    token: string;
}

export function isSession(obj: any): obj is Session {

    return (
        typeof obj === 'object' &&
        obj !== null &&
        typeof obj.id === 'number' &&
        typeof obj.token === 'string'
    );

}
