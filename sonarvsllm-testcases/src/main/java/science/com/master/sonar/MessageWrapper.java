package science.com.master.sonar;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.google.gson.Gson;

import java.util.List;

@JsonRootName(value = "messages")
class MessageWrapper {

    @JsonProperty("messages")
    private List<Message> messages;

    // Getters e Setters
    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    // Classe interna Message
    static class Message {

        Message(String role, String content) {
            this.role = role;
            this.content = content;
        }

        @JsonProperty("role")
        private String role;

        @JsonProperty("content")
        private String content;

        // Getters e Setters
        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(messages);
    }
}
