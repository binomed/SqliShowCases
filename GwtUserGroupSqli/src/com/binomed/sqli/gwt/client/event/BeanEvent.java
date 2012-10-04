package com.binomed.sqli.gwt.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @author jfgarreau
 * 
 *         Generic evnt for management with a bean
 * 
 * @param <Bean>
 * @param <BeanHandler>
 */
public abstract class BeanEvent<Bean, BeanHandler extends EventHandler> extends GwtEvent<BeanHandler> {

	private Bean bean;

	private Throwable exception;

	public BeanEvent(Bean bean) {
		this.bean = bean;
	}

	public BeanEvent(Throwable exception) {
		this.exception = exception;
	}

	protected Bean getBean() {
		return bean;
	}

	protected Throwable getException() {
		return exception;
	}

}
