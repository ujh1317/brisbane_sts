package co.kr.brisbane;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;//웹에서 보내준것 받기

import model.main.MainDto;
import model.member.MemberDto;


import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.apache.ibatis.session.SqlSession;

@Controller
public class MemberController {

	@Autowired
	private SqlSession sqlSession;//변수
	
	@RequestMapping("main.do")
	public String main(String menu,Model model) {
		
		if(menu==null){
			menu="1";
		}
		
		String data="";
		
		MainDto mainDto=sqlSession.selectOne("main.getMainArticle",menu);
		if(mainDto!=null) {
			data=mainDto.getData();
			if(data.equals("")) {
				data="등록된 글이 없습니다.";
			}
		}else {
			data="등록된 글이 없습니다.";
		}
		model.addAttribute("data",data);
		model.addAttribute("menu",menu);
		
		return ".main.member.main";
	}
	
	@RequestMapping("mainWriteForm.do")
	public String mainWriteForm(String menu, Model model) {
		
		if(menu==null) {
			menu="1";
		}
		
		MainDto mainDto=sqlSession.selectOne("main.getMainArticle",menu);
		
		model.addAttribute("mainDto", mainDto);
		model.addAttribute("menu", menu);
		
		return ".main.member.mainWriteForm";
	}//
	
	@RequestMapping(value="mainWritePro.do", method=RequestMethod.POST)
	public String mainWritePro(@ModelAttribute("mainDto") MainDto mainDto,HttpServletRequest request,Model model) throws IOException,NamingException{
		String menu=request.getParameter("menu");
		MainDto dto=sqlSession.selectOne("main.getMainArticle",menu);
		mainDto.setMenu(menu);
		if(dto==null) {
			sqlSession.insert("main.insertMainArticle",mainDto);
		}else {
			sqlSession.update("main.updateMainArticle",mainDto);
		}
		model.addAttribute("menu",menu);	
		return "redirect:main.do";
	}
	
	@RequestMapping(value="mainImage.do", method=RequestMethod.POST)
	public String mainImage(HttpServletRequest request,Model model) throws IOException,NamingException{

		model.addAttribute("request",request);
		return "/member/mainImage";
	}
	

	@RequestMapping("memberInputForm.do")
	public String memberInputForm() {
		
		return ".main.member.memberInputForm";//뷰리턴
	}
	
	//id 중복체크
	@RequestMapping(value="memberConfirmId.do", method=RequestMethod.POST)
	public String memberConfirmId(String id,Model model) throws IOException,NamingException{
		int check=1;
		MemberDto memberDto=sqlSession.selectOne("member.selectOne",id);
		if(memberDto==null) {
			check=-1;//사용 가능한 아이디
		}
		model.addAttribute("check",check);
		return "/member/memberConfirmId";
	}
	
	@RequestMapping(value="memberInputPro.do", method=RequestMethod.POST)
	public String memberInputPro(@ModelAttribute("memberDto") MemberDto memberDto,HttpServletRequest request) throws IOException,NamingException{
		
		String addr=request.getParameter("addr");
		String addr2=request.getParameter("addr2");
		String id=request.getParameter("id");
		memberDto.setAddr(addr+","+addr2);
		memberDto.setRegdate(new Timestamp(System.currentTimeMillis()));
		sqlSession.insert("member.insertMember",memberDto);
		
		return "redirect:main.do";
	}
	


	//로그인
	@RequestMapping(value="memberLoginPro.do",method=RequestMethod.POST)
	public String memberLoginPro(String id,String pw,Model model,HttpServletRequest request) throws IOException,NamingException{
		

		MemberDto memberDto=sqlSession.selectOne("member.selectLogin", id);
		String dbPw="";
	
		int x=-1;
		int check=x;
		if(memberDto!=null){
			dbPw=memberDto.getPw();
			if(pw.equals(dbPw)){
				String name = memberDto.getName();
				HttpSession session = request.getSession();
				session.setAttribute("name", name);
				x=1;//로그인 성공
				
			}else{
				x=0;//암호 틀림
			}//else
		}//
		check=x;

		model.addAttribute("check",check);
		model.addAttribute("id",id);
		model.addAttribute("memId",id);

		return ".main.member.memberLoginPro";
		
	}//
	//로그아웃
	@RequestMapping("memberLogout.do")
	public String memberLogout() {
		//return "/member/logOut";
		return ".main.member.memberLogout";
	}
	
	@RequestMapping("memberModify.do")
	public String memberModify() {
		//return "/member/logOut";
		return ".main.member.memberModify";
	}
	
	//멤버수정폼
	@RequestMapping(value="memberModifyForm.do",method=RequestMethod.POST)
	public String memberModifyForm(String id,Model model) throws IOException,NamingException{
		
		MemberDto memberDto=sqlSession.selectOne("member.selectOne",id);
		String addr=memberDto.getAddr();
		String addr1[]=addr.split(",");
		//=================================
		
		String addr2="";
		
		if(addr!=null) {
			if(addr1.length==0) {
				addr="";
				memberDto.setAddr(addr);
			}else if(addr1.length==1) {
				addr=addr1[0];
				memberDto.setAddr(addr);
			}else if(addr1.length==2) {
				addr=addr1[0];
				addr2=addr1[1];
				memberDto.setAddr(addr);
			}//if
				
		}//if
		//----------------------------------
		//addr=addr1[0];//주소
		//String addr2=addr1[1];//상세주소
		//memberDto.setAddr(addr);
		//----------------------------------
		model.addAttribute("addr2",addr2);
		model.addAttribute("memberDto",memberDto);

		//return "/member/editForm";
		return ".main.member.memberModifyForm";
	}
	
	@RequestMapping(value="memberModifyPro.do",method=RequestMethod.POST)
	public String editPro(@ModelAttribute("memberDto") MemberDto memberDto, HttpServletRequest request) throws IOException,NamingException{
		
		String addr=request.getParameter("addr");
		String addr2=request.getParameter("addr2");
		
		String pw=request.getParameter("pw");
		
		memberDto.setAddr(addr+","+addr2);
		memberDto.setPw(pw);
		
		sqlSession.update("member.memberUpdate",memberDto);
		return ".main.member.memberModifyPro";
	}
	
	//회원탈퇴
	@RequestMapping("memberDeleteForm.do")
	public String memberDeleteForm() throws IOException,NamingException{
		
		return ".main.member.memberDeleteForm";//뷰리턴
	}
	
	@RequestMapping(value="memberDeletePro.do",method=RequestMethod.POST)
	public String memberDeletePro(String id,String pw,Model model) throws IOException,NamingException{
		
		MemberDto memberDto=sqlSession.selectOne("member.selectLogin", id);
		String dbPw="";
		int x=-1;
		int check=x;
		if(memberDto!=null){
			dbPw=memberDto.getPw();
			if(pw.equals(dbPw)){
				sqlSession.delete("member.memberDelete",id);
				x=1;//로그인 성공
			}//if
		}//
		check=x;
		model.addAttribute("check",check);		
		return ".main.member.memberDeletePro";//뷰리턴
	}//
}//class end

