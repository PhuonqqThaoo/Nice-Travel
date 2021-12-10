package com.nicetravel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Table(name = "events")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "booking_Id")
    private Booking booking;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "title", nullable = false, length = 200)
    @NotBlank(message = "Không để trống title")
    private String title;

    @NotBlank(message = "Không để trống description")
    @Column(name = "description", nullable = false, length = 500)
    private String description;

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", booking=" + booking +
                ", account=" + account +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", createDate=" + createDate +
                ", is_delete=" + is_delete +
                '}';
    }

    @Column(name = "create_date", nullable = false)
    @CreationTimestamp
    private Date createDate;

    @Column(name = "is_delete", nullable = false)
    private Boolean is_delete = false;
}