package com.devopsbuddy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.devopsbuddy.web.i18n.I18NService;

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DevopsbuddyApplication.class)
@WebAppConfiguration
public class DevopsbuddyApplicationTests {


	@Autowired
	private I18NService i18NService;
	
	@Test
	public void testMessageByLocaleService() throws Exception {
		String expectedResult = "Bootstrap starter template";
		String messageId = "index.main.callout";
		String actual = i18NService.getMessage(messageId);
		Assert.assertEquals("The actual and expected Strings do not match.", expectedResult, actual);
	}
}
