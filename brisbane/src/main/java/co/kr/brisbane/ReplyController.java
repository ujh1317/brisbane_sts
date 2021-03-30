package co.kr.brisbane;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import org.slf4j.Logger;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.LoggerFactory;

import model.board.BoardDto;
import model.reply.ReplyDto;

@Controller
public class ReplyController {

	@Autowired
	private SqlSession sqlSession;
	
	private static final Logger log = LoggerFactory.getLogger(ReplyController.class);
	
	@RequestMapping(value="/replyWrite.do",method=RequestMethod.POST)
	public String write(@ModelAttribute("replyDto") ReplyDto replyDto, HttpSession session) throws IOException, NamingException {
		
		String writer = (String)session.getAttribute("name");
		replyDto.setWriter(writer);
		sqlSession.insert("reply.insertReply", replyDto);
		
		return "redirect:noticeContent.do";
	}//write
	
	
	@RequestMapping("/replyDelete.do")
	public String delete(Model model, String no) throws IOException, NamingException {
		sqlSession.delete("reply.deleteReply", new Integer(no));
		return "redirect:noticeList.do";
	}//delete()
	
	
}//class
