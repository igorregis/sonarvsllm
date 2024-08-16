# sonarvsllm

This project has the classes analysed in the paper (DOI [10.48550/arXiv.2408.07082](https://doi.org/10.48550/arXiv.2408.07082)) and paper (DOI [XXX](https://doi.org/XXXXXXX.XXXXXXX)).

The classes analysed in the first mentioned paper are under [Quarkus folder](sonarvsllm-testcases/src/main/resources/classFilesToBeAnalysed/quarkus) and [Shattered Pixel Dungeon folder](sonarvsllm-testcases/src/main/resources/classFilesToBeAnalysed/shattered-pixel-dungeon/core/src/main/java/com/shatteredpixel/shatteredpixeldungeon). They have been preserved.

The classes [SourceCodeLLM](sonarvsllm-testcases/src/main/java/br/com/master/sonar/SourceCodeLLM.java) and [SourceCodeLLM4o](sonarvsllm-testcases/src/main/java/br/com/master/sonar/SourceCodeLLM4o.java) are responsible for LLM analysis using the respective GPT 3.5-turbo and GPT4o versions.

The json files listed below have the dataset generated with LLM Analysis and SonarQube metrics for the respective classes obtained by the time of execution of this research:
* [sonarAndLLM35Quarkus.json](sonarvsllm-testcases/src/main/resources/sonarAndLLM35Quarkus.json)
* [sonarAndLLM35Shatteredpixeldungeon.json](sonarvsllm-testcases/src/main/resources/sonarAndLLM35Shatteredpixeldungeon.json)
* [sonarAndLLM4oQuarkus.json](sonarvsllm-testcases/src/main/resources/sonarAndLLM4oQuarkus.json) 
* [sonarAndLLM4oShatteredpixeldungeon.json](sonarvsllm-testcases/src/main/resources/sonarAndLLM4oShatteredpixeldungeon.json)

They have been preserved, since the SonarQube analysis is updated alongside the respective projects.

Details for the second paper will be soon added to this file.
