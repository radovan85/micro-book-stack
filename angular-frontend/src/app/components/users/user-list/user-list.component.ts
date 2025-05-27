import { CommonModule } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { UserService } from '../../../services/user.service';
import { User } from '../../../classes/user';

@Component({
  selector: 'app-user-list',
  imports: [CommonModule],
  templateUrl: './user-list.component.html',
  styleUrl: './user-list.component.css'
})
export class UserListComponent implements OnInit {

  private userService = inject(UserService);
  private userList: User[] = [];
  private pageSize = 5;
  private currentPage = 1;
  private totalPages = 1;
  private paginatedUsers: User[] = [];

  ngOnInit(): void {
    Promise.all([
      this.collectAllUsers()
    ])

      .catch((error) => {
        console.error('Error loading functions', error);
      });
  }

  collectAllUsers() {
    this.userService.collectAllUsers()
      .then((response) => {
        this.userList = response.data;
        this.totalPages = Math.ceil(this.userList.length / this.pageSize);
        this.setPage(1);
      })
  }

  setPage(page: number) {
    if (page < 1 || page > this.totalPages) {
      return;
    }
    this.currentPage = page;
    this.paginatedUsers = this.userList.slice((page - 1) * this.pageSize, page * this.pageSize);
  }

  nextPage() {
    this.setPage(this.currentPage + 1);
  }

  prevPage() {
    this.setPage(this.currentPage - 1);
  }

  public getPaginatedUsers(): User[] {
    return this.paginatedUsers;
  }

  public getCurrentPage(): number {
    return this.currentPage;
  }

  public getTotalPages(): number {
    return this.totalPages;
  }

  public getUserList() {
    return this.userList;
  }

  deleteUser(userId: any): Promise<any> {
    return new Promise(() => {
      if (confirm(`Remove this user?`)) {
        this.userService.deleteUser(userId)
          .then(() => {
            this.userList = this.userList.filter(
              (tempUser) => tempUser.id !== userId
            );

            this.totalPages = Math.max(
              1,
              Math.ceil(this.userList.length / this.pageSize)
            );

            if (
              (this.currentPage - 1) * this.pageSize >=
              this.userList.length &&
              this.currentPage > 1
            ) {
              this.currentPage--;
            }

            this.setPage(this.currentPage);
            this.paginatedUsers = [
              ...this.userList.slice(
                (this.currentPage - 1) * this.pageSize,
                this.currentPage * this.pageSize
              ),
            ];
          })
          .catch((error) => {
            if (error.response.status === 406) {
              alert(error.response.data);
            } else {
              alert(`Failed!`);
            }
          });
      }
    });
  }


  suspendUser(userId: any) {
    if (confirm(`Suspend this user?`)) {
      this.userService.suspendUser(userId)
        .then(() => {
          this.collectAllUsers();
        })

        .then(() => {
          this.userService.redirectAllUsers();
        })

        .catch((error) => {
          if (error.response.status === 406) {
            alert(error.response.data);
          } else {
            alert(`Failed!`);
          }
        });
    }

  }

  reactivateUser(userId: any) {
    if (confirm(`Reactivate this user?`)) {
      this.userService.reactivateUser(userId)
        .then(() => {
          this.collectAllUsers();
        })

        .then(() => {
          this.userService.redirectAllUsers();
        })

        .catch((error) => {
          if (error.response.status === 406) {
            alert(error.response.data);
          } else {
            alert(`Failed!`);
          }
        });
    }

  }

}
