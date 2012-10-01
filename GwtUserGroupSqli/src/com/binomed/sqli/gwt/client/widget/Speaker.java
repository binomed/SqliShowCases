package com.binomed.sqli.gwt.client.widget;

import com.binomed.sqli.gwt.client.utils.StringUtils;
import com.github.gwtbootstrap.client.ui.Paragraph;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class Speaker extends Composite {

	private static SpeakerUiBinder uiBinder = GWT.create(SpeakerUiBinder.class);

	interface SpeakerUiBinder extends UiBinder<Widget, Speaker> {
	}

	@UiField
	Image photoSpeaker;

	@UiField
	Paragraph detailsSpeaker;

	public Speaker(String urlImg, String detailsSpeaker) {
		initWidget(uiBinder.createAndBindUi(this));
		if (StringUtils.isNotEmpty(urlImg)) {
			photoSpeaker.setUrl(urlImg);
		}
		if (StringUtils.isNotEmpty(detailsSpeaker)) {
			this.detailsSpeaker.setText(detailsSpeaker);
		}

	}

}
