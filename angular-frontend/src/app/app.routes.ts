import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { authGuard } from './guards/auth.guard';
import { unidentifiedGuard } from './guards/unidentified.guard';
import { GenreListComponent } from './components/genres/genre-list/genre-list.component';
import { GenreFormComponent } from './components/genres/genre-form/genre-form.component';
import { adminGuard } from './guards/admin.guard';
import { GenreUpdateFormComponent } from './components/genres/genre-update-form/genre-update-form.component';
import { BookListComponent } from './components/books/book-list/book-list.component';
import { BookFormComponent } from './components/books/book-form/book-form.component';
import { BookDetailsComponent } from './components/books/book-details/book-details.component';
import { BookUpdateFormComponent } from './components/books/book-update-form/book-update-form.component';
import { BookImageFormComponent } from './components/books/book-image-form/book-image-form.component';
import { UserListComponent } from './components/users/user-list/user-list.component';
import { RegistrationFormComponent } from './components/users/registration-form/registration-form.component';
import { RegistrationCompletedComponent } from './components/users/registration-completed/registration-completed.component';

export const routes: Routes = [

    {
        path: `home`,
        component: HomeComponent,
        canActivate: [authGuard]
    },

    {
        path: `login`,
        component: LoginComponent,
        canActivate: [unidentifiedGuard]
    },

    {
        path: `genres`,
        component: GenreListComponent,
        canActivate: [authGuard]
    },

    {
        path: `genres/addGenre`,
        component: GenreFormComponent,
        canActivate: [adminGuard]
    },

    {
        path: `genres/updateGenre/:genreId`,
        component: GenreUpdateFormComponent,
        canActivate: [adminGuard]
    },

    {
        path: `books`,
        component: BookListComponent,
        canActivate: [authGuard]
    },

    {
        path: `books/addBook`,
        component: BookFormComponent,
        canActivate: [adminGuard]
    },

    {
        path: `books/bookDetails/:bookId`,
        component: BookDetailsComponent,
        canActivate: [authGuard]
    },

    {
        path: `books/updateBook/:bookId`,
        component: BookUpdateFormComponent,
        canActivate: [adminGuard]
    },

    {
        path: `books/addImage/:bookId`,
        component: BookImageFormComponent,
        canActivate: [adminGuard]
    },

    {
        path: `users`,
        component: UserListComponent,
        canActivate: [adminGuard]
    },

    {
        path: `register`,
        component: RegistrationFormComponent,
        canActivate: [unidentifiedGuard]
    },

    {
        path: `register/completed`,
        component: RegistrationCompletedComponent,
        canActivate: [unidentifiedGuard]
    },

    {
        path: `**`,
        redirectTo: `/home`,
        pathMatch: `full`
    }
];
