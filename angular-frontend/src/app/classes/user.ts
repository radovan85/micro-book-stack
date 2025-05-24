export class User {

    private _id?: number;
    private _firstName?: string;
    private _lastName?: string;
    private _email?: string;
    private _password?: string;
    private _enabled?: number;
    private _rolesIds?: number[];
    private _authToken?: string;

    // Getter i Setter za id
    get id(): number | undefined {
        return this._id;
    }

    set id(value: number | undefined) {
        this._id = value;
    }

    // Getter i Setter za firstName
    get firstName(): string | undefined {
        return this._firstName;
    }

    set firstName(value: string | undefined) {
        this._firstName = value;
    }

    // Getter i Setter za lastName
    get lastName(): string | undefined {
        return this._lastName;
    }

    set lastName(value: string | undefined) {
        this._lastName = value;
    }

    // Getter i Setter za email
    get email(): string | undefined {
        return this._email;
    }

    set email(value: string | undefined) {
        this._email = value;
    }

    // Getter i Setter za password
    get password(): string | undefined {
        return this._password;
    }

    set password(value: string | undefined) {
        this._password = value;
    }

    // Getter i Setter za enabled
    get enabled(): number | undefined {
        return this._enabled;
    }

    set enabled(value: number | undefined) {
        this._enabled = value;
    }

    // Getter i Setter za rolesIds
    get rolesIds(): number[] | undefined {
        return this._rolesIds;
    }

    set rolesIds(value: number[] | undefined) {
        this._rolesIds = value;
    }

    // Getter i Setter za authToken
    get authToken(): string | undefined {
        return this._authToken;
    }

    set authToken(value: string | undefined) {
        this._authToken = value;
    }
}
