package net.korikisulda.hadriangarden.test;

import net.korikisulda.hadriangarden.remote.Probe;
import net.korikisulda.hadriangarden.remote.ProbeType;
import net.korikisulda.hadriangarden.remote.User;

public class Test {
	public static void main(String[] args){
		new Test().doThings();
	}
	
	public void doThings(){
		System.out.println("Things in square brackets are things you should keep a record of.");
		
		//Use this first,then comment it out and use the next.
		//Make sure you fill in everything correctly. You need to register to get the token.
		//register("emailhere","passwordhere");
		//probeFun("emailhere","tokenhere");
		//moreProbeFun("emailftw","oxgSuaWllWIbElqMoNcX4yUMnKj2iTFGqWyU","BFXCBh1Mk4cmbDa5iPycJaaXqav9eTMQ","lorFtFQjy3gzz5kYZpA0ljWNs6hGYhnkGO09","431bac6d088244be85e385d3fbfe75b8");
	}
	
	public void register(String email,String password){
		System.out.println("Your token is [" + new User(email,password).getToken() + "].");
		System.out.println("Your account will be pending. It must be accepted before you can do fun things.");
	}
	
	public void probeFun(String email, String token){
		User user=new User(email,token,"");
		System.out.println("It seems that your account is " + (user.getStatus()?"okay":"somehow wrong. Everything else will probably fail.") + ".");
		System.out.println("Your user probe token is [" + user.requestProbeToken() + "].");
		
		System.out.println("Let's make a probe! Country is gb, Seed 'ThisIsASeed' and we're pretending to be a Raspberry Pi.");
		Probe probe=new Probe(user,"gb","ThisIsASeed",ProbeType.RASPBERRY_PI);
		if(probe.getToken()==""){
			System.out.println("It seems we have failed to make our probe.");
		}else{
			System.out.println("We've made a probe!");
			System.out.println("UUID is [" + probe.getUuid() + "]");
			System.out.println("Probe token is [" + probe.getToken() + "]");
			System.out.println("And we're done!");
		}
	}
	
	public void moreProbeFun(String email,String userToken,String userProbeToken,String probeToken, String probeUuid){
		User user=new User(email,userToken,userProbeToken);
		
		Probe probe=new Probe(user,probeUuid,probeToken);
		
		System.out.println(probe.getUrl());
	}
}
