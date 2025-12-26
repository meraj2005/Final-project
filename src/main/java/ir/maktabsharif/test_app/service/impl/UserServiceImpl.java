package ir.maktabsharif.test_app.service.impl;


import ir.maktabsharif.test_app.dto.TokenResponse;
import ir.maktabsharif.test_app.dto.user.*;
import ir.maktabsharif.test_app.model.enums.Role;
import ir.maktabsharif.test_app.model.enums.Status;
import ir.maktabsharif.test_app.exceptions.BusinessException;
import ir.maktabsharif.test_app.mapper.UserMapper;
import ir.maktabsharif.test_app.model.User;
import ir.maktabsharif.test_app.repository.UserRepository;
import ir.maktabsharif.test_app.security.JwtUtil;
import ir.maktabsharif.test_app.service.UserService;
import ir.maktabsharif.test_app.specification.UserSpecification;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl extends BaseServiceImpl<User, Long> implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;


    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        super(userRepository);
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }


    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    @Override
    public TokenResponse login(UserLoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new BusinessException("Email or password is incorrect")
                );

        if (user.getStatus() != Status.APPROVED) {
            throw new BusinessException("User is not approved yet");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String token = jwtUtil.generateToken(userDetails);

        return new TokenResponse(token, user.getRole().name());
    }

    @Override
    public void register(UserRegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new BusinessException("Email already exists");
        }
        if (request.getRole() == Role.ADMIN) {
            throw new BusinessException("Cannot register with ADMIN role");
        }
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        User user = UserMapper.userRegisterToEntity(request);
        userRepository.save(user);
    }
    @Override
    public List<UserResponse> search(UserSearchFilter filter) {

        List<User> users =
                userRepository.findAll(UserSpecification.filter(filter));

        return users.stream()
                .map(UserMapper::toDTO)
                .toList();
    }

    @Override
    public UserResponse updateUser(Long userId, UpdateUserRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("User not found"));
        if (request.getEmail() != null
                && !request.getEmail().equals(user.getEmail())
                && userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new BusinessException("Email already exists");
        }
        if (request.getFirstName() != null) {
            user.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null) {
            user.setLastName(request.getLastName());
        }
        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }
        if (request.getRole() != null) {
            if (request.getRole() == Role.ADMIN) {
                throw new BusinessException("Cannot assign ADMIN role");
            }
            user.setRole(request.getRole());
        }
        if (request.getStatus() != null) {
            user.setStatus(request.getStatus());
        }
        User updatedUser = userRepository.save(user);
        return UserMapper.toDTO(updatedUser);
    }
    @Override
    public void approveUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        user.setStatus(Status.APPROVED);
        userRepository.save(user);
    }

}