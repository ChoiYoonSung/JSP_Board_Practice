package kr.or.ddit.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import kr.or.ddit.command.PageMaker;
import kr.or.ddit.command.SearchCriteria;
import kr.or.ddit.dao.MemberDAO;
import kr.or.ddit.dto.MemberVO;

public class MemberServiceImpl implements MemberService {

	private SqlSessionFactory sqlSessionFactory;
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}
	
	private MemberDAO memberDAO;
	public void setMemberDAO(MemberDAO memberDAO) {
		this.memberDAO = memberDAO;
	}
	
	@Override
	public Map<String, Object> getMemberList(SearchCriteria cri) throws SQLException {
		SqlSession session = sqlSessionFactory.openSession();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			PageMaker pageMaker = new PageMaker();
			pageMaker.setCri(cri);
			pageMaker.setTotalCount(memberDAO.selectMemberListCount(session, cri));
			
			List<MemberVO> memList = memberDAO.selectMemberList(session,cri);
			
			dataMap.put("memList",memList);
			dataMap.put("pageMaker", pageMaker);
			return dataMap;
		} finally {
			session.close();
		}
	}

	@Override
	public MemberVO getMemberById(String id) throws SQLException {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			MemberVO member = memberDAO.selectMemberById(session, id);
			return member;
		} finally {
			session.close();
		}
	}

	@Override
	public void registMember(MemberVO member) throws SQLException {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			memberDAO.insertMember(session, member);
		} finally {
			session.close();
		}
	}

	@Override
	public void modifyMember(MemberVO member) throws SQLException {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			memberDAO.updateMember(session, member);
		} finally {
			session.close();
		}
	}

	@Override
	public void removeMember(String id) throws SQLException {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			memberDAO.deleteMember(session, id);
		} finally {
			session.close();
		}
	}

}
