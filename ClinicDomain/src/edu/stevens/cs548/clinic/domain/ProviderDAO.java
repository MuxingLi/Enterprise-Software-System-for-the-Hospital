package edu.stevens.cs548.clinic.domain;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;



public class ProviderDAO implements IProviderDAO {
	
	private EntityManager em;
	
	public ProviderDAO(EntityManager em) {
		this.em = em;
	}
	@Override
	public Provider getProviderByProviderId(long NPI) throws ProviderExn {

		TypedQuery<Provider> query = em.createNamedQuery("SearchProviderByProviderId", Provider.class)
				.setParameter("npi", NPI);
		List<Provider> providers = query.getResultList();
		if(providers.size()>1)
		throw new ProviderExn("Duplicate provider records: ProviderId ="+NPI);
		else if(providers.size()<1)
		throw new ProviderExn("Provider not found: provider id = "+ NPI);
		else {
		Provider p =  providers.get(0);
		p.setTreatmentDAO(new TreatmentDAO(this.em));
		return p;
		}
	}

	@Override
	public List<Provider> getProviderByNameSpe(String name, String specilization) {
		TypedQuery<Provider> query = em.createNamedQuery("SearchProviderByNameSpe",Provider.class)	
														.setParameter("name", name)
														.setParameter("specilization", specilization);
		List<Provider> providers = query.getResultList();
		for(Provider provider:providers) {
			provider.setTreatmentDAO(new TreatmentDAO(this.em));
		}
		return providers;
	}

	@Override
	public void addProvider(Provider provider) throws ProviderExn {
		long NPI = provider.getNPI();

		TypedQuery<Provider> query = em.createNamedQuery("SearchProviderByProviderId", Provider.class)
				.setParameter("npi", NPI);
		List<Provider> providers = query.getResultList();
		if(providers.size()<1) {
			em.persist(provider);
			provider.setTreatmentDAO(new TreatmentDAO(this.em)); 
		}else {
			Provider provider2 = providers.get(0);
			throw new ProviderExn("Insertion : Provider with provider id ("+NPI+") already exists.\n** Name :"+
								provider2.getName());
		}
	}

	@Override
	public void delProvider(Provider provider) throws ProviderExn {
		em.remove(provider);
	}
	@Override
	public void deleteProviders() {                       
		TypedQuery<Provider> query = em.createNamedQuery("SearchAllProviders",Provider.class);
		List<Provider> providers = query.getResultList();
		for(int i = 0; i < providers.size(); i++) {
			em.remove(providers.get(i));
			}
	}
	@Override
	public List<Provider> getAllProvider() {
		TypedQuery<Provider> query = em.createNamedQuery("SearchAllProviders",Provider.class);
		List<Provider> providers = query.getResultList();
		for(Provider provider:providers) {
			provider.setTreatmentDAO(new TreatmentDAO(this.em));
		}
		return providers;
	}
}
