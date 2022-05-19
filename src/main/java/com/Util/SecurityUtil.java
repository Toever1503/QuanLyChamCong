package com.Util;

import com.service.CustomUserDetail;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    public static Long getCurrentUserId() {
        return getCurrentUser().getStaff().getStaffId();
    }

    public static CustomUserDetail getCurrentUser() {
        return (CustomUserDetail) getCurrentAuthentication().getPrincipal();
    }

    private static Authentication getCurrentAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static boolean isAuthenticated() {
        return getCurrentAuthentication().isAuthenticated();
    }

    public static boolean hasRole(String role) {
        return getCurrentAuthentication().getAuthorities().stream().anyMatch(r -> r.getAuthority().equals(role));
    }
}
