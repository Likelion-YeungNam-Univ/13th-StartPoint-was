package startpointwas.domain.mentor.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Entrepreneur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String storeName;

    private String category;

    private String area;

    @Column(columnDefinition = "TEXT")
    private String bio;

    private int likeCount;

    private String headline;

    @Column(columnDefinition = "TEXT")
    private String intro;

    private LocalDate registeredDate;

    private LocalTime registeredTime;

    @ElementCollection
    @CollectionTable(name = "entrepreneur_keywords", joinColumns = @JoinColumn(name = "entrepreneur_id"))
    @Column(name = "keyword")
    private List<String> keywords;

    @ElementCollection
    @CollectionTable(name = "entrepreneur_topics", joinColumns = @JoinColumn(name = "entrepreneur_id"))
    @Column(name = "topic")
    private List<String> topics;
}
