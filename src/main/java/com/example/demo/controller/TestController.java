package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Users;

@Controller
public class TestController {

    @GetMapping("/userLogin")
    public String login(){
        return "login";
    }
    
	@GetMapping("/logout")
	public String logout() {
		return "hello";
	}
	
	@GetMapping("/test/hello")
	public String add() {
		return "hello";
	}
	
	@GetMapping("/test/index")
	public String index() {
		return "index";
	}
	
	//@Secured(value = {"ROLE_sale", "ROLE_manager"})
	// @PreAuthorize(value = "hasAnyAuthority('admins')")

	@GetMapping("update")
	@PostAuthorize("hasAnyAuthority('admins')")
	public String update() {
		System.out.println("update......");
		return "Hello Update";
	}
	
	@RequestMapping("getAll")
	@PreAuthorize(value = "hasAnyAuthority('admins')")
	@PostFilter(value = "filterObject.username == 'admin1'")
	public List<Users> getTestPreFilter(){
		ArrayList<Users> list = new ArrayList<>();
		 list.add(new Users(1,"admin1","6666"));
		 list.add(new Users(2,"admin2","888"));
		 System.out.println(list);
		 return list;
	}
}
