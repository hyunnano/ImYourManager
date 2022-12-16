package net.manager.iym.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@ToString
@Data
@Builder
@NoArgsConstructor
@Getter
public class ScheduleDTO{

    private Long scheduleNum;
    @NotEmpty
    private String id;

    @NotEmpty(message = "경기 날짜를 입력해주세요.")
    private String scheduleDate; //일정날짜

    @NotEmpty(message = "시작시간을 입력해주세요.")
    private String scheduleStartTime;   //경기 시간
    @NotEmpty(message = "종료시간을 입력해주세요.")
    private String scheduleEndTime;   //경기 시간

    @NotEmpty(message = "경기 장소를 입력해주세요.")
    private String ground; //일정지역

    @NotEmpty(message = "경기 유형을 입력해주세요.")
    private String playType; //일정경기유형
//
//    private String vote; //일정참가여부


}