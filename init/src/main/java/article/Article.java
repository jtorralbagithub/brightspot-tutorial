package article;

import java.util.*;
import com.psddev.cms.db.Content;
import com.psddev.cms.db.ToolUi;
import com.psddev.dari.db.State;
import com.psddev.dari.util.StorageItem;
import com.psddev.dari.util.StringUtils;
import com.psddev.cms.db.Directory;
import com.psddev.cms.db.Site;
import author.Author;

public class Article extends Content implements Directory.Item  {

    @Required
    private String headline;

    @ToolUi.RichText
    private String body;
    private Author author;
    private StorageItem image;

    // Adding a variety of field types
    private Date date; // a date widget
    private boolean switchComponent; // a toggle on the page
    @ToolUi.Secret
    @ToolUi.Note("must be greater than 8 characters")
    private String password; // a hidden text "password" field in the UI
    @ToolUi.AddToTop
    private List<Author> listOfAuthors; // a list of authors

    public String getHeadline() {
        return headline;
    }

    public String getBody() {
        return body;
    }

    public StorageItem getImage() {
        return image;
    }

    // Adding a vareity of getters for field type
    public Date getDate() {
        return date;
    }

    public boolean getSwitchComponent() {
        return switchComponent;
    }

    public String getPassword() {
        return password;
    }

    public List<Author> getAuthors() {
        return listOfAuthors;
    }

    @Override
    public String createPermalink(Site site) {
        return StringUtils.toNormalized(getHeadline());
    }

    protected void beforeSave() {
        // check if password is the correct length
        try {
            if (password.length() < 8) {
                throw new Exception();
            }
        } catch (final Exception e) {
            State state = getState();
            state.addError(state.getField("password"), "Password must be 8 characters or more.");
            throw new IllegalArgumentException("Resolve the error with the password, then retry Publish.");
        }
    }

}
