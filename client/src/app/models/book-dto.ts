import { Category } from './category';

export class BookDto {
    public id: number;
    public title: string;
    public description: string;
    public imageUrl: string;
    public authorId: number;
    public authorName: string;
    public categories: Category[];
}
