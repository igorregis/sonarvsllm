
		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//td[@id='cke_contents__15__15_structure_el_TextAreaField_content']/iframe")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.selectFrame(
			"//td[@id='cke_contents__15__15_structure_el_TextAreaField_content']/iframe");
		selenium.type("//body",
			RuntimeVariables.replace("WCD Web Content Content"));
		selenium.selectFrame("relative=top");
		selenium.clickAt("//a[@id='_15_abstractLink']",
			RuntimeVariables.replace("Abstract"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}
