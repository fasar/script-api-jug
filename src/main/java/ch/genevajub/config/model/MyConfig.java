package ch.genevajub.config.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@ConfigurationProperties(prefix = "config")
public class MyConfig {

    GithubConfig github;
    EventbriteConfig eventbrite;

    public GithubConfig getGithub() {
        return github;
    }

    public void setGithub(GithubConfig github) {
        this.github = github;
    }

    public EventbriteConfig getEventbrite() {
        return eventbrite;
    }

    public void setEventbrite(EventbriteConfig eventbrite) {
        this.eventbrite = eventbrite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyConfig myConfig = (MyConfig) o;
        return Objects.equals(github, myConfig.github) &&
                Objects.equals(eventbrite, myConfig.eventbrite);
    }

    @Override
    public int hashCode() {
        return Objects.hash(github, eventbrite);
    }

    @Override
    public String toString() {
        return "MyConfig{" +
                "github=" + github +
                ", eventbrite=" + eventbrite +
                '}';
    }

    public static class GithubConfig {
        String token;
        String owner;
        String repo;
        String host;
        Integer port;
        Boolean ssl;
        Boolean disableConnexion;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public String getRepo() {
            return repo;
        }

        public void setRepo(String repo) {
            this.repo = repo;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public Integer getPort() {
            return port;
        }

        public void setPort(Integer port) {
            this.port = port;
        }

        public Boolean getSsl() {
            return ssl;
        }

        public void setSsl(Boolean ssl) {
            this.ssl = ssl;
        }

        public Boolean getDisableConnexion() {
            return disableConnexion;
        }

        public void setDisableConnexion(Boolean disableConnexion) {
            this.disableConnexion = disableConnexion;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GithubConfig that = (GithubConfig) o;
            return Objects.equals(token, that.token) &&
                    Objects.equals(owner, that.owner) &&
                    Objects.equals(repo, that.repo) &&
                    Objects.equals(host, that.host) &&
                    Objects.equals(port, that.port) &&
                    Objects.equals(ssl, that.ssl) &&
                    Objects.equals(disableConnexion, that.disableConnexion);
        }

        @Override
        public int hashCode() {
            return Objects.hash(token, owner, repo, host, port, ssl, disableConnexion);
        }

        @Override
        public String toString() {
            return "GithubConfig{" +
                    "token='" + token + '\'' +
                    ", owner='" + owner + '\'' +
                    ", repo='" + repo + '\'' +
                    ", host='" + host + '\'' +
                    ", port=" + port +
                    ", ssl=" + ssl +
                    ", disableConnexion=" + disableConnexion +
                    '}';
        }
    }


    public static class EventbriteConfig {
        String token;
        String organizationId;
        String host;


        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getOrganizationId() {
            return organizationId;
        }

        public void setOrganizationId(String organizationId) {
            this.organizationId = organizationId;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }
    }
}
