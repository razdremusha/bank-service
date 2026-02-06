package org.example.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.example.dto.ClientDto;
import org.example.infrastructure.entities.Role;
import org.example.services.ClientService;
import org.example.services.UserWebService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

  private final ClientService clientService;
  private final UserWebService userWebService = new UserWebService();

  @Operation(summary = "Show info")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Info successfully received"),
      @ApiResponse(responseCode = "404", description = "User not found")
  })
  @PreAuthorize("hasAuthority('ROLE_ADMIN') or (hasAuthority('ROLE_CLIENT') and authentication.name.equals(#login))")
  @GetMapping(value = "/users/{login}")
  public ResponseEntity<String> showInfoByLogin(@PathVariable("login") @P("login") String login) {

    return userWebService.ShowInfoResponse(login);
  }

  @PostMapping(value = "/admin")
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public ResponseEntity createAdmin(@RequestBody ClientDto client) {
    var response = clientService.createClient(client.getLogin(), client.getPassword(),
        Role.ROLE_ADMIN);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PostMapping(value = "/client")
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public ResponseEntity createClient(@RequestBody ClientDto client) {
    clientService.createClient(client.getLogin(), client.getPassword(), Role.ROLE_CLIENT);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping(value = "/users")
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public ResponseEntity getAllUsers(@RequestParam(name = "gender", required = false) String gender,
      @RequestParam(name = "hairColour", required = false) String hairColour) {
    var response = userWebService.GetAllResponse(gender, hairColour);
    return response;
  }

  @Operation(summary = "Add friend")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Friend added"),
      @ApiResponse(responseCode = "404", description = "User not found")
  })
  @PostMapping(value = "/users/friends/{friendId}")
  @PreAuthorize("hasAuthority('ROLE_CLIENT')")
  public ResponseEntity addFriend(@PathVariable("friendId") String friendId){
    var auth = SecurityContextHolder.getContext().getAuthentication();
    return userWebService.AddFriend(auth.getName(), friendId);
  }

  @Operation(summary = "Delete friend")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Friend delete"),
      @ApiResponse(responseCode = "404", description = "User not found")
  })
  @DeleteMapping(value = "/users/friends/{friendId}")
  @PreAuthorize("hasAuthority('ROLE_CLIENT')")
  public ResponseEntity removeFriend(
      @PathVariable("friendId") String friendId){
    var auth = SecurityContextHolder.getContext().getAuthentication();
    return userWebService.RemoveFriend(auth.getName(), friendId);
  }

}
