package co.kr.brisbane;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.*;//HashMap 사용
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.ibatis.session.SqlSession; //MyBatis 사용
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import javax.naming.NamingException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;

import model.member.MemberDto;
import model.notice.NoticeDto;
import model.reply.ReplyDto;

@Controller
public class NoticeController {
	
	@Autowired
	private SqlSession sqlSession;
	
	//글쓰기 폼
	@RequestMapping("/noticeWrite.do")
	public String writeForm(Model model, String num, String ref, String re_step, String re_level, String pageNum, HttpServletRequest request) {
		
		if(num==null) {
			num = "0";
			ref = "1";
			re_step = "0";
			re_level = "0";
		}//if
		
		HttpSession session=request.getSession();
		String writer=(String)session.getAttribute("name");
		model.addAttribute("writer", writer);
		
		model.addAttribute("num", new Integer(num));
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("ref", new Integer(ref));
		model.addAttribute("re_step", new Integer(re_step));
		model.addAttribute("re_level", new Integer(re_level));
		
		return ".main.notice.noticeWrite";
	}//writeForm()
	
	//DB글쓰기
	@RequestMapping(value="/noticeWritePro",method=RequestMethod.POST)
	public String writePro(@ModelAttribute("noticeDto") NoticeDto noticeDto, HttpServletRequest request) 
			throws IOException, NamingException {
		
		int maxnum = 0;
		if(sqlSession.selectOne("notice.nMaxnum") != null) {
			maxnum = sqlSession.selectOne("notice.nMaxnum");
		}//if
		
		if(maxnum != 0) {
			maxnum = maxnum+1;
		}else {
			maxnum = 1;
		}//else
		
		String ip = request.getRemoteAddr();
		noticeDto.setIp(ip);
		
		if(noticeDto.getNum() != 0) {//답글일때
			sqlSession.update("notice.nRestep", noticeDto);
			noticeDto.setRe_step(noticeDto.getRe_step()+1);
			noticeDto.setRe_level(noticeDto.getRe_level()+1);
		}else {
			noticeDto.setRef(new Integer(maxnum));
			noticeDto.setRe_step(new Integer(0));
			noticeDto.setRe_level(new Integer(0));
		}//else
		
		sqlSession.update("notice.insertNotice", noticeDto);
		
		return "redirect:noticeList.do";
	}//writePro()
	
	//리스트
	@RequestMapping("/noticeList.do")
	public String noticeList(Model model, String pageNum, String num) throws IOException, NamingException {
		
		if(pageNum==null) {pageNum = "1";}
		
		int pageSize = 10;
		int currentPage = Integer.parseInt(pageNum);
		int startRow = (currentPage-1)*pageSize+1;//페이지의 처음row
		int endRow = currentPage*pageSize;//페이지의 마지막 row
		int pageBlock = 10;
		int count = 0;
		
		count = sqlSession.selectOne("notice.nCount");//총 글갯수
		
		int number = count-(currentPage-1)*pageSize;//글번호
		int pageCount = count/pageSize+(count%pageSize==0?0:1);//총 페이지 갯수
		int startPage = (currentPage/10)*10+1;//시작페이지
		int endPage = startPage+pageBlock-1;//마지막페이지
		
		HashMap<String,Integer> map = new HashMap<String,Integer>();
		map.put("start", startRow-1);
		map.put("cnt", pageSize);
		
		List<NoticeDto> list = sqlSession.selectList("notice.noticeList", map);
		List<NoticeDto> bestList = sqlSession.selectList("notice.noticeBest");
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("startRow", startRow);
		model.addAttribute("endRow", endRow);
		model.addAttribute("pageBlock", pageBlock);
		model.addAttribute("count", count);
		model.addAttribute("number", number);
		model.addAttribute("pageCount", pageCount);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("list", list);
		model.addAttribute("bestList", bestList);

		return ".main.notice.noticeList";
	}//noticeList()
	
	//글내용보기/조회수증가
	@RequestMapping("/noticeContent.do")
	public String noticeContent(Model model, String num, String pageNum) throws IOException, NamingException {
		
		int num1 = Integer.parseInt(num);
		sqlSession.update("notice.nReadcount", num1);
		
		//글내용보기
		NoticeDto noticeDto = sqlSession.selectOne("notice.getNotice", num1);
		String content = noticeDto.getContent();
		model.addAttribute("content", content);
		model.addAttribute("num", num1);
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("noticeDto", noticeDto);
		
		List<ReplyDto> replyList = sqlSession.selectList("reply.replyList",num1);
		model.addAttribute("replyList", replyList);
		
		return ".main.notice.noticeContent";
	}//noticeContent()
	
	//글수정폼
	@RequestMapping("/noticeUpdate.do")
	public ModelAndView noticeUpdate(String num, String pageNum, HttpServletRequest request) throws IOException, NamingException {
		
		int num1 = Integer.parseInt(num);
		NoticeDto noticeDto = sqlSession.selectOne("notice.getNotice", num1);
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("pageNum", pageNum);
		mv.addObject("noticeDto", noticeDto);
		mv.setViewName(".main.notice.noticeUpdate");
		
		return mv;
	}//noticeUpdate()
	
	//DB글수정
	@RequestMapping(value="/noticeUpdatePro.do",method=RequestMethod.POST)
	public ModelAndView noticeUpdatePro(NoticeDto noticeDto, String pageNum, HttpServletRequest request) 
			throws IOException, NamingException {
		
		String ip = request.getRemoteAddr();
		noticeDto.setIp(ip);
		
		sqlSession.update("notice.updateNotice", noticeDto);
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("pageNum", pageNum);
		mv.setViewName("redirect:noticeList.do");
		
		return mv;
	}//noticeUpdatePro()
	
	//글삭제
	@RequestMapping("/noticeDelete.do")
	public String noticeDelete(Model model, String num, String pageNum) throws IOException, NamingException {
		
		sqlSession.delete("notice.deleteNotice", new Integer(num));
		model.addAttribute("pageNum", pageNum);
		
		return "redirect:noticeList.do";
	}//noticeDelete()
	
	
	
}//class
