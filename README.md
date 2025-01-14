# sonarvsllm

This project has the classes analysed in the paper 1 (DOI [10.48550/arXiv.2408.07082](https://doi.org/10.48550/arXiv.2408.07082)) (Zeonodo [![DOI](https://zenodo.org/badge/DOI/10.5281/zenodo.13890179.svg)](https://doi.org/10.5281/zenodo.13890179)
) and paper 2 (DOI [XXX](https://doi.org/XXXXXXX.XXXXXXX)).

---
***Paper 2*** 
Submited to FSE 2025

***Scope***
This paper performed a controlled quasi-experiment to evaluate the responses given by 8 LLMs and SonarQube to 3 scenarios of interventions in source code snippets.

***Details***

**Note to Revisors:** The https://anonymous.4open.science/ website has a glitch. When you click on any link below that takes you to a folder, it will hang on loading screen. You need to refresh the page and aldo at the folder tree (left side) you will need to click outside the folder and back to it again, so the content get loaded.

The classes analysed in the second mentioned paper are under [controlled](sonarvsllm-testcases/src/main/resources/classFilesToBeAnalysed/controlled) folder. There is one subfolder for each scenario.

The classes below are responsible for LLM analysis using the respective LLMs, both the robust and the fast versions.
* [SourceCodeLLM4o](sonarvsllm-testcases/src/main/java/science/com/master/sonar/SourceCodeLLM4o.java) 
* [SourceCodeLLMAnthropicClaude](sonarvsllm-testcases/src/main/java/science/com/master/sonar/SourceCodeLLMAnthropicClaude.java) 
* [SourceCodeLLMGoogleGemini](sonarvsllm-testcases/src/main/java/science/com/master/sonar/SourceCodeLLMGoogleGemini.java) 
* [SourceCodeLLMMetaLlama](sonarvsllm-testcases/src/main/java/science/com/master/sonar/SourceCodeLLMMetaLlama.java) 

The json files containing the dataset generated with LLM analysis for the respective sourcecode obtained by the time of execution of this research, can be found in the respective folders:

* [Claude3-haiku](sonarvsllm-testcases/src/main/resources/controlled/Claude3-haiku)
* [Claude35-sonnet](sonarvsllm-testcases/src/main/resources/controlled/Claude35-sonnet)
* [GPT4o-mini](sonarvsllm-testcases/src/main/resources/controlled/GPT4o-mini)
* [GPT4o](sonarvsllm-testcases/src/main/resources/controlled/GPT4o)
* [Gemini15flash](sonarvsllm-testcases/src/main/resources/controlled/Gemini15flash)
* [Gemini15pro](sonarvsllm-testcases/src/main/resources/controlled/Gemini15pro)
* [Llama3-8B](sonarvsllm-testcases/src/main/resources/controlled/Llama3-8B)
* [Llama31-405B](sonarvsllm-testcases/src/main/resources/controlled/Llama31-405B)

To execute the Sonar analysis for the second paper is necessary to run the command below (Note: Replace the project key to your own project at Sonarcloud):
`./mvnw verify org.sonarsource.scanner.maven:sonar-maven-plugin:sonar -Dsonar.projectKey=igorregis_sonarvsllm`
`./mvnw quarkus:dev -Dquarkus.package.main-class=science.com.master.sonar.LLMAnalyser`

---
***Paper 1*** 

***Scope***

This paper performed a comparative study of code quality analysis using LLM, focusing on readability, comparing the results with a reference tool (SonarQube)

***Details***

The classes analysed in the first mentioned paper are under [Quarkus folder](sonarvsllm-testcases/src/main/resources/classFilesToBeAnalysed/quarkus) and [Shattered Pixel Dungeon folder](sonarvsllm-testcases/src/main/resources/classFilesToBeAnalysed/shattered-pixel-dungeon/core/src/main/java/com/shatteredpixel/shatteredpixeldungeon). They have been preserved.

The classes [SourceCodeLLM](sonarvsllm-testcases/src/main/java/science/com/master/sonar/SourceCodeLLM.java) and [SourceCodeLLM4o](sonarvsllm-testcases/src/main/java/science/com/master/sonar/SourceCodeLLM4o.java) are responsible for LLM analysis using the respective GPT 3.5-turbo and GPT4o versions.

The json files listed below have the dataset generated with LLM Analysis and SonarQube metrics for the respective classes obtained by the time of execution of this research:
* [sonarAndLLM35Quarkus.json](sonarvsllm-testcases/src/main/resources/sonarAndLLM35Quarkus.json)
* [sonarAndLLM35Shatteredpixeldungeon.json](sonarvsllm-testcases/src/main/resources/sonarAndLLM35Shatteredpixeldungeon.json)
* [sonarAndLLM4oQuarkus.json](sonarvsllm-testcases/src/main/resources/sonarAndLLM4oQuarkus.json) 
* [sonarAndLLM4oShatteredpixeldungeon.json](sonarvsllm-testcases/src/main/resources/sonarAndLLM4oShatteredpixeldungeon.json)

They have been preserved, since the SonarQube analysis is updated alongside the respective projects.
