package cs544.validation.controller;

import cs544.validation.entity.Car;
import cs544.validation.service.CarService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CarController {

    @Autowired
    private CarService carService;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/cars", method = RequestMethod.GET)
    public String getAll(Model model) {
        model.addAttribute("cars", carService.getAll());
        return "carList";
    }

    @RequestMapping(value = "/addCar", method = RequestMethod.GET)
    public String addCar(@ModelAttribute("car") Car car) {
        return "addCar";
    }

    @RequestMapping(value = "/addCar", method = RequestMethod.POST)
    public String add(@Valid Car car, BindingResult result, RedirectAttributes re) {
        String view = "redirect:/cars";
        if (!result.hasErrors()) {
            carService.add(car);
        } else {
            view = "addCar";
        }
        return view;
    }

    @RequestMapping(value = "/cars/{id}", method = RequestMethod.GET)
    public String get(@PathVariable int id, Model model) {
        model.addAttribute("car", carService.get(id));
        return "carDetail";
    }

    @RequestMapping(value = "/cars/{id}", method = RequestMethod.POST)
    public String update(@Valid Car car, BindingResult result, 
            @PathVariable int id) {
        if (!result.hasErrors()) {
            carService.update(id, car); // car.id already set by binding
            return "redirect:/cars";
        } else {
            return "carDetail";
        }
    }

    @RequestMapping(value = "/cars/delete", method = RequestMethod.POST)
    public String delete(int carId) {
        carService.delete(carId);
        return "redirect:/cars";
    }

}
