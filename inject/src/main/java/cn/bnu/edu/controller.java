package cn.bnu.edu;

import java.io.BufferedWriter;
import java.io.FileWriter;

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
	public  String addknowledge(@RequestParam("user") String user,
			@RequestParam("pointname") String pointname,
			@RequestParam("pointfather") String pointfather,
			@RequestParam("pointim") String pointim,
			@RequestParam("pointdi") String pointdi,
			@RequestParam("conceptname") String conceptname,
			@RequestParam("definition") String definition,
			@RequestParam("chaname") String chaname,
			@RequestParam("chacontent") String chacontent,
			@RequestParam("theoname") String theoname,
			@RequestParam("theocontent") String theocontent,
			@RequestParam("methodname") String methodname,
			@RequestParam("methodinfo") String methodinfo,
			@RequestParam("markname") String markname,
			@RequestParam("markinfo") String markinfo,
			@RequestParam("content") String content
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
		
		if(conceptname!=""&&chaname!=""){
		neo4j.addContainchRelation(conceptname, chaname);
		}
		
		if(methodname!=""&&methodinfo!=""){
			neo4j.addMethodNode(methodname, methodinfo);
			}
		
		if(pointname!=""&&methodname!=""){
			neo4j.addContainMethodRelation(pointname, methodname);
		     }

		
		if(markname!=""&&markinfo!=""){
			neo4j.addMarkNode(markname, markinfo);
			}
		
		if(pointname!=""&&markname!=""){
			neo4j.addContainMarkRelation(pointname, markname);
		     }
		
		if(pointname!=""&&pointim!=""&&pointdi!=""&&content==""){
		neo4j.addKnowledgeNode(pointname, pointim, pointdi);
		}
		
		if(pointname!=""&&pointim!=""&&pointdi!=""&&content!=""){
			neo4j.addKnowledgeNode(pointname, pointim, pointdi,content);
			}
		
		if(pointname!=""&&pointfather!=""){
		neo4j.addFatherRelation(pointname,pointfather);
		}	
		try{
			FileWriter fw = new FileWriter("/home/kde/zhengw/apache-tomcat-8.5.24/log.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.append(user+" "+pointname+" "+pointfather+" "+pointim+" "+pointdi+" "+
            conceptname+" "+definition+" "+chaname+" "+chacontent+" "+theoname+" "+
            		theocontent+"\r");
            bw.close();
            fw.close();
		}catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		System.out.println(user);
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
