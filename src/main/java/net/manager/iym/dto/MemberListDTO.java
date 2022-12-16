package net.manager.iym.dto;

import lombok.*;
import net.manager.iym.domain.Team;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberListDTO {

    @NotEmpty
    private String id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String mail;

    private Team team;
    @NotEmpty
    private String phone;
    @NotEmpty
    private String gender;
    @NotEmpty
    private String memberLoc;

}
