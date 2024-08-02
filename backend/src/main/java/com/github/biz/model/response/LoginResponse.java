package com.github.biz.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Gudao
 * @since 2024/8/2
 */
@Setter
@Getter
@AllArgsConstructor
public class LoginResponse implements Serializable {

	@Serial
	private static final long serialVersionUID = 1910083193592516792L;

	/**
	 * token
	 */
	private String token;

}
