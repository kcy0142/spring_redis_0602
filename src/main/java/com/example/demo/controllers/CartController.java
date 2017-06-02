package com.example.demo.controllers;

import com.example.demo.domain.Cart;
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
public class CartController {
    

	private CartService cartService;

	@Autowired
	public void setCartService(CartService cartService) {
		this.cartService = cartService;
	}
	 


    @RequestMapping("/cart")
    public String redirToList(){
        return "redirect:/cart/list";
    }

    @RequestMapping({"/cart/list", "/cart"})
    public String listCart(Model model){
    	Map<Object,Object> cartList=cartService.findAll();
    	
    	List<Cart> cartLatestList=cartService.findLatestAll();
    	
    	model.addAttribute("cart", cartList);
    	model.addAttribute("cartLatestList", cartLatestList);
        
        return "cart/list";
    }

    @RequestMapping("/cart/show/{id}")
    public String getCart(@PathVariable String id, Model model){
    	
        model.addAttribute("cart", cartService.find(id));
        return "cart/show";
    }
    

    @RequestMapping("cart/edit/{id}")
    public String edit(@PathVariable String id, Model model){
    	 model.addAttribute("cartForm", cartService.find(id));
        return "cart/cartform";
    }

    @RequestMapping("/cart/new")
    public String newCart(Model model){
        model.addAttribute("cartForm", new Cart());
        return "cart/cartform";
    }

    @RequestMapping(value = "/cart", method = RequestMethod.POST)
    public String saveOrUpdateCart(@ModelAttribute("cartVO") Cart cartVO,
			 Model model){

        cartService.save(cartVO);

        return "redirect:/cart/show/" + cartVO.getUserId();
    }

    @RequestMapping("/cart/delete/{id}")
    public String delete(@PathVariable String id){
    	cartService.delete(id);
        return "redirect:/cart/list";
    }
}
