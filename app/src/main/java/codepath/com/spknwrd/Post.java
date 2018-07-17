package codepath.com.spknwrd;


import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseRelation;
import com.parse.ParseUser;

@ParseClassName("Post")
public class Post extends ParseObject {

    private static final String KEY_AUTHOR = "owner";
    private static final String KEY_MEDIA = "picture";
    private static final String KEY_CAPTION = "caption";
    private static final String KEY_LIKES = "like";
    private static final String KEY_COMMENTS="commemts";
    private static final String KEY_LIKE_RELATION="likerelation";


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

    public String getCaption() {
        return getString(KEY_CAPTION);
    }

    public void setCaption(String caption) {
        put(KEY_CAPTION, caption);
    }

    public int getLike(){return getInt(KEY_LIKES);}

    public void setLike(int i){ put(KEY_LIKES, i);}

    public void like(ParseUser user){put(KEY_LIKES,getInt(KEY_LIKES)+1);
        addUser(user);
    }

    public void unlike(ParseUser user){
        put(KEY_LIKES,getInt(KEY_LIKES)-1);
        removeUser(user);
    }

    public ParseRelation<Comment> getCommentRelations() {
        return getRelation(KEY_COMMENTS);
    }

    public void addComment(Comment comment) {
        getCommentRelations().add(comment);
        saveInBackground();
    }
    public void removeComment(Comment comment) {
        getCommentRelations().remove(comment);
        saveInBackground();
    }
    public ParseRelation<ParseUser> getLikeRelation() {
        return getRelation(KEY_LIKE_RELATION);
    }
    public void addUser(ParseUser user) {
        getLikeRelation().add(user);
        saveInBackground();
    }
    public void removeUser(ParseUser user) {
        getLikeRelation().remove(user);
        saveInBackground();
    }
    public static Post newInstance(ParseUser author, ParseFile media, String caption) {
        Post post = new Post();
        post.setAuthor(author);
        post.setMedia(media);
        post.setLike(0);
        post.setCaption(caption);
        post.saveInBackground();
        return post;
    }
}
