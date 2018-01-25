package cn.bnu.edu;

import org.neo4j.ogm.annotation.Relationship;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bnu.edu.neo4j.Neo4jKBModifier;


@Controller
@EnableAutoConfiguration
public class controller {
	
	Neo4jKBModifier neo4j = new Neo4jKBModifier();
	
	@RequestMapping(value = {"/","/index"})
	public String index(){
		return "index";
	}
	
	
	@RequestMapping(value = "/addknowledge")
	public  String addknowledge(@RequestParam("pointname") String pointname,
			@RequestParam("pointfather") String pointfather,
			@RequestParam("pointim") String pointim,
			@RequestParam("pointdi") String pointdi,
			@RequestParam("contain") String contain,
			@RequestParam("containth") String containth){
		neo4j.addKnowledgeNode(pointname, pointim, pointdi);
		neo4j.addConceptNode(contain);
		neo4j.addTheoNode(containth);
		neo4j.addFatherRelation(pointname, pointfather);
		neo4j.addContainRelation(pointname, contain);
		neo4j.addContainthRelation(pointname, containth);
		return "index";
	}
	
			
	
	@RequestMapping(value = "/addconcept")
	public  @ResponseBody String  addconcept(@RequestParam("conceptname") String conceptname,
			@RequestParam("definition") String definition,
			@RequestParam("charactor") String charactor){
		neo4j.addCharacttNode(charactor);
		neo4j.addConceptNode(conceptname, definition);
        return "概念页面";
	}
	
	
	@RequestMapping(value = "/addcharact")
	public  @ResponseBody String  addcharact(@RequestParam("chaname") String chaname,
			@RequestParam("chacontent") String chacontent){
		neo4j.addCharacttNode(chaname, chacontent);
        return "性质页面";
	}
	
	

		
	
	@RequestMapping(value = "/addtheo")
	public  @ResponseBody String  addtheo(@RequestParam("theoname") String theoname,
			@RequestParam("theocontent") String theocontent){
		neo4j.addTheoNode(theoname, theocontent);  
        return "定理";
	}
	
	

}
