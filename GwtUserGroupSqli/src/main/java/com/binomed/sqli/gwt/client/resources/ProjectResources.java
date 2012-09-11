package com.binomed.sqli.gwt.client.resources;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface ProjectResources extends ClientBundle {

	public static final ProjectResources instance = GWT.create(ProjectResources.class);

	// Images
	@Source("com/binomed/sqli/gwt/client/resources/images/gwt.jpg")
	ImageResource gwt();

	@Source("com/binomed/sqli/gwt/client/resources/images/Logo_sqli_group.png")
	// @ImageOptions(repeatStyle = RepeatStyle.Horizontal, height = 16, width = 16)
	ImageResource logoSqli();

}
