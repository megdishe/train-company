package org.train.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.train.domain.CustomerSummary;
import org.train.domain.Trip;
import org.train.domain.Zone;
import org.train.infra.input.InvalidInputException;
import org.train.infra.output.CustomerSummariesWrapper;
import org.train.infra.output.InvalidOutputException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

class InvoicingServiceTest {
    private final InvoicingService invoicingService = InvoicingService.getInstance();
    private final Path validInputPath = Paths.get("src", "test", "resources", "simpleInput.json");
    private final Path invalidInputPath = Paths.get("missingFile.json");
    private final Path invalidJsonInputPath = Paths.get("src", "test", "resources", "invalidJson.txt");
    private final Path invalidOutputPath = Paths.get("/invalidDir/output.json");

    InvoicingServiceTest() {
    }

    @ParameterizedTest
    @CsvFileSource(
            resources = {"/input-output-pairs.csv"},
            numLinesToSkip = 1
    )
    public void should_generate_customer_summaries_for_all_given_inputs(String inputFilePath, String outputFilePath, String expectedOutputFilePath) throws
            IOException {
        Set<CustomerSummary> customerSummaries = this.invoicingService.generateCustomerSummaries(inputFilePath, outputFilePath);
        ObjectMapper objectMapper = new ObjectMapper();
        CustomerSummariesWrapper expectedOutputData = (CustomerSummariesWrapper) objectMapper.readValue(new File(expectedOutputFilePath), CustomerSummariesWrapper.class);
        CustomerSummariesWrapper actual = new CustomerSummariesWrapper();
        actual.setCustomerSummaries(customerSummaries);
        Assertions.assertEquals(expectedOutputData, actual);
    }

    @Test
    public void should_generate_customer_summaries_for_simple_case() throws IOException {
        CustomerSummary expectedCustomerSummary = new CustomerSummary();
        expectedCustomerSummary.setCustomerId(1);
        Trip firstTrip = new Trip();
        firstTrip.setStationStart(Zone.Station.A);
        firstTrip.setStationEnd(Zone.Station.D);
        firstTrip.setStartedJourneyAt(1572242400L);
        firstTrip.setCostInCents(240);
        firstTrip.setZoneFrom(1);
        firstTrip.setZoneTo(2);
        Trip secondTrip = new Trip();
        secondTrip.setStationStart(Zone.Station.D);
        secondTrip.setStationEnd(Zone.Station.A);
        secondTrip.setStartedJourneyAt(1572282000L);
        secondTrip.setCostInCents(240);
        secondTrip.setZoneFrom(2);
        secondTrip.setZoneTo(1);
        expectedCustomerSummary.getTrips().add(firstTrip);
        expectedCustomerSummary.getTrips().add(secondTrip);
        expectedCustomerSummary.setTotalCostInCents(480);
        Set<CustomerSummary> customerSummaries = this.invoicingService.generateCustomerSummaries("src/test/resources/simpleInput.json", "src/test/resources/simpleOutputTemp.json");
        customerSummaries.forEach((actualCustomerSummary) -> {
            Assertions.assertEquals(expectedCustomerSummary, actualCustomerSummary);
            Assertions.assertEquals(1, customerSummaries.size());
        });
        Files.delete(Path.of("src/test/resources/simpleOutputTemp.json"));
    }

    @Test
    public void testGenerateCustomerSummaries_InvalidInputFile() {
        Assertions.assertThrows(InvalidInputException.class, () -> invoicingService.generateCustomerSummaries(invalidInputPath.toString(), "output.json"));
    }

    @Test
    public void testGenerateCustomerSummaries_InvalidJson() {
        Assertions.assertThrows(InvalidInputException.class, () -> invoicingService.generateCustomerSummaries(invalidJsonInputPath.toString(), "output.json"));
    }

    @Test
    public void testGenerateCustomerSummaries_InvalidOutputFile() {
        Assertions.assertThrows(InvalidOutputException.class, () -> invoicingService.generateCustomerSummaries(validInputPath.toString(), invalidOutputPath.toString()));
    }

}