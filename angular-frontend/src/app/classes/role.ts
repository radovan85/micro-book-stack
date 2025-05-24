export class Role {

    private _id?: number;
    private _role?: string;
    private _usersIds?: number[];

    // Getter i Setter za id
    get id(): number | undefined {
        return this._id;
    }

    set id(value: number | undefined) {
        this._id = value;
    }

    // Getter i Setter za role
    get role(): string | undefined {
        return this._role;
    }

    set role(value: string | undefined) {
        this._role = value;
    }

    // Getter i Setter za usersIds
    get usersIds(): number[] | undefined {
        return this._usersIds;
    }

    set usersIds(value: number[] | undefined) {
        this._usersIds = value;
    }
}
