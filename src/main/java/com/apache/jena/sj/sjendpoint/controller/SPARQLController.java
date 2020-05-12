package com.apache.jena.sj.sjendpoint.controller;

import java.util.List;

import org.apache.jena.sparql.engine.binding.Binding;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apache.jena.sj.sjendpoint.dao.JenaDAO;

@RestController
@RequestMapping("/sparql")
@CrossOrigin(origins = "http://localhost:4200")
public class SPARQLController {

	@RequestMapping(method = RequestMethod.GET)
	static public String executeQuery(@RequestParam(name = "q") String querystring){	
		return JenaDAO.executeSPARQL(querystring).toString();
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/mock/")
	static public String executeMockQuery(@RequestBody String querystring){
		System.out.println(querystring);
		List<Binding> r =  JenaDAO.executeSPARQL(querystring);
		return r.toString();
	}
	
}
