package com.aytov.facade;

import com.aytov.domain.DomainException;
import com.aytov.facade.dto.DomainExceptionDto;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class FacadeExceptionHandler {
    @ExceptionHandler(DomainException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public DomainExceptionDto domainException(HttpServletRequest req, DomainException ex) {
        DomainExceptionDto businessExceptionDto = new DomainExceptionDto();
        businessExceptionDto.setKey(ex.getType().getLocalizationMsgKey());
        businessExceptionDto.setParameters(ex.getParameters());

        return businessExceptionDto;
    }
}