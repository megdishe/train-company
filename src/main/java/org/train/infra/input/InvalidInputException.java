package org.train.infra.input;

import java.io.IOException;

public class InvalidInputException extends RuntimeException {
    public InvalidInputException(String msg, IOException e) {
        super(msg, e);
    }
}
