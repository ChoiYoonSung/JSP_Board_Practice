package kr.or.ddit.service;

import java.sql.SQLException;
import java.util.Map;

import kr.or.ddit.command.SearchCriteria;
import kr.or.ddit.dto.MemberVO;

public interface MemberService {
	Map<String, Object> getMemberList(SearchCriteria cri) throws SQLException;
	
	MemberVO getMemberById(String id) throws SQLException;
	
	void registMember(MemberVO member) throws SQLException;
	
	void modifyMember(MemberVO member) throws SQLException;
	
	void removeMember(String id) throws SQLException;
}
