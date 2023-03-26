package service;

import model.Customer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CustomerService {
    private final Map<String, Customer> customers = new HashMap<>();
    private static CustomerService instance = null;

    private CustomerService() {
    }

    public static CustomerService getInstance() {
        if (CustomerService.instance == null) {
            CustomerService.instance = new CustomerService();
        }

        return CustomerService.instance;
    }

    public void addCustomer(Customer customer) {
        this.customers.put(customer.getEmail(), customer);
    }

    public Customer getCustomer(String customerEmail){
        return this.customers.get(customerEmail);
    }

    public Collection<Customer> getAllCustomers(){
        return this.customers.values();
    }
}
