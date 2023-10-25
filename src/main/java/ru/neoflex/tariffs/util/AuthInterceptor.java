package ru.neoflex.tariffs.util;

import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import ru.neoflex.tariffs.exceptions.TariffsException;
import ru.neoflex.tariffs.feignclient.AuthValidServiceClient;


@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {
    private final AuthValidServiceClient authValidServiceClient;
    @Value("${url}")
    private String url;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorization = request.getHeader("Authorization");
        try {
            authValidServiceClient.validateToken(authorization, url).getBody();
            return true;
        } catch (FeignException e) {
            throw new TariffsException("Error during JWT validation");
        }
    }
}
