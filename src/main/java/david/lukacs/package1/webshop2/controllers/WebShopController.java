package david.lukacs.package1.webshop2.controllers;

import david.lukacs.package1.webshop2.models.ShopItem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Controller
@RequestMapping("webshop")
public class WebShopController {

    // FIELDS
    private List<ShopItem> items = new ArrayList<>();

    // CONSTRUCTOR
    public WebShopController() {
        items.add(new ShopItem("Running shoes", "Nike running shoes for every day sport", 1000, 5));
        items.add(new ShopItem("Printer", "Some HP printer that will print pages", 3000, 2));
        items.add(new ShopItem("Coca cola", "0.5l standard coke", 25, 0));
        items.add(new ShopItem("Wokin", "Chicken with fried rice and WOKIN sauce", 119, 100));
        items.add(new ShopItem("T-shirt", "Blue with a corgi on a bike", 300, 1));
    }

    // CRUD - READ - ALL
    @GetMapping("index")
    public String index(Model model) {
        model.addAttribute("content", "table");
        model.addAttribute("items", items);
        return "index";
    }

    // CRUD - READ - ONLY AVAILABLE
    @GetMapping("only-available")
    public String onlyAvailable(Model model) {

        // logic
        List<ShopItem> onlyAvailable = items.stream().filter(item -> item.getQuantityOfStock() > 0).collect(Collectors.toList());

        // pass
        model.addAttribute("content", "table");
        model.addAttribute("items", onlyAvailable);
        return "index";
    }

    // CRUD - READ - CHEAPEST FIRST
    @GetMapping("cheapest-first")
    public String cheapestFirst(Model model) {

        // logic
        // https://www.codebyamir.com/blog/sort-list-of-objects-by-field-java
        List<ShopItem> cheapestFirst = items.stream().sorted(Comparator.comparing(ShopItem::getPrice)).collect(Collectors.toList());

        // pass
        model.addAttribute("content", "table");
        model.addAttribute("items", cheapestFirst);
        return "index";
    }

    // CRUD - READ - CONTAINS NIKE
    @GetMapping("contains-nike")
    public String containsNike(Model model) {

        // logic
        List<ShopItem> containsNike = items.stream()
                .filter(item -> item.getName().toLowerCase().contains("nike")
                        || item.getDescription().toLowerCase().contains("nike")
                ).collect(Collectors.toList());

        // pass
        model.addAttribute("content", "table");
        model.addAttribute("items", containsNike);
        return "index";
    }

    // CRUD - READ - AVERAGE STOCK
    @GetMapping("average-stock")
    public String averageStock(Model model) {

        // logic
        double averageStock = items.stream().mapToDouble(ShopItem::getQuantityOfStock).average().orElse(0.0);

        // pass
        model.addAttribute("content", "average");
        model.addAttribute("average", averageStock);
        return "index";
    }

    // CRUD - READ - MOST EXPENSIVE
    @GetMapping("most-expensive")
    public String mostExpensive(Model model) {

        // logic
        ShopItem mostExpensive = items.stream().max(Comparator.comparing(ShopItem::getPrice)).orElseThrow(NoSuchElementException::new);

        // pass
        model.addAttribute("content", "expensive");
        model.addAttribute("expensive", mostExpensive);
        return "index";
    }

    @GetMapping("search")
    public String search(Model model, String searchValue) {

        // logic
        List<ShopItem> result = items.stream()
                .filter(item -> item.getName().toLowerCase().contains(searchValue.toLowerCase().trim())
                        || item.getDescription().toLowerCase().contains(searchValue.toLowerCase().trim()))
                .collect(Collectors.toList());

        // pass
        model.addAttribute("content", "table");
        model.addAttribute("items", result);
        return "index";
    }

    // CUSTOM
    @GetMapping("greeting")
    @ResponseBody
    public String greeting() {
        return "Hello World!";
    }
}
