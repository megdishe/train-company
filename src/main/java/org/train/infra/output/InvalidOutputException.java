package org.train.infra.output;

import java.io.IOException;

public class InvalidOutputException extends RuntimeException {
    public InvalidOutputException(String msg, IOException e) {
        super(msg, e);
    }
}
