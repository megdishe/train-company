package org.train.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.train.domain.CustomerSummary;
import org.train.domain.Trip;
import org.train.domain.ZoneStrategy;
import org.train.infra.input.InvalidInputException;
import org.train.infra.input.Tap;
import org.train.infra.input.TapsWrapper;
import org.train.infra.output.CustomerSummariesWrapper;
import org.train.infra.output.InvalidOutputException;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.groupingBy;

public class InvoicingService {
    private static volatile InvoicingService instance = null;

    private InvoicingService() {
    }

    public static InvoicingService getInstance() {
        if (instance == null) {
            synchronized (InvoicingService.class) {
                if (instance == null) {
                    instance = new InvoicingService();
                }
            }
        }
        return instance;
    }

    public Set<CustomerSummary> generateCustomerSummaries(String inputFilePath, String outputFilePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        TapsWrapper inputData;

        try {
            inputData = objectMapper.readValue(new File(inputFilePath), TapsWrapper.class);
        } catch (IOException e) {
            System.err.println("Error while trying to parse file");
            throw new InvalidInputException("Error while trying to parse file", e);
        }

        Set<CustomerSummary> customerSummaries = inputData.getTaps()
                .stream()
                .sorted(Comparator.comparing(Tap::getUnixTimestamp))
                .collect(groupingBy(Tap::getCustomerId))
                .entrySet()
                .stream()
                .map(entry -> buildCustomerSummary(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparing(CustomerSummary::getCustomerId))
                .collect(Collectors.toCollection(LinkedHashSet::new));

        CustomerSummariesWrapper outputData = new CustomerSummariesWrapper();
        outputData.setCustomerSummaries(customerSummaries);

        try {
            objectMapper.writeValue(new File(outputFilePath), outputData);
        } catch (IOException e) {
            System.err.println("Error while trying to generate output file");
            throw new InvalidOutputException("Error while trying to generate output file", e);
        }
        return customerSummaries;
    }

    private CustomerSummary buildCustomerSummary(Integer customerId, List<Tap> taps) {
        if (taps.size() % 2 != 0) {
            throw new IllegalArgumentException("Invalid taps sequence!");
        }
        Set<Trip> trips = IntStream.range(0, taps.size() - 1)
                .filter(i -> i % 2 == 0)
                .mapToObj(i -> initTrip(taps.get(i), taps.get(i + 1)))
                .filter(trip -> trip.getStationStart() != trip.getStationEnd())
                .map(ZoneStrategy::calculateTripCost)
                .sorted(Comparator.comparing(Trip::getStartedJourneyAt))
                .collect(Collectors.toCollection(LinkedHashSet::new));
        CustomerSummary customerSummary = new CustomerSummary();
        customerSummary.setCustomerId(customerId);
        customerSummary.getTrips().addAll(trips);
        Integer totalCostInCents = trips.stream()
                .map(Trip::getCostInCents)
                .reduce(0, Integer::sum);
        customerSummary.setTotalCostInCents(totalCostInCents);
        return customerSummary;
    }

    private Trip initTrip(Tap from, Tap to) {
        var trip = new Trip();
        trip.setStationStart(from.getStation());
        trip.setStationEnd(to.getStation());
        trip.setStartedJourneyAt(from.getUnixTimestamp());
        return trip;
    }

}
