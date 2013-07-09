package com.itface.star.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Service;
@Service
public class BeanFactoryAwareBean implements BeanFactoryAware {

	private static DefaultListableBeanFactory beanFactory;

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		// TODO Auto-generated method stub
		this.beanFactory = (DefaultListableBeanFactory) beanFactory;
	}

	public static DefaultListableBeanFactory getDefaultListableBeanFactory() {
		return beanFactory;
	}

}
