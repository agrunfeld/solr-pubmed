package ca.bcgsc.uima.annotators.seth;

public class TempAnnotation {
	private int begin;
	private int end;
	private String seqType;
	private String pattern;
	private String HGVS;
	private String desc;
	
	public TempAnnotation(int starting,int ending, String coveredHGVS, String Type , String description, String regexPattern)
	{
		begin = starting; 
		end = ending;
		HGVS = coveredHGVS;
		seqType = Type;
		desc = description;
		pattern = regexPattern;	
	}

	public TempAnnotation()
	{
		begin = 0; 
		end = 0;
		HGVS = "";
		seqType = "";
		desc = "";	
		pattern = "";
	}

	public String toString(){
		return"";
	}
	
	public int getBegin() {
		return begin;
	}

	public void setBegin(int begin) {
		this.begin = begin;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public String getSeqType() {
		return seqType;
	}

	public void setSeqType(String seqType) {
		this.seqType = seqType;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getHGVS() {
		return HGVS;
	}

	public void setHGVS(String hGVS) {
		HGVS = hGVS;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}


	





	
}
