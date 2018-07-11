package codepath.com.instagram;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Post")
public class Post extends ParseObject {

    private static final String KEY_AUTHOR = "owner";
    private static final String KEY_MEDIA = "picture";
    private static final String KEY_CAPTION = "caption";
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

    public static Post newInstance(ParseUser author, ParseFile media, String caption) {
        Post post = new Post();
        post.setAuthor(author);
        post.setMedia(media);
        post.setCaption(caption);
        post.saveInBackground();
        return post;
    }




}
/*import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

mport com.parse.ParseClassName;
        Aimport com.parse.ParseClassName;

@ParseClassName("Post")
class Post extends ParseObject {

    public Post(){ super();}
    public Post(ParseFile pic, String caption){
        super();
        setPicture(pic);
        setCaption(caption);
    }
    public ParseFile getPicture() {
        return getParseFile("picture");
    }
    public void setPicture(ParseFile parseFile) {
        put("picture", parseFile);
    }
    public ParseUser getOwner()  {
        return getParseUser("owner");
    }
    public void setOwner(ParseUser user) {
        put("owner", user);
    }
    public String getCaption(){
        return getString("caption");
    }
    public void setCaption(String s){
        put("caption",s);
    }

    public static class Query extends ParseQuery<Post>{
        public Query(){
            super(Post.class);
        }
        public Query getTop()
        {
            setLimit(20);
            return this;
        }
        public Query withUser(){
            include("owner");
            return this;
        }
    }
}*/