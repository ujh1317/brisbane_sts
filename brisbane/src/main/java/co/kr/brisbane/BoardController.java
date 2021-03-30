package co.kr.brisbane;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.*;//HashMap 사용
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.ibatis.session.SqlSession;//Mybatis 사용

import model.board.BoardDto;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import javax.naming.NamingException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class BoardController {
   
   @Autowired // setter가 자동
   private SqlSession sqlSession;
   
   //글쓰기 폼,답글,원글
   @RequestMapping("/boardWriteForm.do")
   public String writeF(Model model,String num,String ref, 
         String re_step,String re_level,String pageNum) {
      
      if(num==null) {//원글 쓰기
         num="0";//글번호
         ref="1";//글 그룹
         re_step="0";//글 순서
         re_level="0";//글 깊이
      }
      
      model.addAttribute("pageNum",pageNum);//request.setAttribute()
      model.addAttribute("num",new Integer(num));
      model.addAttribute("ref",new Integer(ref));
      model.addAttribute("re_step",new Integer(re_step));
      model.addAttribute("re_level",new Integer(re_level));
      
      
      //return "board/writeForm";//뷰 이름 리턴 views/ writeForm.jsp
      return ".main.board.boardWriteForm";//뷰 이름 리턴 views/ writeForm.jsp
   }
   
   //DB에 글쓰기
   @RequestMapping(value="WritePro.do",method=RequestMethod.POST)
   public String writePro(@ModelAttribute("boardDto") BoardDto boardDto,
         HttpServletRequest request) throws IOException,NamingException{
   
      int maxNum=0;//최대 글번호, 변수 선언
      if(sqlSession.selectOne("board.boardnumMax") != null) {
         //최대 글번호가 null아니면 , 최대 글번호가 있으면
         maxNum=sqlSession.selectOne("board.boardnumMax");
      }
      
      if(maxNum!=0) {//최대 글번호가 0이 아니면 
         maxNum=maxNum+1;//최대 글번호 +1 로 글작성 , 답글 그룹으로 하려고 
      }else {
         maxNum=1;//글번호 1부터 작성 (처음 글이면), 답글 그룹으로 사용하려고
      }
      
      String ip=request.getRemoteAddr();//ip구한다 
      boardDto.setIp(ip);//setter 작업
      
      if(boardDto.getNum() != 0) {//답글이면
         //답글 끼원 넣기 위치 확보
         sqlSession.update("board.boardreStep",boardDto);
         boardDto.setRe_step(boardDto.getRe_step() + 1);//글순서+1
         boardDto.setRe_level(boardDto.getRe_level() + 1);//글 깊이 +1
      }else {//원글일때
         boardDto.setRef(new Integer(maxNum));//글 그룹
         boardDto.setRe_step(new Integer(0));// 글 순서 0
         boardDto.setRe_level(new Integer(0));//길 깊이 0
      }
      
      sqlSession.insert("board.boardinsertBoard",boardDto);//insert
      
      return "redirect:boardList.do";//response.sendRedirect("lsit.jsp)와 같다
   }
   
   @RequestMapping("boardList.do")
   public String listboard(Model model, String pageNum) 
         throws IOException,NamingException{
	   
      
      if(pageNum==null) {pageNum="1";}
      
      int pageSize=10;
      int currentPage=Integer.parseInt(pageNum);
      int startRow=(currentPage-1)*pageSize+1;//한페이지의 첫번재 row 구한다 
      int endRow=currentPage*pageSize;//한 페이지의 마지막 row를 구한다 
      
      int count=0;//총 글수 넣으려고 
      int pageBlock=10;//블럭당 10개 페이지로 묶는다 
      
      count=sqlSession.selectOne("board.boardselectCount");//총 글수 얻기 
      
      int number=count-(currentPage-1)*pageSize;//글번를 역순으로 
      //int pageCount=count/pageSize+(count%pageSize==0?0:1);//총 페이지수 구하기
      
      HashMap<String,Integer> map=new HashMap<String,Integer>();
      map.put("start", startRow-1);//시작위치 ,mysql은 0부턴 시작 
      map.put("cnt",pageSize);//글 갯수 (10개씩)
      
      
      int pageCount=count/pageSize+(count%pageSize==0?0:1);//총 페이지 수
      //                 몫                       꽁다리 레코드 수(31개 글, 페이지는 4개의 페이지)
      int startPage=(currentPage/10)*10+1;//시작페이지
      //                
      int endPage=startPage+pageBlock-1;
      //             1+10-1=10 end페이지
      
      
      List<BoardDto> list=sqlSession.selectList("board.boardselectList",map);
      
      model.addAttribute("currentPage",currentPage);
      model.addAttribute("startRow",startRow);
      model.addAttribute("endRow",endRow);
      
      model.addAttribute("pageBlock",pageBlock);
      model.addAttribute("pageCount",pageCount);
      
      model.addAttribute("startPage",startPage);
      model.addAttribute("endPage",endPage);
      
      model.addAttribute("count",count);
      model.addAttribute("pageSize",pageSize);
      
      model.addAttribute("number",number);
      model.addAttribute("list",list);//출력할 데이터 
      
      //return "board/list";//뷰 리턴
      return ".main.board.boardList";//뷰 리턴
   }
   
   //조회수 증가, 글내용 보기
   @RequestMapping("boardContent.do")
   public String content(Model model,String num,String pageNum)
   throws IOException,NamingException{
	   int num1=Integer.parseInt(num);
	   sqlSession.update("board.boardreadCount",num1);//조회수 증가
	   
	   BoardDto boardDto=sqlSession.selectOne("board.boardgetBoard",num1);
	   
	   String content=boardDto.getContent();
	   content=content.replace("\n","<br>");
	   model.addAttribute("content",content);
	   
	   model.addAttribute("num",num1);
	   model.addAttribute("pageNum",pageNum);
	   model.addAttribute("boardDto",boardDto);
	   
	   //return "board/content";//뷰리턴,
	   return ".main.board.boardContent";//뷰리턴,
	   
   }
   
   //글수정 폼
   @RequestMapping("boardUpdate.do")
   public ModelAndView updateForm(String num, String pageNum)
   throws IOException,NamingException{
	   int num1=Integer.parseInt(num);
	   BoardDto boardDto=sqlSession.selectOne("board.boardgetBoard",num1);
	   
	   ModelAndView mv=new ModelAndView();
	   mv.addObject("pageNum",pageNum);
	   mv.addObject("boardDto",boardDto);
	   //mv.setViewName("board/updateForm");//뷰
	   mv.setViewName(".main.board.boardUpdate");//뷰
	   
	   return mv;
   }
   
   
   //DB글 수정
   @RequestMapping(value="UpdatePro.do",method=RequestMethod.POST)
   public ModelAndView updatePro(BoardDto boardDto,String pageNum)
   throws IOException,NamingException{
	   sqlSession.update("board.boardupdatePro",boardDto);//글수정
	   
	  ModelAndView mv=new ModelAndView();
	  mv.addObject("pageNum", pageNum);
	  mv.setViewName("redirect:boardList.do");
	  return mv;
   }//updateForm.jsp 만들러가기
   
   //DB글 삭제
   @RequestMapping("Delete.do")
   public String delete(Model model,String num, String pageNum) {
	   sqlSession.delete("board.boarddeletePro",new Integer(num));
	   model.addAttribute("pageNum",pageNum);
	   return "redirect:boardList.do";
   }
   
}//class end