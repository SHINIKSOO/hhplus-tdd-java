package io.hhplus.tdd.exception;


import org.springframework.stereotype.Component;

@Component
public class ErrorException extends RuntimeException {
		
	public static final String MONEY_NOT_VALID = "충전금액을 정상적으로 넣어주세요";
	public ErrorException() {
        super(MONEY_NOT_VALID);
    }
	
}
