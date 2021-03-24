package com.qvik.events.modules.image;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.qvik.events.modules.event.Event;
import lombok.*;

import javax.persistence.*;
import java.util.List;

/** Entity for table Image */
@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "imageId")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "image_id", nullable = false, updatable = false)
    private long imageId;

    @Column(name = "content", nullable = false)
    byte[] content;

    @Column(name = "name", nullable = false)
    String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "image")
    @JsonBackReference
    private List<Event> events;

    public Image(byte[] content, String name) {
        this.content = content;
        this.name = name;
    }
}
