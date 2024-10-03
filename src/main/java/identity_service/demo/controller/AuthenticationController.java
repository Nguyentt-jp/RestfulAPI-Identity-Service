package identity_service.demo.controller;

import identity_service.demo.dto.request.AuthenticationRequest;
import identity_service.demo.dto.request.IntrospectRequest;
import identity_service.demo.dto.response.ApiResponse;
import identity_service.demo.dto.response.TokenResponse;
import identity_service.demo.repository.UserRepository;
import identity_service.demo.service.impl.AuthenticationServiceImpl;
import identity_service.demo.service.impl.JwtTokenServiceImpl;
import identity_service.demo.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationServiceImpl authenService;
    private final UserRepository userRepository;
    private final UserServiceImpl userService;
    private final JwtTokenServiceImpl jwtTokenService;

    @GetMapping
    public ApiResponse<Object> login(@RequestBody AuthenticationRequest authenRequest) {
       TokenResponse tokenResponse = authenService.authenticate(authenRequest);

        return
                ApiResponse.builder()
                        .success(true)
                        .result(tokenResponse)
                        .build();
    }

    @GetMapping("/intro")
    public ApiResponse<Object> intro(@RequestBody IntrospectRequest authenRequest) {
        return ApiResponse.builder()
                .success(true)
                .result( authenService.authenticateWithToken(authenRequest))
                .build();

    }
}
