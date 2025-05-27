export class BookImage {

    private _id?: number;
    private _name?: string;
    private _contentType?: string;
    private _size?: number;
    private _data?: ArrayBuffer;
    private _bookId?: number;

    // Getter i Setter za id
    get id(): number | undefined {
        return this._id;
    }

    set id(value: number | undefined) {
        this._id = value;
    }

    // Getter i Setter za name
    get name(): string | undefined {
        return this._name;
    }

    set name(value: string | undefined) {
        this._name = value;
    }

    // Getter i Setter za contentType
    get contentType(): string | undefined {
        return this._contentType;
    }

    set contentType(value: string | undefined) {
        this._contentType = value;
    }

    // Getter i Setter za size
    get size(): number | undefined {
        return this._size;
    }

    set size(value: number | undefined) {
        this._size = value;
    }

    // Getter i Setter za data
    get data(): ArrayBuffer | undefined {
        return this._data;
    }

    set data(value: ArrayBuffer | undefined) {
        this._data = value;
    }

    // Getter i Setter za bookId
    get bookId(): number | undefined {
        return this._bookId;
    }

    set bookId(value: number | undefined) {
        this._bookId = value;
    }
}

