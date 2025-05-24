export class Book {

    private _bookId?: number;
    private _title?: string;
    private _author?: string;
    private _description?: string;
    private _imageId?: number;
    private _genreId?: number;
    private _price?: number;

    // Getter i Setter za bookId
    get bookId(): number | undefined {
        return this._bookId;
    }

    set bookId(value: number | undefined) {
        this._bookId = value;
    }

    // Getter i Setter za title
    get title(): string | undefined {
        return this._title;
    }

    set title(value: string | undefined) {
        this._title = value;
    }

    // Getter i Setter za author
    get author(): string | undefined {
        return this._author;
    }

    set author(value: string | undefined) {
        this._author = value;
    }

    // Getter i Setter za description
    get description(): string | undefined {
        return this._description;
    }

    set description(value: string | undefined) {
        this._description = value;
    }

    // Getter i Setter za imageId
    get imageId(): number | undefined {
        return this._imageId;
    }

    set imageId(value: number | undefined) {
        this._imageId = value;
    }

    // Getter i Setter za genreId
    get genreId(): number | undefined {
        return this._genreId;
    }

    set genreId(value: number | undefined) {
        this._genreId = value;
    }

    // Getter i Setter za price
    get price(): number | undefined {
        return this._price;
    }

    set price(value: number | undefined) {
        this._price = value;
    }
}

