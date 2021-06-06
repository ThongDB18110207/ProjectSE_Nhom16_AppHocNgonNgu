package com.example.languages_learning_app.DTO.DictionaryApi;

import java.util.List;

public class Root {
    public List<Dictionary> dictionaries;

    public List<Dictionary> getDictionaries() {
        return dictionaries;
    }

    public void setDictionaries(List<Dictionary> dictionaries) {
        this.dictionaries = dictionaries;
    }

    public Root(List<Dictionary> dictionaries) {
        this.dictionaries = dictionaries;
    }

    public Root() {
    }
}
