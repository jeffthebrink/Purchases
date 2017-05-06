package com.theironyard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

@Controller
public class PurchasesController {

    @Autowired
    CustomerRepository customers;

    @Autowired
    PurchaseRepository purchases;

    @PostConstruct
    public void init() {
        if (customers.count() == 0) {
            File f = new File("customers.csv");
            try (Scanner scanner = new Scanner(f)) {
                scanner.nextLine();
                while (scanner.hasNext()) {
                    String line = scanner.nextLine();
                    String[] columns = line.split(",");
                    Customer customer = new Customer(columns[0], columns[1]);
                    customers.save(customer);
                }
            } catch (FileNotFoundException e) {
                System.out.println("File has not been found!");
            }
        }

        if (purchases.count() == 0) {
            File file = new File("purchases.csv");

            try (Scanner scanner = new Scanner((file))) {
                scanner.nextLine();
                while (scanner.hasNext()) {
                    String line = scanner.nextLine();
                    String[] columns = line.split(",");
                    Purchase purchase = new Purchase(columns[1], columns[2], columns[3], columns[4], customers.findOne(Integer.valueOf(columns[0])));
                    purchases.save(purchase);
                }
            } catch (FileNotFoundException e) {
                System.out.println("File has not been found!");
            }
        }
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(Model model, String category) {
        List<Purchase> purchaseList;
        if (category != null) {
            purchaseList = purchases.findByCategory(category);
        } else {
            purchaseList = (List<Purchase>) purchases.findAll();
        }
        model.addAttribute("purchases", purchaseList);
        return "home";
    }
}


