package org.example.warehouse_backend.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.warehouse_backend.entity.User;
import org.example.warehouse_backend.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class DemoUserFilter extends OncePerRequestFilter {
    private final UserRepository userRepository;
    public static final ThreadLocal<User> CURRENT = new ThreadLocal<>();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String id = request.getHeader("X-User-Id");
            if (id != null) {
                userRepository.findById(Long.parseLong(id)).ifPresent(CURRENT::set);
            }
            filterChain.doFilter(request, response);
        } finally {
            CURRENT.remove();
        }
    }
}