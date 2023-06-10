import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sentence {
  private final List<String> words_List;

  /**
   * Constructor.
   * @param words a string of words separated by spaces
   */
  public Sentence(String words) {
    words_List = new ArrayList<>();
    String[] wordArray = words.split("\\s+");
    if (wordArray.length == 1 && wordArray[0].equals("")) {
      return;
    }
    words_List.addAll(Arrays.asList(wordArray));
    }

  /**
   * Returns true if the word is a punctuation.
   * @param word a string to check.
   * @return true if the word is a punctuation.
   */
  private boolean isPunctuation(String word) {
    return word.matches("\\p{Punct}");
  }

  /**
   * Returns the number of words in the sentence.
   * @return int number of words.
   */
  public int getNumberOfWords() {
    return words_List.size();
  }

  /**
   * Returns the longest word in the sentence.
   * @return string representation of the longest word
   */
  public String longestWord() {
    String longest = "";
    for (String word : words_List) {
      if (!isPunctuation(word) && word.length() > longest.length()) {
        longest = word;
      }
    }
    return longest;
  }

  /**
   * Returns a string representation of the sentence.
   * @return a string representation of the sentence
   */
  public String toString() {
    String result = "";
    for (int i = 0; i < words_List.size(); i++) {
      if (i == words_List.size() - 1) {
        result += words_List.get(i);
        break;
      }
      else {
        result += words_List.get(i) + " ";
      }
    }
    return result;
  }

  /**
   * Returns a copy of the sentence.
   * @return a new sentence with the same words.
   */
  public Sentence clone() {
    return new Sentence(this.toString());
  }

  /**
   * Merges two sentences.
   * @param other another sentence.
   * @return a new sentence with the words from this and other.
   */
  public Sentence merge(Sentence other) {
    String result = this.toString() + " " + other.toString();
    return new Sentence(result);
  }

  /**
   * Returns the number of punctuations in the sentence.
   * @return the number of punctuations in the sentence.
   */
  public int countPunctuation() {
    int count = 0;
    for (String word : words_List) {
      if (isPunctuation(word)) {
        count++;
      }
    }
    return count;
  }

  /**
   * Returns the number of words that contain the letter 'z'.
   * @return the number of words that contain the letter 'z'.
   */
  public int countZWords() {
    int count = 0;
    for (String word : words_List) {
      if (word.contains("z")) {
        count++;
      }
    }
    return count;
  }

  /**
   * Converts the sentence to pig latin.
   * @return string representation of the sentence in pig latin.
   */
  public String pigLatin() {
    for (String word : words_List) {
      if (isPunctuation(word)) {
        continue;
      }

      // check if the word starts with a consonant and a vowel
      // move it to the end and add "ay"
      else if (isVowel(word.substring(0))) {
        return;
      }

      // chcek if the word starts with two consonants
      // move them to the end and add "ay"
      else if () {
        return;
      }

      // check if the word starts with a vowel
      // add "way" to the end

    }
    return " ";
  }

  private boolean isVowel(String word) {
    return word.matches("[aeiouAEIOU]");
  }
}