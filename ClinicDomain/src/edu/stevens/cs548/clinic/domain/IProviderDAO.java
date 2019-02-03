package edu.stevens.cs548.clinic.domain;

import java.util.List;

import javax.persistence.EntityManager;

public interface IProviderDAO {
	public static class ProviderExn extends Exception{
		private static final long serialVersionUID = 1L;
		public ProviderExn(String msg) {
			super(msg);
		}
	}
	public Provider getProviderByProviderId(long NPI)throws ProviderExn;
	
	public List<Provider> getProviderByNameSpe(String name, String specilization);
	
	public void addProvider(Provider provider) throws ProviderExn;
	
	public void delProvider(Provider provider) throws ProviderExn;
	
	public void deleteProviders();
	
	public List<Provider> getAllProvider() ;
}
