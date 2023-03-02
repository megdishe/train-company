package org.train.domain;

import java.util.*;

public class Trip {
    private Zone.Station stationStart;
    private Zone.Station stationEnd;
    private long startedJourneyAt;
    private int costInCents;
    private int zoneFrom;
    private int zoneTo;

    public Trip() {
    }

    public Zone.Station getStationStart() {
        return stationStart;
    }

    public void setStationStart(Zone.Station stationStart) {
        this.stationStart = stationStart;
    }

    public Zone.Station getStationEnd() {
        return stationEnd;
    }

    public void setStationEnd(Zone.Station stationEnd) {
        this.stationEnd = stationEnd;
    }

    public long getStartedJourneyAt() {
        return startedJourneyAt;
    }

    public void setStartedJourneyAt(long startedJourneyAt) {
        this.startedJourneyAt = startedJourneyAt;
    }

    public int getCostInCents() {
        return costInCents;
    }

    public void setCostInCents(int costInCents) {
        this.costInCents = costInCents;
    }

    public int getZoneFrom() {
        return zoneFrom;
    }

    public void setZoneFrom(int zoneFrom) {
        this.zoneFrom = zoneFrom;
    }

    public int getZoneTo() {
        return zoneTo;
    }

    public void setZoneTo(int zoneTo) {
        this.zoneTo = zoneTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trip that = (Trip) o;
        return startedJourneyAt == that.startedJourneyAt && costInCents == that.costInCents && zoneFrom == that.zoneFrom && zoneTo == that.zoneTo && Objects.equals(stationStart, that.stationStart) && Objects.equals(stationEnd, that.stationEnd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stationStart, stationEnd, startedJourneyAt, costInCents, zoneFrom, zoneTo);
    }

    @Override
    public String toString() {
        return "{"
                + "                        \"stationStart\":\"" + stationStart + "\""
                + ",                         \"stationEnd\":\"" + stationEnd + "\""
                + ",                         \"startedJourneyAt\":\"" + startedJourneyAt + "\""
                + ",                         \"costInCents\":\"" + costInCents + "\""
                + ",                         \"zoneFrom\":\"" + zoneFrom + "\""
                + ",                         \"zoneTo\":\"" + zoneTo + "\""
                + "}";
    }

}