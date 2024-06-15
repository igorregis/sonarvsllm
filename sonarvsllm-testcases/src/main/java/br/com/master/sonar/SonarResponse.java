package br.com.master.sonar;

public class SonarResponse {
    private Component component;

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }

    @Override
    public String toString() {
        return "SonarResponse{" + "component=" + component + '}';
    }
}
