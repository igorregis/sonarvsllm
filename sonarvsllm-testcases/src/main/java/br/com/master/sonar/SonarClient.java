package br.com.master.sonar;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class SonarClient {
    private static final String SONAR_API_URL = "https://sonarcloud.io/api/measures/component";

    public static SonarResponse getComponentMeasures(String componentKey) {
        System.out.println(componentKey);
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(SONAR_API_URL)
                .queryParam("component", componentKey.replaceAll("\\.", "%2F").
                        replaceAll("%2Fjava", ".java"))
                .queryParam("metricKeys", "lines,code_smells,cognitive_complexity,comment_lines_density,complexity,files,sqale_rating,statements");

        System.out.println("URL gerada: " + target.getUri());

        return target.request(MediaType.APPLICATION_JSON)
                .get(SonarResponse.class);

    }
}
