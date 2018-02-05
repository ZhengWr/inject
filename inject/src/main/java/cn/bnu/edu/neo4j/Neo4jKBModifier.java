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
	
	
	public int addKnowledgeNode(String name, String isim, String isdi,String content ) {
		String neosql = "merge(b:知识点{name:{name},isim:{isim},isdi:{isdi},content:{content}})";
		HashMap<String, Object> params = new HashMap<String,Object>();
		params.put("name", name);
		params.put("isim", isim);
		params.put("isdi", isdi);
		params.put("content", content);
		Result result = sharedSession.query(neosql, params);
		return result.queryStatistics().getNodesCreated();
	}
	
	public int addMethodNode(String name, String content ) {
		String neosql = "merge(b:方法{name:{name},content:{content}})";
		HashMap<String, Object> params = new HashMap<String,Object>();
		params.put("name", name);
		params.put("content", content);
		Result result = sharedSession.query(neosql, params);
		return result.queryStatistics().getNodesCreated();
	}
	
	public int addMarkNode(String name, String content ) {
		String neosql = "merge(b:符号{name:{name},content:{content}})";
		HashMap<String, Object> params = new HashMap<String,Object>();
		params.put("name", name);
		params.put("content", content);
		Result result = sharedSession.query(neosql, params);
		return result.queryStatistics().getNodesCreated();
	}
	
	
	public int addConceptNode(String name) {
		String neosql = "merge(b:概念{name:{name}})";
		HashMap<String, Object> params = new HashMap<String,Object>();
		params.put("name", name);
		Result result = sharedSession.query(neosql, params);
		return result.queryStatistics().getNodesCreated();
	}
	
	
	public int addConceptNode(String name, String condefi) {
		String neosql = "merge (b:概念 {name:{name}}) on create set b.definition={condefi} on match set b.definition={condefi}";
		HashMap<String, Object> params = new HashMap<String,Object>();
		params.put("name", name);
		params.put("condefi", condefi);
		Result result = sharedSession.query(neosql, params);
		return result.queryStatistics().getNodesCreated();
	}
	
	
	public int addCharacttNode(String name) {
		String neosql = "merge(b:性质{name:{name}})";
		HashMap<String, Object> params = new HashMap<String,Object>();
		params.put("name", name);
		Result result = sharedSession.query(neosql, params);
		return result.queryStatistics().getNodesCreated();
	}
	
	public int addCharacttNode(String name, String chacontent) {
		String neosql = "merge (b:性质 {name:{name}}) on create set b.charactor={chacontent} on match set b.charactor={chacontent}";
		HashMap<String, Object> params = new HashMap<String,Object>();
		params.put("name", name);
		params.put("chacontent", chacontent);
		Result result = sharedSession.query(neosql, params);
		return result.queryStatistics().getNodesCreated();
	}
	
	
	public int addTheoNode(String name) {
		String neosql = "merge(b:定理{name:{name}})";
		HashMap<String, Object> params = new HashMap<String,Object>();
		params.put("name", name);;
		Result result = sharedSession.query(neosql, params);
		return result.queryStatistics().getNodesCreated();
	}
	
	public int addTheoNode(String name, String content) {
		String neosql = "merge (b:定理 {name:{name}}) on create set b.therom={content} on match set b.therom={content}";
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
	
	
	public int addContainthRelation(String name1, String name2) {
		String neosql = "match (b:知识点) where b.name = {name1} "
				+"match (b2:定理) where b2.name = {name2} "
				+"merge (b)-[r:containtheory]->(b2)";
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("name1", name1);
		params.put("name2", name2);
		Result result = sharedSession.query(neosql,params);
		return result.queryStatistics().getRelationshipsCreated();
	}
	
	public int addContainMethodRelation(String name1, String name2) {
		String neosql = "match (b:知识点) where b.name = {name1} "
				+"match (b2:方法) where b2.name = {name2} "
				+"merge (b)-[r:containmethod]->(b2)";
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("name1", name1);
		params.put("name2", name2);
		Result result = sharedSession.query(neosql,params);
		return result.queryStatistics().getRelationshipsCreated();
	}

	public int addContainMarkRelation(String name1, String name2) {
		String neosql = "match (b:知识点) where b.name = {name1} "
				+"match (b2:符号) where b2.name = {name2} "
				+"merge (b)-[r:containmark]->(b2)";
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
				+"merge (b)-[r:containconcept]->(b2)";
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("name1", name1);
		params.put("name2", name2);
		Result result = sharedSession.query(neosql,params);
		System.out.println("进入概念");
		return result.queryStatistics().getRelationshipsCreated();
	}
	
	
	public int addContainchRelation(String name1, String name2) {
		String neosql = "match (b:概念) where b.name = {name1} "
				+"match (b2:性质) where b2.name = {name2} "
				+"merge (b)-[r:containcharactor]->(b2)";
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
