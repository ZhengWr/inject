package cn.bnu.edu.neo4j;

import java.util.HashMap;

import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.model.Result;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;


public class Neo4jKBModifier {
	private Session sharedSession;

	public Neo4jKBModifier(){
		Configuration config=new Configuration(); 
		config.driverConfiguration()
		      .setDriverClassName("org.neo4j.ogm.drivers.http.driver.HttpDriver")
		      .setURI("http://neo4j:zw941012@172.16.216.209:7474");
		SessionFactory sf = new SessionFactory(config);
		setSession(sf.openSession());
	}
	public void setSession(Session s){
		sharedSession=s;
	}
	
	
	public int addKnowledgeNode(String name, String isim, String isdi ) {
		String neosql = "merge(b:知识点{name:{name},isim:{isim},isdi:{isdi}})";
		HashMap<String, Object> params = new HashMap<String,Object>();
		params.put("name", name);
		params.put("isim", isim);
		params.put("isdi", isdi);
		Result result = sharedSession.query(neosql, params);
		return result.queryStatistics().getNodesCreated();
	}
	
	public int addConceptNode(String name, String condefi) {
		String neosql = "merge(b:概念{name:{name},condefi:{condefi}})";
		HashMap<String, Object> params = new HashMap<String,Object>();
		params.put("name", name);
		params.put("condefi", condefi);
		Result result = sharedSession.query(neosql, params);
		return result.queryStatistics().getNodesCreated();
	}
	
	
	public int addCharacttNode(String name, String chacontent) {
		String neosql = "merge(b:性质{name:{name},chacontent:{chacontent}})";
		HashMap<String, Object> params = new HashMap<String,Object>();
		params.put("name", name);
		params.put("chacontent", chacontent);
		Result result = sharedSession.query(neosql, params);
		return result.queryStatistics().getNodesCreated();
	}
	
	
	public int addTheoNode(String name, String content) {
		String neosql = "merge(b:定理{name:{name},content:{content}})";
		HashMap<String, Object> params = new HashMap<String,Object>();
		params.put("name", name);
		params.put("content", content);
		Result result = sharedSession.query(neosql, params);
		return result.queryStatistics().getNodesCreated();
	}
	
	
	public int addSoluNode(String name, String content) {
		String neosql = "merge(b:方法{name:{name},content:{content}})";
		HashMap<String, Object> params = new HashMap<String,Object>();
		params.put("name", name);
		params.put("content", content);
		Result result = sharedSession.query(neosql, params);
		return result.queryStatistics().getNodesCreated();
	}
	
	
	public int addChildRelation(String name1, String name2) {
		String neosql = "match (b:知识点) where b.name = {name1} "
				+"match (b2:知识点) where b2.name = {name2} "
				+"merge (b)-[r:child]->(b2)";
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("name1", name1);
		params.put("name2", name2);
		Result result = sharedSession.query(neosql,params);
		return result.queryStatistics().getRelationshipsCreated();
	}

	
	public int addFatherRelation(String name1, String name2) {
		String neosql = "match (b:知识点) where b.name = {name1} "
				+"match (b2:知识点) where b2.name = {name2} "
				+"merge (b)-[r:father]->(b2)";
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("name1", name1);
		params.put("name2", name2);
		Result result = sharedSession.query(neosql,params);
		return result.queryStatistics().getRelationshipsCreated();
	}
	
	
	public int addContainRelation(String name1, String name2) {
		String neosql = "match (b:知识点) where b.name = {name1} "
				+"match (b2:概念) where b2.name = {name2} "
				+"merge (b)-[r:contain]->(b2)";
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("name1", name1);
		params.put("name2", name2);
		Result result = sharedSession.query(neosql,params);
		return result.queryStatistics().getRelationshipsCreated();
	}
	
    public static void main(String[] args){
    	Neo4jKBModifier demo = new Neo4jKBModifier();
//		demo.addNode("try1", "lab", 0);
		String n= "cc";
		String n1= "cc";
		String n2= "dd";
		String n3= "dd";
		String source1="word1";
		String source2="word2";
    }
}
