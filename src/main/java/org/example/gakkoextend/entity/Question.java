package org.example.gakkoextend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.*;

@Entity
@Table(name = "question")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Question {

    public enum Kind {SINGLE, MULTI}

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_question_quiz"))
    private Quiz quiz;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 8)
    private Kind kind;

    @Column(nullable = false, columnDefinition = "text")
    private String content;

    // Opcje jako prosta kolekcja (JPA zrobi tabelę question_option)
    @ElementCollection
    @CollectionTable(name = "question_option",
            joinColumns = @JoinColumn(name = "question_id",
                    foreignKey = @ForeignKey(name = "fk_qopt_question")))
    @Column(name = "label", nullable = false)
    private List<String> options = new ArrayList<>();

    // Indeksy poprawnych odpowiedzi (np. 0,2)
    @ElementCollection
    @CollectionTable(name = "question_correct_index",
            joinColumns = @JoinColumn(name = "question_id",
                    foreignKey = @ForeignKey(name = "fk_qcorr_question")))
    @Column(name = "idx", nullable = false)
    private Set<Integer> correctIndexes = new HashSet<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public Kind getKind() {
        return kind;
    }

    public void setKind(Kind kind) {
        this.kind = kind;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public Set<Integer> getCorrectIndexes() {
        return correctIndexes;
    }

    public void setCorrectIndexes(Set<Integer> correctIndexes) {
        this.correctIndexes = correctIndexes;
    }
}
