package org.example.gakkoextend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Table(name="answer")
@Builder
@Entity
public class Answer {

    @Id @GeneratedValue @UuidGenerator
    private UUID id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name="submission_id", nullable=false,
            foreignKey=@ForeignKey(name="fk_answer_submission"))
    private Submission submission;


    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name="question_id", nullable=false,
            foreignKey=@ForeignKey(name="fk_answer_question"))
    private Question question;


    @ElementCollection
    @CollectionTable(name="answer_choice",
            joinColumns=@JoinColumn(name="answer_id",
                    foreignKey=@ForeignKey(name="fk_achoice_answer")))
    @Column(name="idx", nullable=false)
    private Set<Integer> chosenIndexes = new HashSet<>();

    @Column(nullable = false)
    private boolean isCorrect;

    public Answer(UUID id, Submission submission, Question question, Set<Integer> chosenIndexes, boolean isCorrect) {
        this.id = id;
        this.submission = submission;
        this.question = question;
        this.chosenIndexes = chosenIndexes;
        this.isCorrect = isCorrect;
    }

    public Answer() {

    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Submission getSubmission() {
        return submission;
    }

    public void setSubmission(Submission submission) {
        this.submission = submission;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Set<Integer> getChosenIndexes() {
        return chosenIndexes;
    }

    public void setChosenIndexes(Set<Integer> chosenIndexes) {
        this.chosenIndexes = chosenIndexes;
    }
}
