package kr.or.ddit.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import kr.or.ddit.command.SearchCriteria;
import kr.or.ddit.dto.MemberVO;

public interface MemberDAO {
	List<MemberVO> selectMemberList(SqlSession session, SearchCriteria cri) throws SQLException;
	
	int selectMemberListCount(SqlSession session, SearchCriteria cri) throws SQLException;
	
	MemberVO selectMemberById(SqlSession session, String id) throws SQLException;
	
	void insertMember(SqlSession session, MemberVO member) throws SQLException;
	
	void updateMember(SqlSession session, MemberVO member) throws SQLException;
	
	void deleteMember(SqlSession session, String id) throws SQLException;
	
}
