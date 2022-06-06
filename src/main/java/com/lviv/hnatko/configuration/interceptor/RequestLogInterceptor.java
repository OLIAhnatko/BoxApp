package com.lviv.hnatko.configuration.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class RequestLogInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(RequestLogInterceptor.class);

    private final static short MAX_VALUE_LENGTH = 200;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long t = System.currentTimeMillis();
        String reqMethod = request.getMethod();
        String reqUri = request.getRequestURI();
        if (reqUri.startsWith("/webjars") || reqUri.startsWith("/swagger")) return super.preHandle(request, response, handler);

        StringBuilder log = new StringBuilder(reqMethod + " " + reqUri);

        Map<String, List<String>> parametersMap =
                request.getParameterMap()
                       .entrySet()
                       .stream()
                       .collect(Collectors.toMap(Map.Entry::getKey, e -> Arrays.asList(e.getValue())));

        log.append(parametersMap.isEmpty() ? "; " : ": ");

        for (Map.Entry<String, List<String>> entry : parametersMap.entrySet()) {
            String valueList = entry.getValue().stream().map(String::valueOf).collect(Collectors.joining(","));
            log.append(entry.getKey()).append("=")
               .append(valueList.length() > MAX_VALUE_LENGTH
                               ? valueList.substring(0, MAX_VALUE_LENGTH - 3).concat("...")
                               : valueList).append("; ");
        }

        if (reqMethod.equalsIgnoreCase(RequestMethod.POST.name()) || reqMethod.equalsIgnoreCase(RequestMethod.PUT.name())) {
            //String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            log.append("body: ").append("no body - getReader() is commented").append("; ");
        }

        String referer = Optional.ofNullable(request.getHeader("referer")).map(s -> "referrer: " + s).orElse("");
        log.append(referer).append("; log time: ").append(System.currentTimeMillis() - t).append("ms");

        logger.debug(log.toString()); //duplicate, test and then delete this
        logger.info(log.toString());
        return super.preHandle(request, response, handler);
    }
}
