package org.train.domain.exception;

import org.train.domain.Zone;

public class UnsupportedStationCombinationException extends RuntimeException {
    public UnsupportedStationCombinationException(Zone.Station from, Zone.Station to) {
        super("Unsupported station combination: " + from + " to " + to);
    }
}
