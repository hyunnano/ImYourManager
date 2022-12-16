package net.manager.iym.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;
@Service
public class Validate {
    public Map<String, String> validateHandling(Errors errors){
        Map<String, String> validResult = new HashMap<>();
        //유효성검사에 통과하지 못한 항목을 가져옴.
        for (FieldError error: errors.getFieldErrors()){
            String validKey = String.format("%s", error.getField());  // %s = 유효성검사 통과하지 못한 dto의 필드명
            validResult.put(validKey, error.getDefaultMessage());     // dto에 작성된 default 메세지
        }
        return validResult;
    }

}