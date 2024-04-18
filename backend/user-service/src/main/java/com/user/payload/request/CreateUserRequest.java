package com.user.payload.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class CreateUserRequest implements UserRequest {
	@NotBlank @NotNull
	private String id;

	@NotBlank(message = "First name should not be empty")
	@Size(min = 1, max = 40,  message = "First name should be between 1 to 40 characters")
	private String first_name;

    @NotBlank(message = "Last name should not be empty")
	@Size(min = 1, max = 40,  message = "Username should be between 1 to 40 characters")
	private String last_name;

	@NotBlank(message = "Email should not be empty")
	@Size(max = 50, message = "Email should be under 50 characters")
	@Email(message = "Invalid email")
	private String email;

    private String role;

}