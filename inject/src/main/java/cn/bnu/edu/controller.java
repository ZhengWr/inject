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
			@RequestParam("conceptname") String conceptname,
			@RequestParam("definition") String definition,
			@RequestParam("chaname") String chaname,
			@RequestParam("chacontent") String chacontent,
			@RequestParam("theoname") String theoname,
			@RequestParam("theocontent") String theocontent
			){
		if(conceptname!=""&&definition!=""){
		   neo4j.addConceptNode(conceptname, definition);
		}
		if(theoname!=""&&theocontent!=""){
		   neo4j.addTheoNode(theoname, theocontent);
		}
		
		if(conceptname!=""&&theoname!=""){
		neo4j.addContainthRelation(conceptname, theoname);
		}
		
		if(pointname!=""&&conceptname!=""){
		neo4j.addContainRelation(pointname, conceptname);
	     }
		
		if(chaname!=""&&chacontent!=""){
		neo4j.addCharacttNode(chaname, chacontent);
		}
		
		if(pointname!=""&&chaname!=""){
		neo4j.addContainchRelation(pointname, chaname);
		}
		
		if(pointname!=""&&pointim!=""&&pointdi!=""){
		neo4j.addKnowledgeNode(pointname, pointim, pointdi);
		}
		
		if(pointname!=""&&pointfather!=""){
		neo4j.addFatherRelation(pointname,pointfather);
		}
        System.out.println(pointname+pointfather+pointim+pointdi+conceptname+definition+chaname+chacontent+theoname+theocontent);
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
