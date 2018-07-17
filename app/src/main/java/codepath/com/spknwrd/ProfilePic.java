package codepath.com.spknwrd;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
@ParseClassName("ProfilePic")

public class ProfilePic extends ParseObject{
    private static final String KEY_AUTHOR = "owner";
    private static final String KEY_MEDIA = "picture";
    public ParseUser getAuthor() {
        return getParseUser(KEY_AUTHOR);
    }

    public void setAuthor(ParseUser author) {
        put(KEY_AUTHOR, author);
    }

    public ParseFile getMedia() {
        return getParseFile(KEY_MEDIA);
    }

    public void setMedia(ParseFile media) {
        put(KEY_MEDIA, media);
    }

    public static Post newInstance(ParseUser author, ParseFile media) {
        Post post = new Post();
        post.setAuthor(author);
        post.setMedia(media);
        post.saveInBackground();
        return post;
    }
}
