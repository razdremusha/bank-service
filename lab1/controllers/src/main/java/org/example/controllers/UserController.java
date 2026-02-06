package org.example.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.example.entity.Gender;
import org.example.entity.HairColour;
import org.example.entity.User;
import org.example.service.contracts.FriendshipService;
import org.example.service.contracts.LoginService;
import org.example.service.contracts.UserService;
import org.example.service.dto.UserDto;
import org.example.service.exceptions.AlreadyExistException;
import org.example.service.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(
    name = "User Controller",
    description = "Control users and friends"
)
@RestController
public class UserController {

  private final UserService userService;
  private final FriendshipService friendshipService;

  private final LoginService loginService;

  @Autowired
  public UserController(UserService userService, FriendshipService friendshipService,
      LoginService loginService) {
    this.userService = userService;
    this.friendshipService = friendshipService;
    this.loginService = loginService;
  }

  @Operation(summary = "Get user by login")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Login found"),
      @ApiResponse(responseCode = "404", description = "User not found")
  })
  @GetMapping(value = "/users/{login}")
  public ResponseEntity<UserDto> getUserByLogin(@PathVariable("login") String login) {
    Optional<UserDto> user = userService.findByLogin(login);
    if (user.isPresent()) {
      return new ResponseEntity<>(user.get(), HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @Operation(summary = "Get friends by login")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "List of friends successfully received"),
      @ApiResponse(responseCode = "404", description = "User not found")
  })
  @GetMapping(value = "/users/friends/{login}")
  public ResponseEntity<List<UserDto>> getFriendsByLogin(@PathVariable("login") String login)
      throws UserNotFoundException {
    return new ResponseEntity<>(friendshipService.getFriendsByLogin(login), HttpStatus.OK);
  }

  @Operation(summary = "GetAllUsers")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Users received")
  })
  @GetMapping(value = "/users")
  public ResponseEntity<List<UserDto>> getUsers(
      @RequestParam(name = "gender", required = false) String gender,
      @RequestParam(name = "hairColour", required = false) String hairColour) {
    var genderEnum = gender != null ? Gender.valueOf(gender) : null;
    var hairColourEnum = hairColour != null ? HairColour.valueOf(hairColour) : null;
    var users = userService.getFilteredUsers(genderEnum, hairColourEnum);
    return new ResponseEntity<>(users, HttpStatus.OK);
  }

  @Operation(summary = "Create user")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "User created"),
      @ApiResponse(responseCode = "404", description = "User not found")
  })
  @PostMapping(value = "/users")
  public ResponseEntity<UserDto> createUser(@RequestBody User user)
      throws AlreadyExistException {
    return new ResponseEntity<>(
        userService.createUser(user.getLogin(), user.getName(), user.getAge(), user.getGender(),
            user.getHairColor()), HttpStatus.CREATED);
  }

  @Operation(summary = "Show info")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Info successfully received"),
      @ApiResponse(responseCode = "404", description = "User not found")
  })
  @GetMapping(value = "/users/info/{login}")
  public ResponseEntity<String> showInfoByLogin(@PathVariable("login") String login)
      throws UserNotFoundException {
    loginService.login(login);

    return new ResponseEntity<>(userService.showInfo(), HttpStatus.OK);
  }

  @Operation(summary = "Add friend")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Friend added"),
      @ApiResponse(responseCode = "404", description = "User not found")
  })
  @PostMapping(value = "/users/friends/{accountId}/{friendId}")
  public ResponseEntity addFriend(@PathVariable("accountId") String accountId,
      @PathVariable("friendId") String friendId) throws UserNotFoundException {
    loginService.login(accountId);
    friendshipService.addFriend(friendId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @Operation(summary = "Delete friend")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Friend delete"),
      @ApiResponse(responseCode = "404", description = "User not found")
  })
  @DeleteMapping(value = "/users/friends/{accountId}/{friendId}")
  public ResponseEntity removeFriend(@PathVariable("accountId") String accountId,
      @PathVariable("friendId") String friendId)
      throws UserNotFoundException {
    loginService.login(accountId);
    friendshipService.deleteFriend(friendId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

}
