package com.music.project.exception;

import com.music.project.helpers.base.response.ResponseObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class ExceptionGlobalHandler {

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ResponseObject> handlingAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        var apiResponse = new ResponseObject();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());
        return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
    }
}
