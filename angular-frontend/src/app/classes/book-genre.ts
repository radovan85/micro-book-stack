export class BookGenre {

    private _genreId?: number;
    private _name?: string;
    private _description?: string;

    // Getter i Setter za genreId
    get genreId(): number | undefined {
        return this._genreId;
    }

    set genreId(value: number | undefined) {
        this._genreId = value;
    }

    // Getter i Setter za name
    get name(): string | undefined {
        return this._name;
    }

    set name(value: string | undefined) {
        this._name = value;
    }

    // Getter i Setter za description
    get description(): string | undefined {
        return this._description;
    }

    set description(value: string | undefined) {
        this._description = value;
    }
}

