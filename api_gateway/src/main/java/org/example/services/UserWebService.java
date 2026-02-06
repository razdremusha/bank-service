package org.example.services;


import java.util.List;
import org.example.service.dto.UserDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class UserWebService {

  final private RestClient restClient = RestClient.create();

  final private String url = "http://localhost:8080";

  public ResponseEntity GetAllResponse(String gender, String hairColour) {
    String baseUrl = url + "/users";
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl);
    if (gender != null) {
      builder.queryParam("gender", gender);
    }
    if (hairColour != null) {
      builder.queryParam("hairColour", hairColour);
    }
    String finalUrl = builder.toUriString();
    try {
      return restClient.get()
          .uri(finalUrl)
          .retrieve()
          .toEntity(new ParameterizedTypeReference<List<UserDto>>() {
          });
    } catch (HttpStatusCodeException ex) {
      return ResponseEntity
          .status(ex.getStatusCode())
          .headers(ex.getResponseHeaders())
          .body(ex.getResponseBodyAsString());
    }
  }

  public ResponseEntity ShowInfoResponse(String login) {
    String baseUrl = url + "/users/info/" + login;
    try {
      return restClient.get()
          .uri(baseUrl)
          .retrieve()
          .toEntity(String.class);
    } catch (HttpStatusCodeException ex) {
      return ResponseEntity
          .status(ex.getStatusCode())
          .headers(ex.getResponseHeaders())
          .body(ex.getResponseBodyAsString());
    }
  }

  public ResponseEntity AddFriend(String userId, String friendId) {
    String baseUrl = url + "/users/friends/{userId}/{friendId}";
    try {
      return restClient.post().uri(baseUrl, userId, friendId).retrieve()
          .toEntity(String.class);
    } catch (HttpStatusCodeException ex) {
      return ResponseEntity
          .status(ex.getStatusCode())
          .headers(ex.getResponseHeaders())
          .body(ex.getResponseBodyAsString());
    }
  }

  public ResponseEntity RemoveFriend(String userId, String friendId) {
    String baseUrl = url + "/users/friends/{userId}/{friendId}";
    try {
      return restClient.delete().uri(baseUrl, userId, friendId).retrieve()
          .toEntity(String.class);
    } catch (HttpStatusCodeException ex) {
      return ResponseEntity
          .status(ex.getStatusCode())
          .headers(ex.getResponseHeaders())
          .body(ex.getResponseBodyAsString());
    }
  }
}
