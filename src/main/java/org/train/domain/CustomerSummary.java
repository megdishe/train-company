package org.train.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class CustomerSummary implements Serializable {
    private int customerId;
    private int totalCostInCents;
    private Set<Trip> trips;

    public CustomerSummary() {
        this.trips = new HashSet<>();
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getTotalCostInCents() {
        return totalCostInCents;
    }

    public void setTotalCostInCents(int totalCostInCents) {
        this.totalCostInCents = totalCostInCents;
    }

    public Set<Trip> getTrips() {
        return trips;
    }

    public void setTrips(Set<Trip> trips) {
        this.trips = trips;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerSummary that = (CustomerSummary) o;
        return customerId == that.customerId && totalCostInCents == that.totalCostInCents && Objects.equals(trips, that.trips);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, totalCostInCents, trips);
    }

    @Override
    public String toString() {
        return "{"
                + "                        \"customerId\":\"" + customerId + "\""
                + ",                         \"totalCostInCents\":\"" + totalCostInCents + "\""
                + ",                         \"trips\":" + trips
                + "}";
    }
}
