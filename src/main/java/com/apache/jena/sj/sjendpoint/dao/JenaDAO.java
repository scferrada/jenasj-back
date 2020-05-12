package com.apache.jena.sj.sjendpoint.dao;

import java.util.LinkedList;
import java.util.List;

import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.Syntax;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.sparql.algebra.Algebra;
import org.apache.jena.sparql.algebra.Op;
import org.apache.jena.sparql.engine.QueryIterator;
import org.apache.jena.sparql.engine.binding.Binding;
import org.apache.jena.tdb.TDBFactory;

public class JenaDAO {

	
	private static Dataset dataset;
	private static Model model;
	
	static {
		dataset = TDBFactory.createDataset("C:\\Users\\scfer\\Documents\\Universidad\\Doctorado\\Jena\\wikidata");
		model = dataset.getDefaultModel();
	}
	
	static public List<Binding> executeSPARQL(String s){
		Query q = QueryFactory.create(s, Syntax.syntaxSPARQL_11sj) ;
        Op op = Algebra.compile(q) ;
        op = Algebra.optimize(op) ;
        QueryIterator qIter = Algebra.exec(op, model) ;
        List<Binding> result = new LinkedList<Binding>();
    	while(qIter.hasNext()) {
    		result.add(qIter.nextBinding());
    	}
    	System.out.println(result);
		return result;
	}

	public static List<Binding> getMock() {
		String s = "PREFIX ex:<http://ex.com/> "
				+ "SELECT DISTINCT ?s ?s2 ?d WHERE { { ?s ex:a ?a; ex:b ?b } "
				+ "SIMILARITY JOIN top 2 using 'manhattan' on (?a, ?b) (?a2,?b2)"
				+ " { ?s2 ex:a ?a2; ex:b ?b2 }} "
				+ "ORDER BY ?s";
        Query query = QueryFactory.create(s, Syntax.syntaxSPARQL_11sj) ;
        
        Op op = Algebra.compile(query) ;
        op = Algebra.optimize(op) ;
        
        QueryIterator qIter = Algebra.exec(op, createSimModel()) ;
        System.out.println(qIter);
        
        List<Binding> res = new LinkedList<Binding>();
        for ( ; qIter.hasNext() ; )
        {
            Binding b = qIter.nextBinding() ;
            res.add(b);
        }
        return res;
	}
	
	private static Model createSimModel(){
        Model m = ModelFactory.createDefaultModel();
        Property a = m.createProperty("http://ex.com/a");
        Property b = m.createProperty("http://ex.com/b");
        int N = 20;
        for (int i = 0; i < N; i++) {
            Resource r = m.createResource("http://ex.com/"+i);
            r.addProperty(a, ""+i, XSDDatatype.XSDdouble).addProperty(b, ""+i, XSDDatatype.XSDdouble);
        }
        return m;
    }
	
}
