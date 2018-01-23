package cn.bnu.edu;

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
	public  @ResponseBody String  addknowledge(@RequestParam("pointname") String pointname,
			@RequestParam("pointim") String pointim,
			@RequestParam("pointdi") String pointdi){
		neo4j.addKnowledgeNode(pointname, pointim, pointdi);
		return "";
	}
	
			
	
	@RequestMapping(value = "/addconcept")
	public  @ResponseBody String  addconcept(@RequestParam("conname") String conname,
			@RequestParam("condefi") String condefi){
		neo4j.addConceptNode(conname, condefi);
        return "概念页面";
	}
	
	
	@RequestMapping(value = "/addcharact")
	public  @ResponseBody String  addcharact(@RequestParam("chaname") String chaname,
			@RequestParam("chacontent") String chacontent){
		neo4j.addCharacttNode(chaname, chacontent);
        return "性质页面";
	}
	
	
	@RequestMapping(value = "/child")
	public  @ResponseBody String  addchild(@RequestParam("child1") String child1,
			@RequestParam("child2") String child2){
		neo4j.addChildRelation(child1, child2);
        return "定理页面";
	}
	
	
	@RequestMapping(value = "/father")
	public  @ResponseBody String  addfather(@RequestParam("father1") String father1,
			@RequestParam("father2") String father2){
		neo4j.addFatherRelation(father1, father2);
        return "父节点";
	}
	
	
	@RequestMapping(value = "/contain")
	public  @ResponseBody String  addcontain(@RequestParam("contain1") String contain1,
			@RequestParam("contain2") String contain2){
		neo4j.addContainRelation(contain1, contain2);   
        return "包含";
	}
	
	@RequestMapping(value = "/addtheo")
	public  @ResponseBody String  addtheo(@RequestParam("theoname") String theoname,
			@RequestParam("theocontent") String theocontent){
		neo4j.addTheoNode(theoname, theocontent);  
        return "包含";
	}
	
	
	@RequestMapping(value = "/addsolu")
	public  @ResponseBody String  addsolu(@RequestParam("soluname") String soluname,
			@RequestParam("solucontent") String solucontent){
		neo4j.addSoluNode(soluname, solucontent);
        return "方法页面";
	}
}
