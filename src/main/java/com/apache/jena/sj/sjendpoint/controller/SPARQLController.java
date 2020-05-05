package com.apache.jena.sj.sjendpoint.controller;

import java.util.LinkedList;
import java.util.List;

import javax.websocket.server.PathParam;

import org.apache.jena.query.Dataset;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.Syntax;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.sparql.algebra.Algebra;
import org.apache.jena.sparql.algebra.Op;
import org.apache.jena.sparql.engine.QueryIterator;
import org.apache.jena.sparql.engine.binding.Binding;
import org.apache.jena.tdb.TDBFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sparql")
public class SPARQLController {

	private static Dataset dataset;
	private static Model model;
	
	static {
		dataset = TDBFactory.createDataset("C:\\Users\\scfer\\Documents\\Universidad\\Doctorado\\Jena\\wikidata");
		model = dataset.getDefaultModel();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{q}")
	static public List<Binding> executeQuery(@PathParam(value = "q") String querystring){
		Query q = QueryFactory.create(querystring, Syntax.syntaxSPARQL_11sj) ;
        Op op = Algebra.compile(q) ;
        op = Algebra.optimize(op) ;
        QueryIterator qIter = Algebra.exec(op, model) ;
        List<Binding> result = new LinkedList<Binding>();
    	while(qIter.hasNext()) {
    		result.add(qIter.nextBinding());
    	}
		return result;
	}
	
}
