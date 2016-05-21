package ig2i.travelPocket.model;

import java.util.Objects;


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
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        final SuggestionType other = (SuggestionType) obj;
        return (!Objects.equals(this.name, other.name));

    }

}
