package science.com.master.sonar;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;

import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class SonarClient {
    private static final String SONAR_API_URL = "https://sonarcloud.io/api/measures/component";

    private static final Map<String, SonarResponse> CACHE = new HashMap<>();

    public synchronized SonarResponse getComponentMeasures(String componentKey, String branch) {
        if (CACHE.containsKey(componentKey + branch)) {
            System.out.println("Cache Hit! " + branch);
            return CACHE.get(componentKey + branch);
        }
        System.out.println(componentKey);
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(SONAR_API_URL)
                .queryParam("component", componentKey.replaceAll("\\.", "%2F").
                        replaceAll("%2Fjava", ".java"))
                .queryParam("metricKeys", "lines,code_smells,cognitive_complexity,comment_lines_density,complexity,files,sqale_rating,statements")
                .queryParam("branch", branch);
        System.out.println("URL gerada: " + target.getUri());

        SonarResponse response = target.request(MediaType.APPLICATION_JSON).get(SonarResponse.class);
        CACHE.put(componentKey + branch, response);
        client.close();
        return response;

    }

}
