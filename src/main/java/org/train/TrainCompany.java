package org.train;

import org.train.application.InvoicingService;
import org.train.domain.CustomerSummary;

import java.io.IOException;
import java.util.Set;

public class TrainCompany {
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            throw  new IllegalArgumentException("Input file path and output file path are mandatory");
        }

        InvoicingService invoicingService = InvoicingService.getInstance();
        Set<CustomerSummary> customerSummaries = invoicingService.generateCustomerSummaries(args[0], args[1]);

        System.out.println(customerSummaries);
    }

}