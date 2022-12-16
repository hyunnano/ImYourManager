package net.manager.iym.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@NoArgsConstructor
@Entity
@Builder
@Getter
@AllArgsConstructor
@ToString
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "scheduleNum")
    private Long scheduleNum;
    @ManyToOne
    @JoinColumn(name="teamNum", nullable = false)
    private Team team;


    @Column(length = 200, nullable = false)
    private String ground;

    @Column(length = 20, nullable = false)
    private String playType;

    @Column(length = 30, nullable = false)
    @DateTimeFormat(pattern= "yyyy-MM-dd")
    //@JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime scheduleDate;
    @Column(length = 30, nullable = false)  // Date 체크필요
    @DateTimeFormat(pattern ="HH:mm")
    //@JsonFormat(pattern = "HH:mm")
    private LocalTime scheduleStartTime;
    @Column(length = 30, nullable = false)  // Date 체크필요
    @DateTimeFormat(pattern ="HH:mm")
    //@JsonFormat(pattern = "HH:mm")
    private LocalTime scheduleEndTime;


}