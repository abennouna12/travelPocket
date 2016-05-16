package ig2i.travelPocket.model;

import java.util.Objects;

/**
 * Created by aBennouna on 11/05/2016.
 */
public class SuggestionType {

    public String name;
    public String value;
    public Boolean checked;

    public String getName(){
        return this.name;
    }

    public String getValue() {
        return this.value;
    }

    public Boolean getChecked() {
        return this.checked;
    }

    public SuggestionType(String name, String value, Boolean checked) {
        this.name = name;
        this.value = value;
        this.checked = checked;
    }

    public SuggestionType() {

    }

    @Override
    public boolean equals(Object o) {
        SuggestionType suggest = (SuggestionType)o;
        return (this.name == suggest.name) ? true : false;
    }
}
