import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { inject } from '@angular/core';

export var userGuard: CanActivateFn = (route, state) => {
  
  var authService = inject(AuthService);
  var router = inject(Router);
  var returnValue = false;

  if (authService.isUser()) {
    returnValue = true;
  } else {
    router.navigate([`home`]);
  }

  return returnValue;
};
