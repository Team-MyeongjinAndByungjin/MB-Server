package team.mb.mbserver.global.error;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class ExceptionFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (BusinessException e) {
            sendErrorMessage(response, e.getStatus(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            sendErrorMessage(response, 500, "Server Error");
        }
    }

    private void sendErrorMessage(HttpServletResponse response, int status, String message) throws IOException {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(status)
                .message(message)
                .build();

        String errorResponseJson = objectMapper.writeValueAsString(errorResponse);

        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(errorResponseJson);
    }
}
