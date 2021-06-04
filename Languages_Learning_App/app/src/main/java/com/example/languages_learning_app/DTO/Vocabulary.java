package com.example.languages_learning_app.DTO;

public class Vocabulary {
    // Required parameters
    private String id;
    private String word;
    private String meaning;

    // Optional parameters
    private String pronunciation;
    private String imageUrl;

    public String getId() {
        return id;
    }

    public String getWord() {
        return word;
    }

    public String getMeaning() {
        return meaning;
    }

    public String getPronunciation() {
        return pronunciation;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    // Private constructors
    private Vocabulary(){}

    public Vocabulary(Vocabulary.VocabularyBuilder builder) {
        this.pronunciation = builder.pronunciation;
        this.imageUrl = builder.imageUrl;
    }

    // Builder class
    public static class VocabularyBuilder {
        // Required parameters
        private String id;
        private String word;
        private String meaning;

        // Optional parameters
        private String pronunciation;
        private String imageUrl;

        public VocabularyBuilder(String id, String word, String meaning) {
            this.id = id;
            this.word = word;
            this.meaning = meaning;
        }

        public Vocabulary.VocabularyBuilder setPronunciation(String pronunciation) {
            this.pronunciation = pronunciation;
            return this;
        }

        public Vocabulary.VocabularyBuilder setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Vocabulary build() {
            return new Vocabulary(this);
        }
    }
}
