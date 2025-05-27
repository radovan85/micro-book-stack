package com.radovan.play.security;

import jakarta.inject.Inject;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;
import com.radovan.play.exceptions.ForbiddenAccessException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletionStage;

public class RoleSecuredAction extends Action<RoleSecured> {

    @Override
    public CompletionStage<Result> call(Http.Request request) {

        // 2. Bezbedno dobavljanje rola
        List<String> userRoles;
        try {
            userRoles = request.attrs().get(SecurityAttrs.ROLES);
        } catch (Exception e) {
            throw new ForbiddenAccessException("User roles not properly initialized");
        }

        // 3. Provera da li su role postavljene
        if (userRoles == null || userRoles.isEmpty()) {
            throw new ForbiddenAccessException("No roles assigned to user");
        }

        // 4. Provera traženih rola
        Set<String> requiredRoles = Set.of(configuration.value());

        boolean hasRole = userRoles.stream()
                .anyMatch(requiredRoles::contains);

        if (!hasRole) {
            String errorMsg = String.format(
                    "Role violation. Required: %s, User has: %s",
                    requiredRoles, userRoles
            );
            throw new ForbiddenAccessException(errorMsg);
        }

        return delegate.call(request);
    }
}