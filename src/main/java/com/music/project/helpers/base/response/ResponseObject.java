package com.music.project.helpers.base.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseObject<T> {
    @Builder.Default
    int code = 200;

    String message;
    T result;
	public static Object builder() {
		// TODO Auto-generated method stub
		return null;
	}
}
