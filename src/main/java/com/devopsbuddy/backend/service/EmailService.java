package com.devopsbuddy.backend.service;

import org.springframework.mail.SimpleMailMessage;

import com.devopsbuddy.web.domain.frontend.FeedbackPojo;

public interface EmailService {

	/**
	 * Sends an email with the content in the Feedback Pojo.
	 * @param feedback The Feedback Pojo
	 */
	public void sendFeedbackEmail(FeedbackPojo feedback);
	
	/**
	 * Sends an email with the content of the Simple Mail Message object
	 * @param message The object containing the email content
	 */
	public void sendGenericEmailMessage(SimpleMailMessage message);
}
