package hello;

public class User {
    private int id;
    private String user, pass, email;

    public User(int id, String user, String pass,String email)
    {
        this.id = id;
        this.user = user;
        this.pass = pass;
        this.email = email;
    }

    @Override
    public String toString()
    {
        return String.format("User[id=%d, user='%s', pass='%s', email='%s']",)
    }
}
