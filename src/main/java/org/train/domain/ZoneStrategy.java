package org.train.domain;

import org.train.domain.exception.StationOutOfFromZoneSetException;
import org.train.domain.exception.StationOutOfToZoneSetException;
import org.train.domain.exception.UnsupportedStationCombinationException;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;

import static org.train.domain.Zone.*;

public enum ZoneStrategy {

    ZONE_1_TO_2(Set.of(ZONE_1), Set.of(ZONE_2), 240),
    ZONE_1_TO_1(Set.of(ZONE_1), Set.of(ZONE_1), 240),
    ZONE_2_TO_2(Set.of(ZONE_2), Set.of(ZONE_2), 240),
    ZONE_3_TO_3(Set.of(ZONE_3), Set.of(ZONE_3), 200),
    ZONE_4_TO_4(Set.of(ZONE_4), Set.of(ZONE_4), 200),
    ZONE_2_TO_1(Set.of(ZONE_2), Set.of(ZONE_1), 240),
    ZONE_3_TO_4(Set.of(ZONE_3), Set.of(ZONE_4), 200),
    ZONE_3_TO_1_2(Set.of(ZONE_3), Set.of(ZONE_1, ZONE_2), 280),
    ZONE_4_TO_1_2(Set.of(ZONE_4), Set.of(ZONE_1, ZONE_2), 300),
    ZONE_4_TO_3(Set.of(ZONE_4), Set.of(ZONE_3), 200),
    ZONE_1_2_TO_3(Set.of(ZONE_1, ZONE_2), Set.of(ZONE_3), 280),
    ZONE_1_2_TO_4(Set.of(ZONE_1, ZONE_2), Set.of(ZONE_4), 300);

    private final Set<Zone> from;
    private final Set<Zone> to;
    private final int price;

    ZoneStrategy(Set<Zone> from, Set<Zone> to, int price) {
        this.from = from;
        this.to = to;
        this.price = price;
    }

    public static Trip calculateTripCost(Trip trip) {
        return Arrays.stream(ZoneStrategy.values())
                .filter(zoneStrategy -> zoneStrategy.from.stream()
                        .anyMatch(zone -> zone.containsStation(trip.getStationStart()))
                        && zoneStrategy.to.stream().anyMatch(zone -> zone.containsStation(trip.getStationEnd())))
                .min(Comparator.comparing(ZoneStrategy::getPrice))
                .map(zoneStrategy -> {
                    trip.setCostInCents(zoneStrategy.getPrice());
                    trip.setZoneFrom(zoneStrategy.getZoneFrom(trip.getStationStart()));
                    trip.setZoneTo(zoneStrategy.getZoneTo(trip.getStationEnd()));
                    return trip;
                })
                .orElseThrow(() -> new UnsupportedStationCombinationException(trip.getStationStart(), trip.getStationEnd()));
    }

    public int getZoneFrom(Zone.Station station) {
        return from.stream()
                .filter(zone -> zone.containsStation(station))
                .map(Zone::getZoneId)
                .findFirst()
                .orElseThrow(StationOutOfFromZoneSetException::new);
    }

    public int getZoneTo(Zone.Station station) {
        return to.stream()
                .filter(zone -> zone.containsStation(station))
                .map(Zone::getZoneId)
                .findFirst()
                .orElseThrow(StationOutOfToZoneSetException::new);
    }

    public int getPrice() {
        return price;
    }

}








