/**
 * 
 */
package com.saintgobain.ramses.selenium.testdata;

import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.saintgobain.ramses.selenium.constants.SeleniumConstants;
import com.saintgobain.ramses.selenium.helper.ResourceHelper;
import com.saintgobain.ramses.selenium.workbook.DataHelper;

public class TestData {

	@Test
	public void testdata() {
		String testCase = "unit Test Case-#2939";
		List<HashMap<String, String>> data = DataHelper.getdata(ResourceHelper.getConfigBundleValue("excel_data_path") + testCase + SeleniumConstants.XLSX);
		System.out.println(data);
		Assert.assertNotNull(data);
	}
}
