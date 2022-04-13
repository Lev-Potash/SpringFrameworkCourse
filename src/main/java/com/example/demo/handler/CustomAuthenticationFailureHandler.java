package com.example.demo.handler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Класс, который, наоборот, отвечает за неуспешную попытку входа.
 * Тут тоже просто выставляется статус 401(Unauthorized).
 *
 * @Component - говорит, что класс на который указывает данная аннотация, является бином.
 * К компоненту можно получить доступ при обнаружении его с помощью аннотации
 * @ScanComponent , которая является частью @SpringBootApplication.
 *
 * Если используется @SpringBootApplication, то @ScanComponent сканирует все бины,
 * которые находятся внутри ApplicationContext, начиная от корня.
 *
 * Если нужно считать бины из внешнего пакета, за пределами области видимости,
 * @ScanComponent объявляют явно и задают параметры:
 * @ScanComponent ("com.work.innerSideContextScopeExample", "com.work.outSideContextScopeExample"),
 * тогда мы увидим все бины указаные в параметрах сканирования.
 *
 * P.S.:
 * @Component является общим стереотипом для любого компонента, управляемого Spring.
 * @Service аннотирует классы на уровне службы.
 * @Repository аннотирует классы на уровне сохраняемости, который будет действовать как репозиторий базы данных.
 *
 */
@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("check login password");
        response.getWriter().flush();
        response.getWriter().close();
    }
}