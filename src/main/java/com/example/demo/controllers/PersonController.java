package com.example.demo.controllers;

import com.example.demo.domain.Car;
import com.example.demo.domain.Person;
import com.example.demo.services.PersonService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Controller
public class PersonController {
    

	private PersonService personService;

	@Autowired
	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}
	 


    @RequestMapping("/person")
    public String redirToList(){
        return "redirect:/person/list";
    }

    @RequestMapping({"/person/list", "/person"})
    public String listPerson(Model model,@ModelAttribute("personVO") Person personVO){
    	Map<Object,Object> personList=personService.findAll(personVO);
    	model.addAttribute("person", personList);
        return "person/list";
    }
    
    @RequestMapping({"/person/massInsert1"})
    public String massInsert1(Model model,@ModelAttribute("personVO") Person personVO){
    	
    	//mass insert start
    	long start = System.currentTimeMillis();
		for(int a=0;a<3;a++){
			List<Person> entries = new ArrayList<>();
			for(int b=0;b<100000;b++){
				
				 Person person1 = new Person(); 
				 person1.setName("test_man"+a+"_"+b);
				 person1.setAge(23);
				 person1.setGender("man");
				 person1.setId("test"+a+"_"+b);
				 person1.setOrganization("lgcns");
				 entries.add(person1);
			}
			personService.saveMultiListForSimple(entries);
		}
		long end = System.currentTimeMillis();
		System.out.println("Total saveMultiListForSimple: " + ((end - start)/1000.0) + " seconds");
		//mass insert end
		
    	Map<Object,Object> personList=personService.findAll(personVO);
    	model.addAttribute("person", personList);
        return "person/list";
    }
    
    @RequestMapping({"/person/massInsert2"})
    public String massInsert2(Model model,@ModelAttribute("personVO") Person personVO){
    	
    	//mass insert start
    	long start = System.currentTimeMillis();
		for(int a=0;a<3;a++){
			List<Person> entries = new ArrayList<>();
			for(int b=0;b<100000;b++){
				
				 Person person1 = new Person(); 
				 person1.setName("test_man"+a+"_"+b);
				 person1.setAge(23);
				 person1.setGender("man");
				 person1.setId("test"+a+"_"+b);
				 person1.setOrganization("lgcns");
				 entries.add(person1);
			}
			personService.saveMultiList(entries);
		}
		long end = System.currentTimeMillis();
		System.out.println("Total saveMultiList: " + ((end - start)/1000.0) + " seconds");
		//mass insert end
		
    	Map<Object,Object> personList=personService.findAll(personVO);
    	model.addAttribute("person", personList);
        return "person/list";
    }


    ///person/massInsert1
    @RequestMapping("/person/show/{org}/{id}")
    public String getPerson(@PathVariable String org,@PathVariable String id, Model model){
    	
        model.addAttribute("person", personService.find(org,id));
        return "person/show";
    }
    

    @RequestMapping("person/edit/{org}/{id}")
    public String edit(@PathVariable String org,@PathVariable String id, Model model){
    	 model.addAttribute("personForm", personService.find(org,id));
        return "person/personform";
    }

    @RequestMapping("/person/new")
    public String newPersont(Model model){
        model.addAttribute("personForm", new Person());
        return "person/personform";
    }

    
    
    @RequestMapping(value = "/person", method = RequestMethod.POST)
    public String saveOrUpdatePerson(@ModelAttribute("personVO") Person personVO,
			 Model model){
        personService.save(personVO);
        return "redirect:/person/show/" + personVO.getOrganization()+"/"+ personVO.getId();
    }

    @RequestMapping("/person/delete/{org}/{id}")
    public String delete(@PathVariable String org,@PathVariable String id){
    	personService.delete(org,id);
        return "redirect:/person/list";
    }
}
