import { Copy } from './copy';

export class User {
    id: number;
    firstName: string;
    lastName: string;
    email: string;
    role: number;
    copies: Copy[] = [];
}
