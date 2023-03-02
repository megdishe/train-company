package org.train.infra.input;

import java.util.List;
import java.util.Objects;

public class TapsWrapper {
    private List<Tap> taps;
    public List<Tap> getTaps() {
        return taps;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TapsWrapper that = (TapsWrapper) o;
        return Objects.equals(taps, that.taps);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taps);
    }

    @Override
    public String toString() {
        return "{" +
                "\"taps\" : " + taps +
                '}';
    }
}
