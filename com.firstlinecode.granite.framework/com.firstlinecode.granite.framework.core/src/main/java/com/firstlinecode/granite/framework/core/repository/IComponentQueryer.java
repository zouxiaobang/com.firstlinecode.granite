package com.firstlinecode.granite.framework.core.repository;

public interface IComponentQueryer {
	IComponentInfo[] getServices();
	IComponentInfo getService(String serviceId);
	IComponentInfo[] getComponents();
	String[] getComponentBinding(String componentId);
}
