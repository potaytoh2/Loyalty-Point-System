package com.admin.service;

import java.io.IOException;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.admin.entity.Log;
import com.admin.entity.Transaction;
import com.admin.exception.UnauthorisedException;
import com.admin.utils.JWTUtils;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class ProxyService {
    private final ForwardService pointsService;
    private final UserForwardService userService;
    private final LogService logger;

    @Autowired
    public ProxyService(@Value("${user.host}") String user,
            @Value("${points.host}") String points,
            LogService logger) {
        userService = new UserForwardService(user, "/user");
        pointsService = new ForwardService(points, "/points");
        this.logger = logger;
    }

    public List<Log> getLogs() {
        return logger.getLogs();
    }

    public ResponseEntity<String> forwardPoints(HttpServletRequest request) throws IOException {
        return forwardToService(pointsService, request);
    }

    public ResponseEntity<String> forwardUsers(HttpServletRequest request) throws IOException {
        return forwardToService(userService, request);
    }

    public void approveRequest(Transaction transaction) {
        ForwardService service;
        if (transaction.getService().equals("user"))
            service = userService;
        else
            service = pointsService;

        service.approveRequest(transaction.getService(), transaction.getDetails(), transaction.getActionType());
    }

    private ResponseEntity<String> forwardToService(ForwardService service, HttpServletRequest request)
            throws IOException {
        String adminId = getAdminId(request);
        String requestURI = request.getRequestURI();

        if (!checkPermissions(adminId, requestURI, request.getMethod()))
            throw new UnauthorisedException(requestURI);

        String body = request.getReader()
                .lines()
                .reduce((first, second) -> first + '\n' + second)
                .orElse("");

        logger.logAction(new Log.Builder()
                .withAdminId(adminId)
                .withAction(requestURI)
                .withDetails(body)
                .withUserAgent(request.getHeader("User-Agent"))
                .build());

        return service.forward(request, body);
    }

    /**
     * 
     * @param request
     * @return "sub" field which uniquely identifies user
     */
    private String getAdminId(HttpServletRequest request) {
        return JWTUtils.get_sub(request.getHeader("Authorization"));
    }

    private boolean checkPermissions(String userId, String uri, String method) {
        List<String> ls = userService.getPermissions(userId);
        if (ls == null)
            throw new UnauthorisedException("USER has no permissions");

        return ls.stream()
                .reduce(false,
                        (bool, resource) -> bool || (method + ":" + uri).matches(resource),
                        (bool, anotherBool) -> bool || anotherBool);
    }
}
