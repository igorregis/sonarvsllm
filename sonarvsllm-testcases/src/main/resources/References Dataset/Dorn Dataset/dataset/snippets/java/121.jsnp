									"//div[@class='lfr-component lfr-menu-list']/ul/li[3]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[3]/a"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

			case 4:

				boolean bookmarksFolder4Present = selenium.isElementPresent(
						"//td[4]/span/ul/li/strong/a");

				if (!bookmarksFolder4Present) {
					label = 5;

					continue;
				}

				selenium.clickAt("//td[4]/span/ul/li/strong/a",
					RuntimeVariables.replace(""));
