package edu.stevens.cs548.clinic.domain;

public class ProviderFactory implements IProviderFactory {

	@Override
	public Provider createProvider(long NPI, String name, String specilization) {
		Provider provider = new Provider();
		provider.setName(name);
		provider.setNPI(NPI);
		provider.setSpecilization(specilization);
		return provider;
	}

}
