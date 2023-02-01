package com.quest.global.SmartHome.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "House not found!")
public class HouseNotFoundRestException extends Exception {
}
