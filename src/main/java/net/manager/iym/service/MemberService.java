package net.manager.iym.service;


import net.manager.iym.dto.MemberDTO;

public interface MemberService {

    String register(MemberDTO memberDTO);//회원 등록 메소드
    MemberDTO readMember();//회원 정보 조회 메소드
    void modify(MemberDTO memberDTO);//회원 정보 수정 메소드
    void remove(String id);//탈퇴 메소드



}
