package com.example.demo.controllers;

import com.example.demo.domain.Car;
import com.example.demo.domain.Cart;
import com.example.demo.services.CarService;
import com.example.demo.services.CartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@Controller
public class CarController {
    

	private CarService carService;

	@Autowired
	public void setCartService(CarService carService) {
		this.carService = carService;
	}
	 

    @RequestMapping("/car")
    public String redirToList(){
        return "redirect:/car/list";
    }

    @RequestMapping({"/car/list", "/Car"})
    public String listCar(Model model){
    	Map<Object,Object> carList=carService.findAll();
    	model.addAttribute("car", carList);
        return "car/list";
    }
    
    @RequestMapping({"/car/search"})
    public String searchCarList(Model model,@ModelAttribute("carVO") Car carVO){
    	List<Car> carList1=carService.findSearchAll(carVO);
    	model.addAttribute("carSearch", carList1);
        return "car/list";
    }

    @RequestMapping("/car/show/{id}")
    public String getCar(@PathVariable String id, Model model){
    	
        model.addAttribute("car", carService.find(id));
        return "car/show";
    }
    

    @RequestMapping("car/edit/{id}")
    public String edit(@PathVariable String id, Model model){
    	 model.addAttribute("carForm", carService.find(id));
        return "car/carform";
    }

    @RequestMapping("/car/new")
    public String newCar(Model model){
        model.addAttribute("carForm", new Car());
        return "car/carform";
    }

    @RequestMapping(value = "/car", method = RequestMethod.POST)
    public String saveOrUpdatePerson(@ModelAttribute("carVO") Car carVO,
			 Model model){

        int chk=carService.save(carVO);
        System.out.println("=====chk 0 이면 등록 이고 1이면 중복이니까 목록으로:"+chk);

        return "redirect:/car/show/" + carVO.getId();
    }

    @RequestMapping("/car/delete/{id}")
    public String delete(@PathVariable String id){
    	carService.delete(id);
        return "redirect:/car/list";
    }
}
