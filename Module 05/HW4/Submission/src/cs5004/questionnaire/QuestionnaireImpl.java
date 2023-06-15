package cs5004.questionnaire;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Predicate;

/**
 * Represents a collection of {@link Question}s, forming a questionnaire.
 */
public class QuestionnaireImpl implements Questionnaire {
  private final List<Question> questionsList;
  private final Map<String, Question> questionsMap;

  public QuestionnaireImpl() {
    questionsList = new ArrayList<Question>();
    questionsMap = new HashMap<String, Question>();
  }

  /**
   * Add a question to the questionnaire.
   *
   * @param identifier a name for the question <b>unique</b> within this questionnaire. Not null
   *                   or empty.
   * @param q          the {@link Question} to be added to the questionnaire
   */
  public void addQuestion(String identifier, Question q) {
    if (identifier == null || identifier.isEmpty()) {
      throw new IllegalArgumentException("identifier cannot be null or empty.");
    }
    questionsList.add(q);
    questionsMap.put(identifier, q);
  }

  /**
   * Remove the question with the given identifier from the questionnaire.
   *
   * @param identifier the identifier of the question to be removed.
   * @throws NoSuchElementException if there is no question with the given identifier.
   */
  public void removeQuestion(String identifier) {
    // Try removing from the hashmap first
    Question removedQuestion = questionsMap.remove(identifier);
    // if successful, remove it from the arrayList
    if (removedQuestion != null) {
      questionsList.remove(removedQuestion);
    }
    // else throw an exception
    else {
      throw new NoSuchElementException("no question with given identifier.");
    }
  }

  /**
   * Get the question with the given number, based on the order in which it was added to the
   * questionnaire, or the sorted order if the {@code sort()} method is called. The first question
   * is 1, second 2, etc.
   *
   * @param num the number of the question, counting from 1.
   * @return the question.
   * @throws IndexOutOfBoundsException if there is no such question num.
   */
  public Question getQuestion(int num) {
    if (num > questionsList.size()) {
      throw new IndexOutOfBoundsException("Out of bounds.");
    }
    return questionsList.get(num);
  }

  /**
   * Get the question with the given identifier (question having been previously added to the
   * questionnaire).
   *
   * @param identifier the identifier of the question.
   * @return the question.
   * @throws NoSuchElementException if there is no question with the identifier.
   */
  public Question getQuestion(String identifier) {
    // get the question
    Question askedQuestion = questionsMap.get(identifier);
    // if found, return
    if (askedQuestion != null) {
      return questionsMap.get(identifier);
    }
    // else throw error
    else {
      throw new NoSuchElementException("no question with the identifier");
    }
  }

  /**
   * Return a list of all required questions in the questionnaire.
   *
   * @return the required questions.
   */
  public List<Question> getRequiredQuestions() {
    // create a new list
    List<Question> required_questions = new ArrayList<>();

    for (Question q : this.questionsList) {
      // for every question that is required, add it to new list
      if (q.isRequired()) {
        required_questions.add(q);
      }
    }
    return required_questions;
  }

  /**
   * Return a list of all optional questions in the questionnaire.
   *
   * @return the optional questions.
   */
  public List<Question> getOptionalQuestions() {
    // create a new list
    List<Question> optional_questions = new ArrayList<>();

    for (Question q : this.questionsList) {
      // for every question that is NOT required, add it to new list
      if (!q.isRequired()) {
        optional_questions.add(q);
      }
    }
    return optional_questions;
  }

  /**
   * Report if all required questions have some non-empty response.
   *
   * @return true if all required questions have responses, false otherwise.
   */
  public boolean isComplete() {
    // for each question in questionsList
    for (Question q: questionsList) {
      // if a question is required and answer is null, return false
      if (q.isRequired() && Objects.equals(q.getAnswer(), "")) {
        return false;
      }
    }
    // else it is complete and return true
    return true;
  }

  /**
   * Return a list of just the responses to all the questions in the questionnaire.
   *
   * @return the responses
   */
  public List<String> getResponses() {
    List<String> responses_list = new ArrayList<>();
    // iterate over all questions in questionsList
    for (Question q : this.questionsList) {
      // add answer for each question to responses_list
      responses_list.add(q.getAnswer());
    }
    return responses_list;
  }

  /**
   * Produce a new questionnaire containing just the questions where the given predicate returns
   * true. The returned questionnaire is completely independent of this questionnaire. That is,
   * the questions in the returned questionnaire are <b>copies</b> of the original questions.
   *
   * @param pq the predicate.
   * @return the new questionnaire.
   */
  public Questionnaire filter(Predicate<Question> pq) {
    // create a new questionnaire
    QuestionnaireImpl filteredQuestionnaire = new QuestionnaireImpl();

    // for each question in questionsList
    for (Question question : questionsList) {
      // if this test passes, add it to the filteredQuestionnaire
      if (pq.test(question)) {
        // create a new question
        Question copiedQuestion = question.copy();
        // add the question to the filteredQuestionnaire

        /*filteredQuestionnaire.addQuestion(getIdentifier((HashMap<String, Question>)
                questionsMap, question), question.copy());*/
      }
    }
    return filteredQuestionnaire;
  }

  /**
   * Sort the questions according to the given comparator. Return values from
   * {@code getQuestion(int)} should reflect the new sorted order following sort.
   *
   * @param comp a comparator for Question
   */
  public void sort(Comparator<Question> comp) {
    Collections.sort(this.questionsList, comp);
  }

  /**
   * Produce a single summary value based on the given folding function and
   * seed value.
   *
   * @param bf   the folding function
   * @param seed the seed value
   * @return the summary value
   */
  public <R> R fold(BiFunction<Question, R, R> bf, R seed) {
    return null;
  }

}

//class comp implements Comparator<Question> {
//}