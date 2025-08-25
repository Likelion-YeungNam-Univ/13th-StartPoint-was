package startpointwas.domain.mentor.entity;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;


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

    private String largeCategory;

    private String area;

    @Column(columnDefinition = "TEXT")
    private String bio;

    private int likeCount;

    private String headline;

    @Column(columnDefinition = "TEXT")
    private String intro;

    private LocalDate registeredDate;

    private LocalTime registeredTime;

    private String keywords;

    @ElementCollection
    @CollectionTable(name = "entrepreneur_topics", joinColumns = @JoinColumn(name = "entrepreneur_id"))
    @Column(name = "topic")
    private List<String> topics;
}
