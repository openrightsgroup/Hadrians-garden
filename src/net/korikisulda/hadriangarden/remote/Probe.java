package net.korikisulda.hadriangarden.remote;

import net.korikisulda.hadriangarden.http.ConvenientGet;
import net.korikisulda.hadriangarden.http.ConvenientPost;

import org.json.JSONObject;
/**
 * Represents a probe.
 * You'll need a User to initialise this.
 */
public class Probe {
	private String uuid;
	private String token;
	private User user;
	
	public Probe(User userFor,String uuid, String probeSecret){
		this.uuid=uuid;
		this.token=probeSecret;
		this.user=userFor;
	}
	
	public Probe(User userFor,String seed,String country,ProbeType probeType){
		this(userFor,"","");
		token=registerProbe(userFor.getEmail(),country,seed,probeType);
	}
	
	private String registerProbe(final String email,final String country,final String seed, final ProbeType probeType){
		final Probe probe=this;
		
		ConvenientPost post=new ConvenientPost(){{
			String probeUuid=md5sum(seed + "-" + user.getProbeToken());
			setUrl("http://korikisulda.net/api/1.2/register/probe");
			add("email",email);
			add("probe_seed",seed);
			add("probe_type",probeType.getName());
			add("cc",country);
			add("signature",sign(probeUuid,user.getToken()));
			add("probe_uuid",probeUuid);
			
			probe.setUuid(probeUuid);
		}};
		
		if(!post.execute()) return null;
		System.out.println(post.getResult());
        JSONObject json = post.getResultAsJson();

        if(json.getBoolean("success")) return json.getString("secret");
        else return null;
	}
	
	private void setUuid(String uuid){
		this.uuid=uuid;
	}
	
	public String getUuid(){
		return uuid;
	}
	
	public String getToken(){
		return token;
	}
	
	/**
	 * Gets a URL the backend wants us to take a look at
	 * @return URL returned by backend
	 */
	public String getUrl(){
		ConvenientGet get=new ConvenientGet(){{
			setUrl("http://korikisulda.net/api/1.2/request/httpt");
			add("signature",sign(getUuid(),getToken()));
			add("probe_uuid",getUuid());
		}};
		
		if(!get.execute()) return null;
		
		JSONObject json=get.getResultAsJson();

        if(json.getBoolean("success"))
            return json.getString("url");
        else return null;
	}
}
