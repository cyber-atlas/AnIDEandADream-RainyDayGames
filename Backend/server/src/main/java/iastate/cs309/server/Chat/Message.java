package iastate.cs309.server.Chat;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * a message describes a sender, recipient and the text content they shared
 */
public class Message {

    @Id
    private Integer id;

    @Column
    private String from;

    @Column
    private String to;

    @Column
    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }


}
