package codepath.com.instagram;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;


@ParseClassName("Comment")
public class Comment extends ParseObject {

    private static final String KEY_AUTHOR = "author";
    private static final String KEY_BODY = "body";

    public String getBody() {
        return getString(KEY_BODY);
    }
    public void setBody(String body) { put(KEY_BODY, body); }

    public ParseUser getAuthor() {
        return getParseUser(KEY_AUTHOR);
    }
    public void setAuthor(ParseUser author) {
        put(KEY_AUTHOR, author);
    }


    public static Comment newInstance(String body, ParseUser u ) {
        Comment comment = new Comment();
        comment.setBody(body);
        comment.setAuthor(u);
        comment.saveInBackground();
        return comment;
    }




}