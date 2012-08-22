package dbData;

import java.util.*;

public class AbilityData {
	public String name;
	public String type;
	public String icon;
	public String description;
	
	public String dmgBase;
	public String dmgRatio;
	
	public int range;
	public int cd;
	
	public static Map<String, Map<String, FieldTypes>> fields;
	
	// fill dictionary with attack fields
	// attack type -> (field name, fieldType)
	public static void init() {
		fields = new HashMap<String, Map<String, FieldTypes>>();
		
		Map<String, FieldTypes> f;
		
		f = new HashMap<String, FieldTypes>();
		f.put("angle", FieldTypes.INT);
		fields.put("AttackCone", f);
		
		f = new HashMap<String, FieldTypes>();
		f.put("goToCastPoint", FieldTypes.BOOLEAN);
		fields.put("AttackSmartcast", f);
		
		f = new HashMap<String, FieldTypes>();
		f.put("radius", FieldTypes.INT);
		fields.put("AttackPoint", f);
		
		f = new HashMap<String, FieldTypes>();
		f.put("width", FieldTypes.INT);
		f.put("penetrates", FieldTypes.INT);
		f.put("speed", FieldTypes.INT);
		fields.put("AttackSkillshot", f);
		
		f = new HashMap<String, FieldTypes>();
		f.put("width", FieldTypes.INT);
		f.put("penetrates", FieldTypes.INT);
		f.put("speed", FieldTypes.INT);
		f.put("stopAtEnemy", FieldTypes.BOOLEAN);
		fields.put("AttackDashSkillshot", f);
	}
	
	public enum FieldTypes {
		INT, NUMBER, BOOLEAN, STRING;
	}
}