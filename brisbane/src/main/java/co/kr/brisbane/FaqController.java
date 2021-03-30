package co.kr.brisbane;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.omg.CORBA.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import model.faq.FaqDto;

@Controller
public class FaqController {
	@Autowired // setter가 자동
	private SqlSession sqlSession1;

	//글쓰기 폼,답글,원글
	@RequestMapping("/faqWriteForm.do")
	public String WriteForm(Model model,String num,String ref,
			String re_step,String re_level,String pageNum,String question, String answer) {

		if(num==null) {//원글 쓰기
			num="0";//글번호
			ref="1";//글 그룹
			re_step="0";//글 순서
			re_level="0";//글 깊이
			
		}
		
		model.addAttribute("answer",answer);
		model.addAttribute("question",question);
		model.addAttribute("pageNum",pageNum);//request.setAttribute()
		model.addAttribute("num",new Integer(num));
		model.addAttribute("ref",new Integer(ref));
		model.addAttribute("re_step",new Integer(re_step));
		model.addAttribute("re_level",new Integer(re_level));
		

		//return "board/writeForm";//뷰 이름 리턴 views/ writeForm.jsp
		return ".main.faq.faqWriteForm";//뷰 이름 리턴 views/ writeForm.jsp
	}
	

	//DB에 글쓰기
		@RequestMapping(value="faqWritePro.do",method=RequestMethod.POST)
		public String writeP(@ModelAttribute("faqDto") FaqDto faqDto,
				HttpServletRequest request) throws IOException,NamingException{

			int maxNum=0;//최대 글번호, 변수 선언
			if(sqlSession1.selectOne("faq.faqnumMax") != null) {
				//최대 글번호가 null아니면 , 최대 글번호가 있으면
				maxNum=sqlSession1.selectOne("faq.faqnumMax");
			}

			if(maxNum!=0) {//최대 글번호가 0이 아니면 
				maxNum=maxNum+1;//최대 글번호 +1 로 글작성 , 답글 그룹으로 하려고 
			}else {
				maxNum=1;//글번호 1부터 작성 (처음 글이면), 답글 그룹으로 사용하려고
			}

			String ip=request.getRemoteAddr();//ip구한다 
			faqDto.setIp(ip);//setter 작업
			
			if(faqDto.getNum() != 0) {//답글이면
				//답글 끼원 넣기 위치 확보
				sqlSession1.update("faq.reStep",faqDto);
				faqDto.getQuestion();
				faqDto.getAnswer();
				faqDto.getNum();
				faqDto.setRe_step(faqDto.getRe_step() + 1);//글순서+1
				faqDto.setRe_level(faqDto.getRe_level() + 1);//글 깊이 +1
				
				
			}else {//원글일때
				faqDto.getQuestion();
				faqDto.getAnswer();
				faqDto.setRef(new Integer(maxNum));//글 그룹
				faqDto.setRe_step(new Integer(0));// 글 순서 0
				faqDto.setRe_level(new Integer(0));//길 깊이 0
				faqDto.getNum();
		
			}

			sqlSession1.insert("faq.insertfaqBoard",faqDto);//insert

			return "redirect:faqList.do";//response.sendRedirect("lsit.jsp)와 같다
		}

		@RequestMapping("faqList.do")
		public String list(Model model, String pageNum) 
				throws IOException,NamingException{

			if(pageNum==null) {pageNum="1";}
		
			int pageSize=10;
			int currentPage=Integer.parseInt(pageNum);
			int startRow=(currentPage-1)*pageSize+1;//한페이지의 첫번재 row 구한다 
			int endRow=currentPage*pageSize;//한 페이지의 마지막 row를 구한다 

			int count=0;//총 글수 넣으려고 
			int pageBlock=10;//블럭당 10개 페이지로 묶는다 
			
			count=sqlSession1.selectOne("faq.faqselectCount");//총 글수 얻기 

			int number = count-(currentPage-1)*pageSize;
		     int pageCount=count/pageSize+(count%pageSize==0?0:1);
		       int startPage=(currentPage/10)*10+1;//시작페이지             
		       int endPage=startPage+pageBlock-1;
			
			//int pageCount=count/pageSize+(count%pageSize==0?0:1);//총 페이지수 구하기

			HashMap<String,Integer> map=new HashMap<String,Integer>();
			map.put("start", startRow-1);//시작위치 ,mysql은 0부턴 시작 
			map.put("cnt",pageSize);//글 갯수 (10개씩)
			


		
			//             1+10-1=10 end페이지


			List<FaqDto> list=sqlSession1.selectList("faq.faqselectList",map);

			model.addAttribute("currentPage",currentPage);
			model.addAttribute("startRow",startRow);
			model.addAttribute("endRow",endRow);

			model.addAttribute("pageBlock",pageBlock);
			model.addAttribute("pageCount",pageCount);

			model.addAttribute("startPage",startPage);
			model.addAttribute("endPage",endPage);
		
			model.addAttribute("count",count);
			model.addAttribute("pageSize",pageSize);

			model.addAttribute("num",number);
			model.addAttribute("list",list);//출력할 데이터 

			//return "board/list";//뷰 리턴
			return ".main.faq.faqList";//뷰 리턴
		}
		


		 @RequestMapping("faqContent.do")
		   public String content(Model model,String num,String pageNum,String ref, String re_step,String re_level )
		   throws IOException,NamingException{
			 
			  
			   int num1=Integer.parseInt(num);
			 
		
			
			   sqlSession1.update("faq.faqcount",num1);//조회수 증가
			   
			   FaqDto faqDto=sqlSession1.selectOne("faq.getfaqBoard",num1);
			   
			   String content=faqDto.getContent();
			   content=content.replace("\n","<br>");
			   model.addAttribute("content",content);
			  
			   model.addAttribute("re_level",re_level);
			   model.addAttribute("re_step",re_step);
			   model.addAttribute("ref", ref);
			   model.addAttribute("num",new Integer(num1));
			   model.addAttribute("pageNum",pageNum);
			   model.addAttribute("faqDto",faqDto);
			 
			   //return "board/content";//뷰리턴,
			   return ".main.faq.faqContent";//뷰리턴,
			   
		   }
		 
		   //글수정 폼
		   @RequestMapping("faqUpdate.do")
		   public ModelAndView updateForm(String num, String pageNum)
		   throws IOException,NamingException{
			   int num1=Integer.parseInt(num);
			   FaqDto faqDto=sqlSession1.selectOne("faq.getfaqBoard",num1);
			   
			   ModelAndView mv=new ModelAndView();
			   mv.addObject("num",new Integer(num1));
			   mv.addObject("pageNum",pageNum);
			   mv.addObject("faqDto",faqDto);
			   //mv.setViewName("board/updateForm");//뷰
			   mv.setViewName(".main.faq.faqUpdate");//뷰
			   
			   return mv;
		   }
		   //DB글 수정
		   @RequestMapping(value="faqUpdatePro.do",method=RequestMethod.POST)
		   public ModelAndView faqUpdatePro(FaqDto faqDto,String pageNum,String num)
		   throws IOException,NamingException{
			   sqlSession1.update("faq.faqupdatePro",faqDto);//글수정
			   
			  ModelAndView mv=new ModelAndView();
			  mv.addObject("num", num);
			  mv.addObject("pageNum", pageNum);
			  mv.setViewName("redirect:faqList.do");
			  return mv;
		   }//updateForm.jsp 만들러가기
		   
		   
		   //DB글 삭제
		   @RequestMapping("faqDelete.do")
		   public String delete(Model model,String num, String pageNum) {
			   sqlSession1.delete("faq.faqdeletePro",new Integer(num));
			   model.addAttribute("pageNum",pageNum);
			   return "redirect:faqList.do";
		   }
		
}

		
