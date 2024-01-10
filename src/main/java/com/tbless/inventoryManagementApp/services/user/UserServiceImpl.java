package com.tbless.inventoryManagementApp.services.user;

import com.tbless.inventoryManagementApp.data.models.User;
import com.tbless.inventoryManagementApp.data.repository.UserRepository;
import com.tbless.inventoryManagementApp.dtos.request.UpdateUserPasswordRequest;
import com.tbless.inventoryManagementApp.dtos.request.UserImageUpdateRequest;
import com.tbless.inventoryManagementApp.dtos.response.UpdateUserPasswordResponse;
import com.tbless.inventoryManagementApp.dtos.response.UserResponse;
import com.tbless.inventoryManagementApp.exceptions.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.tbless.inventoryManagementApp.utils.ResponseUtils.THIS_USER_NOT_FOUND;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;


//    private final UserService userService;
//    private final JwtService jwtService;
//    public final UserDetailsService userDetailsService;
//    private final PasswordEncoder passwordEncoder;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findUserByEmailAddress(String emailAddress) {
        return userRepository.findUserByEmailAddressIgnoreCase(emailAddress).orElse(null);
    }

    @Override
    public UserResponse findUserByEmailAddressProfile(String emailAddress) {
        User foundUser = userRepository.findUserByEmailAddressIgnoreCase(emailAddress).orElse(null);
        return buildUserResponse(foundUser);
    }

    @Override
    public long count() {
        return userRepository.count();
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserServiceImpl::buildUserResponse)
                .toList();
    }

    @Override
    public String updateUserProfilePicture(String emailAddress, UserImageUpdateRequest userUpdateRequest) throws UserNotFoundException {
        User user = userRepository.findUserByEmailAddressIgnoreCase(emailAddress)
                .orElseThrow(() -> new UserNotFoundException(THIS_USER_NOT_FOUND));
       String imageUrl = userUpdateRequest.getImageUrl();
        user.setImageUrl(imageUrl);
        userRepository.save(user);
        return userUpdateRequest.getImageUrl();
    }

    @Override
    public UpdateUserPasswordResponse updateUserAccount(UpdateUserPasswordRequest request) {
//        Optional<User> userFound = userRepository.findUserByEmailAddressIgnoreCase(request.getEmail());
//        boolean isValid = userFound.isPresent() && userFound.get().
//                getPassword().equals(request.getOldPassword());
//        if(isValid){
//            userFound.get().setLoggedIn(true);
//
//            userFound.get().setEmail(request.getEmail());
////            userFound.get().setUserPassword(request.getNewPassword());
//            validateUpdatedPassword(request, userFound);
//            usersRepo.save(userFound.get());
//            UpdateUserPasswordResponse response = new UpdateUserPasswordResponse();
//            response.setEmail(userFound.get().getEmail());
//            response.setNewPassword(userFound.get().getUserPassword());
//            return response;
//        }
//        throw new InvalidUserException(request.getEmail() + " not found");
        return null;
    }

    private static UserResponse buildUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .emailAddress(user.getEmailAddress())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .imageUrl(user.getImageUrl())
                .userRoles(user.getUserRoles())
                .build();
    }

    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }




//    public ApiResponse save(User user) throws UserRegistrationException {
//        boolean isRegisteredUser = userRepository.findUserByEmailAddressIgnoreCase(user.getEmailAddress()).isPresent();
//        if (isRegisteredUser) {
//            throw new UserRegistrationException("User Already exist");
//        } else {
//            User savedUser = buildUser(user);
//            System.out.println(savedUser.toString());
//            UserDetails userDetails = userDetailsService.loadUserByUsername(savedUser.getEmailAddress());
//            String jwt = jwtService.generateToken(userDetails);
//            return GenerateApiResponse.createdResponse("Bearer " + jwt);
//        }
//    }
//
//    @Override
//    public User findUserByEmailAddress(String emailAddress) {
//        return userRepository.findUserByEmailAddressIgnoreCase(emailAddress).orElse(null);
//    }
//
//    @Override
//    public User findUserByEmailIgnoreCase(String emailAddress) throws UserNotFoundException {
//        Optional<User> foundUser = userRepository.findUserByEmailAddressIgnoreCase(emailAddress);
//        User user = foundUser.orElseThrow(() -> new UserNotFoundException(
//                String.format(USER_WITH_USERNAME_NOT_FOUND, emailAddress)
//        ));
//        User userResponse = buildUserResponse(user);
//        return userResponse;
//    }
//
//    @Override
//    public void deleteAll() {
//        userRepository.deleteAll();
//
//    }
//
//    private static User buildUserResponse(User user) {
//        return User.builder()
//                .emailAddress(user.getEmailAddress())
//                .phoneNumber(user.getPhoneNumber())
//                .userRoles(user.getUserRoles())
//                .firstName(user.getFirstName())
//                .lastName(user.getLastName())
//                .genderType(user.getGenderType())
//                .build();
//    }
//
//    private User buildUser(RegistrationRequest registrationRequest) {
//        Set<Roles> userRoles = new HashSet<>();
//        userRoles.add(Roles.USER);
//        User user = User.builder()
//                .emailAddress(registrationRequest.getEmailAddress())
//                .firstName(registrationRequest.getFirstName())
//                .lastName(registrationRequest.getLastName())
//                .password(passwordEncoder.encode(registrationRequest.getPassword()))
//                .genderType(GenderType.valueOf(registrationRequest.getGender()))
//                .userRoles(userRoles)
//                .build();
//        return userRepository.save(user);
//    }
//
//    private User checkIfUserAlreadyExist(RegistrationRequest userRegistrationRequest) throws UserAlreadyExistsException {
//        if (userExist(userRegistrationRequest.getEmailAddress()))
//            throw new UserAlreadyExistsException(String.format(USER_ALREADY_EXISTS, userRegistrationRequest.getEmailAddress()));
//        User user = new User();
//        return user;
//    }
//
//    private boolean userExist(String emailAddress) {
//        Optional<User> foundUser = userRepository.findUserByEmailAddressIgnoreCase(emailAddress);
//        return foundUser.isPresent();
//    }

}