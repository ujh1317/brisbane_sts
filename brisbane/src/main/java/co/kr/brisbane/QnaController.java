package co.kr.brisbane;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;//웹에서 보내준것 받기
import model.qna.QnaDto;

import java.io.IOException;
import java.util.*;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.apache.ibatis.session.SqlSession;

@Controller
public class QnaController {
	@Autowired //setter가 자동으로!
	private SqlSession sqlSession;
	
	@RequestMapping("qnaList.do")
	public String qnaList(Model model,String pageNum,String list_category) throws IOException,NamingException{
		
		if(pageNum==null) {pageNum="1";}
		
		if(list_category==null){
			list_category="";
		}
		
		int pageSize=10;
		int currentPage=Integer.parseInt(pageNum);
		int startRow=(currentPage-1)*pageSize+1;//한페이지의 첫번째 row구한다.
		int endRow=currentPage*pageSize;
		
		int count=0;//총 글수 넣으려고
		int number=0;
		int pageBlock=10;//블럭당 10개 페이지로 묶는다.
		
		count=sqlSession.selectOne("qna.getQnaCount");//총 글수 얻기.
		
		number=(currentPage-1)*pageSize+1;
		
		int pageCount=count/pageSize+(count%pageSize==0?0:1);//총 페이지 수
	      //                 몫                       꽁다리 레코드 수(31개 글, 페이지는 4개의 페이지)
	    int startPage=(currentPage/10)*10+1;//시작페이지
	      //                
	    int endPage=startPage+pageBlock-1;
	      //             1+10-1=10 end페이지
		
		HashMap map=new HashMap();
		map.put("start", startRow-1);//시작위치, mysql은 0부터 시작
		map.put("cnt", pageSize);//글개수
		map.put("list_category", list_category);
		
		List<QnaDto> qnaList=null;
		
		
		if(count>0){			
			if(list_category==null||list_category.equals("")||list_category.equals("전체 글")) {
				qnaList=sqlSession.selectList("qna.getQnaList",map);
			}else if(list_category!=null&&list_category!=""){
				qnaList=sqlSession.selectList("qna.getsortedQnaList",map);
			}
		}else{
			qnaList=Collections.EMPTY_LIST;//비어있다는 뜻
		}
		
		model.addAttribute("pageNum",pageNum);
		model.addAttribute("list_category",list_category);
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
		
		model.addAttribute("qnaList",qnaList);
		
		return ".main.qna.qnaList";
	}	
	

	@RequestMapping("/qnaWriteForm.do")
	public String qnaWriteForm(Model model, String num, String ref, String re_step, String re_level, String pageNum) {
		
		if(num==null) {//원글 쓰기
			num="0";//글번호
			ref="1";//글그룹
			re_step="0";//글 순서
			re_level="0";//글깊이
		}
		
		QnaDto qnaDto=sqlSession.selectOne("qna.getQnaArticle",new Integer(num));
		
		model.addAttribute("pageNum",pageNum);//request.setAttribute()
		model.addAttribute("num",new Integer(num));
		model.addAttribute("ref",new Integer(ref));
		model.addAttribute("re_step",new Integer(re_step));
		model.addAttribute("re_level",new Integer(re_level));
		model.addAttribute("qnaDto",qnaDto);

		return ".main.qna.qnaWriteForm";//뷰리턴
	}
	
	//DB에 글쓰기
	@RequestMapping(value="qnaWritePro.do",method=RequestMethod.POST)
	public String qnaWritePro(@ModelAttribute("qnaDto") QnaDto qnaDto, HttpServletRequest request) 
			throws IOException,NamingException{
		

		HttpSession session=request.getSession();
		String writer=(String)session.getAttribute("memId");
		qnaDto.setWriter(writer);
		int maxNum=0;//최대 글번호,변수 선언
		if(sqlSession.selectOne("qna.getQnaMaxNum")!=null) {
			//최대 글번호가 있으면
			maxNum=sqlSession.selectOne("qna.getQnaMaxNum");
		}
		if(maxNum!=0) {//최대 글번호가 0이 아니면
			maxNum=maxNum+1;//최대 글번호 +1로 글작성,답글그룹으로 하려고
		}else {
			maxNum=1;//글번호1부터 작성
		}
		
		String ip=request.getRemoteAddr();
		qnaDto.setIp(ip);
		
		if(qnaDto.getNum()!=0) {//답글이면
			//답글 끼워넣기 위치 확보
			sqlSession.update("qna.updateQnaStep",qnaDto);
			qnaDto.setRe_step(qnaDto.getRe_step()+1);
			qnaDto.setRe_level(qnaDto.getRe_level()+1);
		}else {//원글일때
			qnaDto.setRef(new Integer(maxNum));//글그룹
			qnaDto.setRe_step(new Integer(0));//글 순서0
			qnaDto.setRe_level(new Integer(0));//글 깊이0	
		}
		
		sqlSession.insert("qna.insertQnaArticle",qnaDto);
		
		return "redirect:qnaList.do";
	} 
 	
	@RequestMapping("qnaContent.do")
	public String qnaContent(Model model,String num,String pageNum) throws IOException,NamingException{
		
		sqlSession.update("qna.updateQnaReadcount",Integer.parseInt(num));//조회수 증가
		//글내용 보기
		QnaDto qnaDto=sqlSession.selectOne("qna.getQnaArticle",Integer.parseInt(num));
		String content=qnaDto.getContent();
		content=content.replace("\n", "<br>");
		model.addAttribute("content",content);
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("num", new Integer(num));
		model.addAttribute("ref", new Integer(qnaDto.getRef()));
		model.addAttribute("re_step", new Integer(qnaDto.getRe_step()));
		model.addAttribute("re_level", new Integer(qnaDto.getRe_level()));
		model.addAttribute("qnaDto", qnaDto);

		return ".main.qna.qnaContent";//뷰리턴
	}
	
	//글수정 폼
	@RequestMapping("qnaUpdateForm.do")
	public ModelAndView qnaUpdateForm(String num,String pageNum) throws IOException,NamingException{
		
		QnaDto qnaDto=sqlSession.selectOne("qna.getQnaArticle",Integer.parseInt(num));
		
		ModelAndView mv=new ModelAndView();
		
		mv.addObject("num", new Integer(num));
		mv.addObject("pageNum",pageNum);
		mv.addObject("qnaDto",qnaDto);

		mv.setViewName(".main.qna.qnaUpdateForm");//뷰
		return mv;
	}
	
	@RequestMapping(value="qnaUpdatePro.do",method=RequestMethod.POST)
	public ModelAndView qnaUpdatePro(QnaDto qnaDto,String pageNum) throws IOException,NamingException{
		
		int x=-10;
		
		QnaDto dto=sqlSession.selectOne("qna.getQnaArticle",qnaDto.getNum());
		if(dto==null) {
			x=0;
		}else {
			sqlSession.update("qna.updateQnaArticle",qnaDto);
			x=1;
		}
		
		int check=x;
		ModelAndView mv=new ModelAndView();
		mv.addObject("pageNum",pageNum);
		mv.addObject("check",check);
		mv.setViewName(".main.qna.qnaUpdatePro");
		return mv;
	}//
	
	//글삭제
	@RequestMapping("qnaDelete.do")
	public String qnaDelete(Model model,String num,String pageNum) {
		int check=-10;
		QnaDto dto=sqlSession.selectOne("qna.getQnaArticle",Integer.parseInt(num));
		if(dto==null) {
			check=0;
		}else {
			sqlSession.delete("qna.deleteQnaArticle",new Integer(num));
			check=1;
		}
		
		model.addAttribute("pageNum",pageNum);
		model.addAttribute("check",check);
		
		return ".main.qna.qnaDelete";
	}
	
}//class end
