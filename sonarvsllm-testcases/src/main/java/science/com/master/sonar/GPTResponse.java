package science.com.master.sonar;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GPTResponse {

    @JsonProperty("score")
    public String score;

    @JsonProperty("reasoning")
    public String reasoning;

    @JsonProperty("tokens")
    public Integer tokens;
    @JsonProperty("lineCount")
    public Integer lineCount;

    @JsonProperty("name")
    public String name;

    public SonarResponse sonarData;

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}