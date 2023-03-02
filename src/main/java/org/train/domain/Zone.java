package org.train.domain;

import java.util.Set;

import static org.train.domain.Zone.Station.A;
import static org.train.domain.Zone.Station.B;
import static org.train.domain.Zone.Station.C;
import static org.train.domain.Zone.Station.D;
import static org.train.domain.Zone.Station.E;
import static org.train.domain.Zone.Station.F;
import static org.train.domain.Zone.Station.G;
import static org.train.domain.Zone.Station.H;
import static org.train.domain.Zone.Station.I;

public enum Zone {
    ZONE_1(1, Set.of(A, B)),
    ZONE_2(2, Set.of(C, D, E)),
    ZONE_3(3, Set.of(C, E, F)),
    ZONE_4(4, Set.of(F, G, H, I));

    private final int zoneId;
    private final Set<Station> stations;

    Zone(int zoneId, Set<Station> stations) {
        this.zoneId = zoneId;
        this.stations = stations;
    }

    public int getZoneId() {
        return zoneId;
    }

    public Set<Station> getStations() {
        return stations;
    }

    public boolean containsStation(Station station) {
        return this.stations.contains(station);
    }


    public enum Station {
        A,
        B,
        C,
        D,
        E,
        F,
        G,
        H,
        I

    }
}
