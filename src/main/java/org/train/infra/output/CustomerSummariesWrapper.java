package org.train.infra.output;

import org.train.domain.CustomerSummary;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

public class CustomerSummariesWrapper implements Serializable {
    private Set<CustomerSummary> customerSummaries;

    public Set<CustomerSummary> getCustomerSummaries() {
        return customerSummaries;
    }

    public void setCustomerSummaries(Set<CustomerSummary> customerSummaries) {
        this.customerSummaries = customerSummaries;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerSummariesWrapper that = (CustomerSummariesWrapper) o;
        return Objects.equals(customerSummaries, that.customerSummaries);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerSummaries);
    }

    @Override
    public String toString() {
        return "{" + "\"customerSummaries\" :" + customerSummaries + '}';
    }
}
